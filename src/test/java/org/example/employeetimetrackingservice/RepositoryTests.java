package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class RepositoryTests {
    @Autowired
    private DepartmentRepository depRep;
    @Test
    public void allDepartments(){
        System.out.println(depRep.findAll());
    }
    @Test
    public void addDepartments(){
        Department department = new Department();
        department.setName("Кадровый");
        System.out.println(depRep.save(department));
    }
    @Test
    @Transactional
    public void updateDepartments(){
        Department department = depRep.getReferenceById(3L);
        System.out.println(department);
        department.setName("Кадровый");
        System.out.println(depRep.save(department));
    }
}
