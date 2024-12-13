package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.UserHoliday;
import org.example.employeetimetrackingservice.entities.Vacation;
import org.example.employeetimetrackingservice.exceptions.myexceptions.vacation.VacationDateException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.vacation.VacationDeleteException;
import org.example.employeetimetrackingservice.repositories.UserHolidaysRepository;
import org.example.employeetimetrackingservice.repositories.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Service
public class VacationService {
    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private UserHolidaysRepository userHolidaysRepository;

    private static final int AWAITS = 1;
    private static final int ACCEPTED = 2;
    private static final int REJECTED = 3;
    private static final int PASSED = 4;

    private static final String REJECT_ACTION = "reject";
    private static final String APPROVE_ACTION = "approve";

    @Transactional
    public List<Vacation> findAllByUsername(String name){
        return vacationRepository.findAllByUsername(name);
    }

    @Transactional
    public List<Vacation> findAllAwaitingByDepartmentId(Long id){
        return vacationRepository.findAllWaitingByDepartmentId(id, AWAITS);
    }

    @Transactional
    public List<Vacation> findAllByUser(String userName){
        return vacationRepository.findAllByUsername(userName);
    }

    @Transactional
    public List<Vacation> findAllAwaitingByUserAndDepartment(String userName, Long depId){
        return vacationRepository.findAllWaitingByDepartmentIdAndUser(depId,AWAITS,userName);
    }

    @Transactional
    public List<Vacation> findAllActiveVacationsByDepartmentId(String userName){
        return vacationRepository.findAllActiveVacationsByUsername(userName);
    }

    @Transactional
    public Vacation save(LocalDate start, LocalDate end, Long userId){
        LocalDate today = LocalDate.now();
        if(today.isAfter(start)){
            throw new VacationDateException("Нельзя взять отпуск на прошедшие дни");
        }
        if (end.isBefore(start) || start.equals(end)) {
            throw new VacationDateException("Начало не может быть позже конца");
        }
        //проверка на кол-во доступных дней
        UserHoliday userHoliday = userHolidaysRepository.findByUserId(userId)
                .orElseThrow(()->new EntityNotFoundException("У пользователя нету записи доступных отпусков"));
        int availableDays = userHoliday.getDays();
        int daysForVacation = (int)ChronoUnit.DAYS.between(start,end)+1;
        if(availableDays<daysForVacation){
            throw new VacationDateException("Количество запрашиваемых дней отпуска превышает доступные "+availableDays);
        }
        else {
            userHoliday.setDays(availableDays-daysForVacation);
            userHolidaysRepository.save(userHoliday);
        }

        User user = userService.findById(userId);
        Vacation vacation = Vacation.builder()
                .status(AWAITS)
                .start(start)
                .end(end)
                .user(user)
                .build();
        return vacationRepository.save(vacation);
    }

    @Transactional
    public Vacation update(Long vacationId, int status){
        Vacation vacation = vacationRepository.findById(vacationId).orElseThrow(()->new EntityNotFoundException("Отпуск с таким id не найден"));
        switch (status){
            case ACCEPTED-> vacation.setStatus(ACCEPTED);
            case REJECTED -> vacation.setStatus(REJECTED);
            case PASSED -> vacation.setStatus(PASSED);
        }
        return vacationRepository.save(vacation);
    }

    @Transactional
    public void deleteRejected(Long id){
        Vacation vacation =vacationRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Отпуск с таким id не найден"));
        if(vacation.getStatus()==REJECTED || vacation.getStatus()==AWAITS){
            vacationRepository.deleteById(id);
            return;
        }
        throw new VacationDeleteException("Нельзя удалить отпуск который не был отклонен");
    }
}
