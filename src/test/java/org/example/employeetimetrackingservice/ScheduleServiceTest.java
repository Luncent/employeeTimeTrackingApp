package org.example.employeetimetrackingservice;

import org.example.employeetimetrackingservice.services.ScheduleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalTime;

@SpringBootTest
public class ScheduleServiceTest {
    @Autowired
    private ScheduleService scheduleService;
    @Test
    public void test(){
        System.out.println(scheduleService.getUsersSchedule("Иван Иванов",2L));
    }

    @Test
    public void testLT(){
        System.out.println(LocalTime.MIDNIGHT);
    }
}
