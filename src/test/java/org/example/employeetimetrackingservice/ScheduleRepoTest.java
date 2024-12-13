package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.DepartmentDayShift;
import org.example.employeetimetrackingservice.entities.Schedule;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.Vacation;
import org.example.employeetimetrackingservice.repositories.DepartmentDayShiftRepository;
import org.example.employeetimetrackingservice.repositories.ScheduleRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.example.employeetimetrackingservice.repositories.VacationRepository;
import org.example.employeetimetrackingservice.services.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class ScheduleRepoTest {
    @Autowired
    private UserRepository userRep;
    @Autowired
    private ScheduleRepository scheduleRepo;
    @Autowired
    private DepartmentDayShiftRepository departmentDayShiftRepository;
    @Autowired
    private ScheduleService scheduleService;

    @Test
    public void all(){
        System.out.println(scheduleRepo.findAll());
    }

    @Test
    @Transactional
    public void getAllActive(){
        scheduleRepo.findAllByUserName("Иван Иванов")
                .stream()
                .forEach(System.out::println);
    }
    @Test
    @Transactional
    public void add(){
        scheduleService.add("Иван Иванов",2L);
    }
    /*@Test
    @Transactional
    public void update(){
        Schedule schedule = scheduleRepo.getReferenceById(3L);
        System.out.println(schedule);
        schedule.setEnd_time(LocalTime.now());
        System.out.println(scheduleRepo.save(schedule));
    }*/
}
