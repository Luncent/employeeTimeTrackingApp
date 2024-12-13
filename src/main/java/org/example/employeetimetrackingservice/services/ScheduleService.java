package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.dto.UserWeekScheduleDTO;
import org.example.employeetimetrackingservice.entities.*;
import org.example.employeetimetrackingservice.exceptions.myexceptions.schedule.ScheduleExistsException;
import org.example.employeetimetrackingservice.mappers.WeekSchedulesMapper;
import org.example.employeetimetrackingservice.repositories.DepartmentDayShiftRepository;
import org.example.employeetimetrackingservice.repositories.ScheduleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ScheduleService {
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private HolidayService holidayService;
    @Autowired
    private VacationService vacationService;
    @Autowired
    private WeekSchedulesMapper weekSchedulesMapper;
    @Autowired
    private DepartmentDayShiftRepository departmentDayShiftRepository;
    @Autowired
    private UserService userService;

    @Transactional
    public UserWeekScheduleDTO getUsersSchedule(String userName, Long weeksSkip){
        List<Vacation> activeVacations = vacationService.findAllActiveVacationsByDepartmentId(userName);
        List<Holiday> holidays = holidayService.getAll();
        List<Schedule> schedules = scheduleRepository.findAllByUserName(userName);
        return weekSchedulesMapper.convert(activeVacations,holidays,schedules,weeksSkip);
    }

    @Transactional
    public List<Schedule> getUserSchedules(String userName){
        return scheduleRepository.findAllByUserName(userName);
    }

    @Transactional
    public Optional<Schedule> getUserScheduleByDate(String userName, String dayName){
        return scheduleRepository
                .findAllByUserAndDay(userName,dayName);
    }

    @Transactional
    public Schedule add(String userName, Long departmentDayScheduleId){
        DepartmentDayShift dayShift = departmentDayShiftRepository.findById(departmentDayScheduleId)
                .orElseThrow(()->new EntityNotFoundException("У отдела нет такого расписания"));
        //пользователю нельзя иметь несколько расписаний на один день
        String dayName = dayShift.getDayName();
        if(scheduleRepository.findAllByUserAndDay(userName, dayName).isPresent()){
            throw new ScheduleExistsException("Расписание на указанный день уже есть");
        }

        User user = userService.findByName(userName);
        Schedule schedule = Schedule.builder()
                .dayShift(dayShift).
                user(user)
                .build();

        return scheduleRepository.save(schedule);
    }

    @Transactional
    public void delete(Long userScheduleId){
        scheduleRepository.deleteById(userScheduleId);
    }
}
