package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Break;
import org.example.employeetimetrackingservice.entities.Schedule;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.UserTimeRecord;
import org.example.employeetimetrackingservice.repositories.BreakRepository;
import org.example.employeetimetrackingservice.repositories.UserTimeRecordsRepository;
import org.example.employeetimetrackingservice.utils.DayNameConvertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;
import java.util.Optional;

@Service
public class WorkService {
    @Autowired
    private BreakRepository breakRepository;

    @Autowired
    private ScheduleService scheduleService;

    @Autowired
    private UserTimeRecordsRepository userTimeRecordsRepository;

    @Autowired
    private UserService userService;

    private static final Long START_HOURS_LIMIT = 1L;

    private static final int NOT_WORKING_DAY = -1;
    private static final int WORK_NOT_STARTED_STATUS = 0;
    private static final int IN_WORK_STATUS = 1;
    private static final int WORKPAUSED_STATUS = 2;
    private static final int WORKENDED_STATUS = 3;

    @Transactional
    public int getWorkStatus(String userName){
        LocalDate now = LocalDate.now();
        Optional<UserTimeRecord> userTimeRecord = userTimeRecordsRepository
                .findByDateAndUser(LocalDate.now(),userName);
        if(userTimeRecord.isPresent()){
            return userTimeRecord.get().getStatus();
        }
        else{
            String rusDayName = DayNameConvertions.getRusDayName(now.getDayOfWeek().toString());
            Optional<Schedule> userSchedule =  scheduleService.getUserScheduleByDate(userName, rusDayName);
            if(!userSchedule.isPresent()){
                return NOT_WORKING_DAY;
            }
            return WORK_NOT_STARTED_STATUS;
        }
    }

    @Transactional
    public int startWork(String userName){
        //переменные
        LocalDate date = LocalDate.now();
        String dayName = DayNameConvertions.getRusDayName(
                date.getDayOfWeek().toString()
        );
        Schedule userSchedule = scheduleService.getUserScheduleByDate(userName,dayName)
                .orElseThrow(()->new EntityNotFoundException("Расписания на "+dayName+" нет"));
        LocalTime currentTime = LocalTime.now();
        LocalTime userScheduleStart = userSchedule.getDayShift().getStart();
        User user = userService.findByName(userName);

        //проверки
        startWorkChecks(userName,dayName,userSchedule,currentTime,userScheduleStart);

        //записываем новую запись
        UserTimeRecord userTimeRecord = UserTimeRecord.builder()
                .date(date)
                .dayName(dayName)
                .delay(LocalTime.of(0,0,0))
                .overworkedTime(LocalTime.of(0,0,0))
                .underworkedTime(LocalTime.of(0,0,0))
                .user(user)
                .status(IN_WORK_STATUS) //установить статус в работе
                .build();

        //если опоздание
        if(currentTime.compareTo(userScheduleStart)==1){
            LocalTime delay = currentTime.minusHours(userScheduleStart.getHour());
            delay = delay.minusMinutes(userScheduleStart.getMinute());
            delay = delay.minusSeconds(userScheduleStart.getSecond());
            userTimeRecord.setDelay(delay);
        }

        //если раньше начал
        if(currentTime.compareTo(userScheduleStart)==-1){
            LocalTime overworkedTime = userScheduleStart.minusHours(currentTime.getHour());
            overworkedTime = overworkedTime.minusMinutes(currentTime.getMinute());
            overworkedTime = overworkedTime.minusSeconds(currentTime.getSecond());
            userTimeRecord.setOverworkedTime(overworkedTime);
        }

        userTimeRecordsRepository.save(userTimeRecord);

        return IN_WORK_STATUS;
    }

    private void startWorkChecks(String userName, String dayName,
                                 Schedule userSchedule,
                                 LocalTime currentTime,
                                 LocalTime userScheduleStart){
        int status = getWorkStatus(userName);
        if(status==WORKENDED_STATUS){
            throw new EntityNotFoundException("Работа закончена");
        }
        if(status!=WORK_NOT_STARTED_STATUS){
            throw new EntityNotFoundException("Работа уже начата");
        }
        //проверка празник ли
        if(scheduleService.getUsersSchedule(userName,0L).isHoliday(dayName)){
            throw new EntityNotFoundException("Сегодня у вас выходной!");
        }

        //u can start work not before more than 1 hour from the start
        if(!currentTime.plusHours(START_HOURS_LIMIT).isAfter(userScheduleStart)){
            throw new EntityNotFoundException("Слишком рано для начала смены. Вы не можете начать работу ранее 1 часа до начала");
        }
    }


    @Transactional
    public int takeBreak(String userName){
        //проверки
        takeBreakChecks(userName);

        //логика
        LocalDate today = LocalDate.now();
        String constantEngDayName = today.getDayOfWeek().toString();
        String rusDayName = DayNameConvertions.getRusDayName(constantEngDayName);
        User user = userService.findByName(userName);
        //если расписание отсутствует
        Schedule schedule = scheduleService.getUserScheduleByDate(userName,rusDayName)
                .orElseThrow(()->new EntityNotFoundException("Расписания на данный день нету"));

        Break newBreak = Break.builder()
                .dayName(rusDayName)
                .date(today)
                .user(user)
                .start(LocalTime.now())
                .end(schedule.getDayShift().getEnd())
                .build();

        //сохранение и указание нового статуса
        breakRepository.save(newBreak);
        UserTimeRecord timeRecord = userTimeRecordsRepository.findByDateAndUser(today,userName)
                .orElseThrow(()->new EntityNotFoundException("Нет записи оработки на сегодня"));
        timeRecord.setStatus(WORKPAUSED_STATUS);
        userTimeRecordsRepository.save(timeRecord);

        return WORKPAUSED_STATUS;
    }

    private void takeBreakChecks(String userName){
        //провервка что пауза ставится когда состояние работы "в работе"
        checkStatus(IN_WORK_STATUS, userName, "Неверное состояние: должно быть 'В работе'");
    }

    @Transactional
    public int finishWork(String userName){
        //work status should be IN WORK
        checkStatus(IN_WORK_STATUS, userName, "Неверное состояние: должно быть 'В работе'");

        LocalDate today = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        String engDayName = today.getDayOfWeek().toString();
        String rusDayName = DayNameConvertions.getRusDayName(engDayName);

        UserTimeRecord userTimeRecord = userTimeRecordsRepository
                .findByDateAndUser(today,userName)
                .orElseThrow(()->new EntityNotFoundException("Пользователь не работал сегодня"));

        Schedule userSchedule = scheduleService
                .getUserScheduleByDate(userName,rusDayName)
                .orElseThrow(()->new EntityNotFoundException("У пользоваетеля нет расписания на сегодняшний день ("+rusDayName+")"));

        //если переработал
        LocalTime scheduleEndTime = userSchedule.getDayShift().getEnd();
        if(timeNow.compareTo(scheduleEndTime)==1){
            LocalTime overworkedTime = timeNow.minusHours(scheduleEndTime.getHour());
            overworkedTime = overworkedTime.minusMinutes(scheduleEndTime.getMinute());
            overworkedTime = overworkedTime.minusSeconds(scheduleEndTime.getSecond());

            LocalTime prevOverworkedTime = userTimeRecord.getOverworkedTime();
            overworkedTime.plusHours(prevOverworkedTime.getHour());
            overworkedTime.plusMinutes(prevOverworkedTime.getMinute());
            overworkedTime.plusSeconds(prevOverworkedTime.getSecond());

            userTimeRecord.setOverworkedTime(overworkedTime);
        }

        //если недоработал
        if(timeNow.compareTo(scheduleEndTime)==-1){
            LocalTime underworkedTime = scheduleEndTime.minusHours(timeNow.getHour());
            underworkedTime = underworkedTime.minusMinutes(timeNow.getMinute());
            underworkedTime = underworkedTime.minusSeconds(timeNow.getSecond());

            LocalTime prevUnderworkedTime = userTimeRecord.getUnderworkedTime();
            underworkedTime.plusHours(prevUnderworkedTime.getHour());
            underworkedTime.plusMinutes(prevUnderworkedTime.getMinute());
            underworkedTime.plusSeconds(prevUnderworkedTime.getSecond());

            userTimeRecord.setUnderworkedTime(underworkedTime);
        }

        UserTimeRecord timeRecord = userTimeRecordsRepository.findByDateAndUser(today,userName)
                .orElseThrow(()->new EntityNotFoundException("Нет записи оработки на сегодня"));
        timeRecord.setStatus(WORKENDED_STATUS);
        userTimeRecordsRepository.save(timeRecord);

        return WORKENDED_STATUS;
    }


    private void checkStatus(int status, String userName, String msg){
        if(getWorkStatus(userName)!=status){
            throw new EntityNotFoundException(msg);
        }
    }

    @Transactional
    public int resume(String userName){
        //work status should be IN PAUSE
        checkStatus(WORKPAUSED_STATUS, userName, "Неверное состояние: должно быть 'Приостановлено'");

        LocalDate today = LocalDate.now();
        LocalTime timeNow = LocalTime.now();
        String engDayName = today.getDayOfWeek().toString();
        String rusDayName = DayNameConvertions.getRusDayName(engDayName);

        //Изменить время окончания паузы
        List<Break> breakList = breakRepository.findLatestByUserAndDay(userName,today, PageRequest.of(0,1));
        if(breakList.size()==0){
            throw new EntityNotFoundException("Перерыв не найден");
        }
        Break userBreak = breakList.get(0);
        userBreak.setEnd(timeNow);
        breakRepository.save(userBreak);

        UserTimeRecord timeRecord = userTimeRecordsRepository.findByDateAndUser(today,userName)
                .orElseThrow(()->new EntityNotFoundException("Нет записи оработки на сегодня"));
        timeRecord.setStatus(IN_WORK_STATUS);
        userTimeRecordsRepository.save(timeRecord);

        return IN_WORK_STATUS;
    }
}
