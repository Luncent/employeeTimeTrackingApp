package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.dto.DepartmentWeekScheduleDTO;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.DepartmentDayShift;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departmentdayshift.DepartmentDayShiftTimeException;
import org.example.employeetimetrackingservice.mappers.WeekSchedulesMapper;
import org.example.employeetimetrackingservice.repositories.DepartmentDayShiftRepository;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.example.employeetimetrackingservice.utils.DayNames;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.Duration;
import java.time.LocalTime;
import java.util.List;

@Service
public class DepartmentsDayShiftsService {
    @Autowired
    private DepartmentDayShiftRepository departmentDayShiftRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private WeekSchedulesMapper weekSchedulesMapper;

    private static final int SCHEDULE_TIME = 510;

    @Transactional
    public DepartmentWeekScheduleDTO findWeekScheduleByDepartmentId(Long departmentId){
        List<DepartmentDayShift> departmentDayShifts = departmentDayShiftRepository.findAllByDepartmentId(departmentId);
        return weekSchedulesMapper.convert(departmentDayShifts, departmentId);
    }

    @Transactional
    public DepartmentDayShift save(Long departmentId, String dayName, LocalTime start, LocalTime end){
        if (Duration.between(start, end).toMinutes() != SCHEDULE_TIME) {
            throw new DepartmentDayShiftTimeException("Время смены должно составлять 8 часов 30 минут");
        }
        if (end.isBefore(start) || start.equals(end)) {
            throw new DepartmentDayShiftTimeException("Начало не может быть позже конца смены");
        }
        if(!DayNames.correctDayName(dayName)){
            throw new DepartmentDayShiftTimeException("Неверное имя дня "+dayName);
        }
        Department department = departmentRepository.findById(departmentId).orElseThrow(()->new EntityNotFoundException("Департамент не найден"));
        DepartmentDayShift departmentDayShift = DepartmentDayShift.builder()
                .dayName(dayName)
                .start(start)
                .end(end)
                .department(department)
                .build();
        return departmentDayShiftRepository.save(departmentDayShift);
    }

    @Transactional
    public void delete(Long id){
        departmentDayShiftRepository.deleteById(id);
    }
}
