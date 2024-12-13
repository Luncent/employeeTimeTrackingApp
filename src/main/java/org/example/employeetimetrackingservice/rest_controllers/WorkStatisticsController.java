package org.example.employeetimetrackingservice.rest_controllers;

import jakarta.persistence.EntityNotFoundException;
import org.example.employeetimetrackingservice.dto.DepartmentStatistics;
import org.example.employeetimetrackingservice.dto.UserStatistics;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.example.employeetimetrackingservice.services.DepartmentService;
import org.example.employeetimetrackingservice.services.UserService;
import org.example.employeetimetrackingservice.services.WorkStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/statistics")
public class WorkStatisticsController {
    @Autowired
    private WorkStatisticsService workStatisticsService;
    @Autowired
    private UserService userService;
    @Autowired
    private DepartmentRepository departmentRepository;

    @GetMapping("{userName}")
    public UserStatistics getUserStatistics(@PathVariable String userName){
        //exception if nptfound
        userService.findByName(userName);
        return workStatisticsService.getUserStatisticsForLastNDays(userName);
    }

    @GetMapping
    public DepartmentStatistics getUserStatistics(@RequestParam Long departmentId){
        if(!departmentRepository.findById(departmentId).isPresent()){
            throw new EntityNotFoundException("Отдел не найден");
        }
        return workStatisticsService.getDepartmentStatistics(departmentId);
    }
}
