package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Holiday;
import org.example.employeetimetrackingservice.repositories.HolidayRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class HolidayRepoTest {
    @Autowired
    private HolidayRepository rep;
    @Test
    public void allDepartments(){
        System.out.println(rep.findAll());
    }
    @Test
    public void getDepartmentsByDate(){
        System.out.println(rep.findByName("Новый год"));
    }
    @Test
    public void addDepartments(){
        Holiday newHoliday = new Holiday();
        newHoliday.setName("9 мая");
        newHoliday.setDate("09-05");
        System.out.println(rep.save(newHoliday));
    }
    @Test
    @Transactional
    public void updateDepartments(){
        Holiday holiday = rep.getReferenceById(3L);
        System.out.println(holiday);
        holiday.setName("Кадровый");
        System.out.println(rep.save(holiday));
    }
}
