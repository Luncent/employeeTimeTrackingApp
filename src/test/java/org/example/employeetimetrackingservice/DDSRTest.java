package org.example.employeetimetrackingservice;


import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Break;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.DepartmentDayShift;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.repositories.BreakRepository;
import org.example.employeetimetrackingservice.repositories.DepartmentDayShiftRepository;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

@SpringBootTest
public class DDSRTest {
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private DepartmentDayShiftRepository repo;

    @Test
    public void all(){
        System.out.println(repo.findAll());
    }
    @Test
    public void getAllByDepartment(){
        System.out.println(repo.findAllByDepartmentId(1L));
    }
    @Test
    @Transactional
    public void add(){
        Department department = departmentRepository.getReferenceById(2L);
        DepartmentDayShift departmentDayShift = DepartmentDayShift.builder()
                .dayName("Понедельник")
                .department(department)
                .start(LocalTime.now())
                .end(LocalTime.now())
                .build();

        System.out.println(repo.save(departmentDayShift));
    }
    @Test
    @Transactional
    public void update(){
        DepartmentDayShift departmentDayShift = repo.getReferenceById(3L);
        System.out.println(departmentDayShift);
        departmentDayShift.setStart(LocalTime.now());
        System.out.println(repo.save(departmentDayShift));
    }

    @Test
    @Transactional
    public void selectAllWithCondition(){
        List<DepartmentDayShift> departmentDayShifts = repo.findAllByDepartmentId(1L);
        departmentDayShifts.forEach(System.out::println);
    }
}
