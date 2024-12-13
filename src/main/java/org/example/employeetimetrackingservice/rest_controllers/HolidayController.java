package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.entities.Holiday;
import org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions.HolidayAddEditException;
import org.example.employeetimetrackingservice.services.HolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/holidays")
public class HolidayController {

    @Autowired
    private HolidayService holidayService;

    @GetMapping
    public List<Holiday> getAll(){
        return holidayService.getAll();
    }

    @PostMapping
    public Holiday save(@RequestParam String name,
                        @RequestParam String date){
        if (name == null || name.trim().isEmpty()) {
            throw new HolidayAddEditException("имя");
        }
        if (date == null || date.trim().isEmpty()) {
            throw new HolidayAddEditException("дата");
        }
        if (date.length()!=5) {
            throw new HolidayAddEditException("длина даты должна быть равна 5");
        }
        return holidayService.save(name,date);
    }

    @PutMapping
    public Holiday update(@RequestParam Long id,
                   @RequestParam String name,
                   @RequestParam String date){
        if (name == null || name.trim().isEmpty()) {
            throw new HolidayAddEditException("имя");
        }
        if (date == null || date.trim().isEmpty()) {
            throw new HolidayAddEditException("дата");
        }
        if (date.length()!=5) {
            throw new HolidayAddEditException("длина даты должна быть равна 5");
        }
        return holidayService.update(id,name,date);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        holidayService.delete(id);
    }
}
