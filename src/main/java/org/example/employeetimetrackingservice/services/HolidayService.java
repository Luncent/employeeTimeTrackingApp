package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.dto.UserSavedDTO;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.Holiday;
import org.example.employeetimetrackingservice.entities.Role;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions.HolidayExistsException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UserManagerAlreadyTaken;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UsernameAlreadyTakenException;
import org.example.employeetimetrackingservice.mappers.UserMapper;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.example.employeetimetrackingservice.repositories.HolidayRepository;
import org.example.employeetimetrackingservice.repositories.RoleRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class HolidayService {
    @Autowired
    private HolidayRepository holidayRepository;

    @Transactional
    public List<Holiday> getAll() {
        return holidayRepository.findAll();
    }

    @Transactional
    public Holiday findById(Long id) {
        return holidayRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Праздник не найден"));
    }

    @Transactional
    public Holiday save(String name, String date) {
        if(holidayRepository.findByName(name).isPresent()){
            throw new HolidayExistsException("Праздник с названием '"+name+"' существует");
        }
        if(holidayRepository.findByDate(date).isPresent()){
            throw new HolidayExistsException("В указанную дату '"+date+"' существует праздник");
        }
        Holiday holiday = Holiday.builder()
                .date(date)
                .name(name)
                .build();
        return holidayRepository.save(holiday);
    }

    @Transactional
    public Holiday update(Long id, String name, String date) {
        Optional<Holiday> holidayByName = holidayRepository.findByName(name);
        if(holidayByName.isPresent() && holidayByName.get().getId()!=id){
            throw new HolidayExistsException("Праздник с названием '"+name+"' существует");
        }
        Optional<Holiday> holidayByDate = holidayRepository.findByDate(date);
        if(holidayByDate.isPresent() && holidayByDate.get().getId()!=id){
            throw new HolidayExistsException("В указанную дату '"+date+"' существует праздник");
        }
        Holiday holiday = holidayRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Праздник не найден"));
        holiday.setDate(date);
        holiday.setName(name);
        return holidayRepository.save(holiday);
    }

    @Transactional
    public void delete(Long id) {
        holidayRepository.deleteById(id);
    }
}
