package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.dto.UserWeekScheduleDTO;
import org.example.employeetimetrackingservice.entities.Schedule;
import org.example.employeetimetrackingservice.services.ScheduleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/userSchedules")
public class UserScheduleController {
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public UserWeekScheduleDTO getUserWeekSchedule(@RequestParam String userName,
                                                   @RequestParam Long weeksToSkip){
        return scheduleService.getUsersSchedule(userName, weeksToSkip);
    }

    @GetMapping("/chosen")
    public List<Schedule> getUserChosenSchedule(@RequestParam String userName){
        return scheduleService.getUserSchedules(userName);
    }
    @PostMapping
    public Schedule addScheduleForUser(@RequestParam String userName,
                                       @RequestParam Long departmentDayScheduleId){
        return scheduleService.add(userName, departmentDayScheduleId);
    }
    @DeleteMapping
    public void deleteUserSchedule(@RequestParam Long userScheduleId){
        scheduleService.delete(userScheduleId);
    }

}
