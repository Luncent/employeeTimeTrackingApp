package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.UserHoliday;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departments.DepartmentExistsException;
import org.example.employeetimetrackingservice.repositories.UserHolidaysRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserHolidaysService {
    @Autowired
    private UserHolidaysRepository userHolidaysRepository;

    @Transactional
    public UserHoliday getAllUserHolidays(Long userId) {
        return userHolidaysRepository.findByUserId(userId).orElseThrow(()->new EntityNotFoundException("Выходные дни для полльзователя не былы найдены"));
    }

}
