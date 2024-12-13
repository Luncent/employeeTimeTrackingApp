package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.entities.Vacation;
import org.example.employeetimetrackingservice.services.VacationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/vacations")
public class VacationController {
    @Autowired
    private VacationService vacationService;

    @GetMapping
    public List<Vacation> findAllAwaitingByDepartment(@RequestParam Long departmentId){
        return vacationService.findAllAwaitingByDepartmentId(departmentId);
    }

    @GetMapping("{userName}")
    public List<Vacation> findAllByUser(@PathVariable String userName){
        return vacationService.findAllByUser(userName);
    }

    @GetMapping("{userName}/{departmentId}")
    public List<Vacation> findAllByUser(@PathVariable String userName,
                                        @PathVariable Long departmentId){
        return vacationService.findAllAwaitingByUserAndDepartment(userName,departmentId);
    }

    @PostMapping
    public Vacation add(@RequestParam Long userId,
                        @RequestParam LocalDate start,
                        @RequestParam LocalDate end){
        return vacationService.save(start, end, userId);
    }

    @PutMapping
    public Vacation update(@RequestParam Long vacationId, int status){
        return vacationService.update(vacationId, status);
    }

    @DeleteMapping
    public void delete(@RequestParam Long vacationId){
        vacationService.deleteRejected(vacationId);
    }
}
