package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.Holiday;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departments.DepartmentAddEditException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions.HolidayAddEditException;
import org.example.employeetimetrackingservice.services.DepartmentService;
import org.example.employeetimetrackingservice.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @GetMapping
    public List<Department> getAll(){
        return departmentService.getAll();
    }

    @PostMapping
    public Department save(@RequestParam String name){
        if (name == null || name.trim().isEmpty()) {
            throw new DepartmentAddEditException("имя");
        }
        return departmentService.save(name);
    }

    @PutMapping
    public Department update(@RequestParam Long id,
                          @RequestParam String name){
        if (name == null || name.trim().isEmpty()) {
            throw new HolidayAddEditException("имя");
        }
        return departmentService.update(id,name);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        departmentService.delete(id);
    }
}
