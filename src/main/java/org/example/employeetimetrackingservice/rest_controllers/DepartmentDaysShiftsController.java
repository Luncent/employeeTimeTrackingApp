package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.dto.DepartmentWeekScheduleDTO;
import org.example.employeetimetrackingservice.entities.DepartmentDayShift;
import org.example.employeetimetrackingservice.services.DepartmentsDayShiftsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalTime;

@RestController
@RequestMapping("/departmentDaysShifts")
public class DepartmentDaysShiftsController {
    @Autowired
    private DepartmentsDayShiftsService departmentsDayShiftsService;

    @GetMapping
    public DepartmentWeekScheduleDTO getWeekSchedule(@RequestParam Long departmentId){
        return departmentsDayShiftsService.findWeekScheduleByDepartmentId(departmentId);
    }
    @PostMapping
    public DepartmentDayShift getWeekSchedule(@RequestParam Long departmentId,
                                              @RequestParam String dayName,
                                              @RequestParam LocalTime start,
                                              @RequestParam LocalTime end){
        return departmentsDayShiftsService.save(departmentId,dayName,start,end);
    }
    @DeleteMapping
    public void deleteDepartmentSchedule(@RequestParam Long dayShiftId){
        departmentsDayShiftsService.delete(dayShiftId);
    }
}
