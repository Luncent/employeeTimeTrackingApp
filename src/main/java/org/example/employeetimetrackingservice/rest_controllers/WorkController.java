package org.example.employeetimetrackingservice.rest_controllers;


import jakarta.persistence.EntityNotFoundException;
import org.example.employeetimetrackingservice.entities.Schedule;
import org.example.employeetimetrackingservice.services.ScheduleService;
import org.example.employeetimetrackingservice.services.WorkService;
import org.example.employeetimetrackingservice.utils.DayNameConvertions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Optional;

@RestController
@RequestMapping("/work")
public class WorkController {
    @Autowired
    private WorkService workService;
    @Autowired
    private ScheduleService scheduleService;

    @GetMapping
    public ResponseEntity<Integer> getWorkStatus(@RequestParam String userName){
        return ResponseEntity.ok(workService.getWorkStatus(userName));
    }

    @PostMapping("/start")
    public ResponseEntity<Integer> start(@RequestParam String userName){
        return ResponseEntity.ok(workService.startWork(userName));
    }

    @PostMapping("/pause")
    public ResponseEntity<Integer> pause(@RequestParam String userName){
        return ResponseEntity.ok(workService.takeBreak(userName));
    }

    @PostMapping("/resume")
    public ResponseEntity<Integer> resume(@RequestParam String userName){
        return ResponseEntity.ok(workService.resume(userName));
    }

    @PostMapping("/finish")
    public ResponseEntity<Integer> finishWork(@RequestParam String userName){
        return ResponseEntity.ok(workService.finishWork(userName));
    }

    @GetMapping("/schedule/{userName}")
    public Schedule getUserScheduleByDate(@PathVariable String userName){
        LocalDate now = LocalDate.now();
        String rusDayName = DayNameConvertions.getRusDayName(now.getDayOfWeek().toString());
        Optional<Schedule> userSchedule =  scheduleService.getUserScheduleByDate(userName, rusDayName);
        return userSchedule.orElseThrow(()->new EntityNotFoundException("Расписания на сегодня нет"));
    }
}
