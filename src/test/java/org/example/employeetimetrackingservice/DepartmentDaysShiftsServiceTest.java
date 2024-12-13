package org.example.employeetimetrackingservice;

import org.example.employeetimetrackingservice.services.DepartmentsDayShiftsService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class DepartmentDaysShiftsServiceTest {
    @Autowired
    private DepartmentsDayShiftsService departmentsDayShiftsService;

    @Test
    public void test(){
        System.out.println(departmentsDayShiftsService.findWeekScheduleByDepartmentId(1L));
    }

}
