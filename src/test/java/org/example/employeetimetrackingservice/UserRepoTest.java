package org.example.employeetimetrackingservice;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.example.employeetimetrackingservice.repositories.RoleRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UserRepoTest {
    @Autowired
    private UserRepository rep;
    @Autowired
    private RoleRepository roleRepo;
    @Autowired
    private DepartmentRepository departmentRepo;
    @Test
    public void allDepartments(){
        System.out.println(rep.findAll());
    }
    @Test
    @Transactional
    public void add(){
        User user = new User();
        user.setName("Кадровый");
        user.setPassword("124");
        user.setDepartment(departmentRepo.getReferenceById(3L));
        user.setRole(roleRepo.getReferenceById(3L));
        System.out.println(rep.save(user));
    }
    @Test
    @Transactional
    public void update(){
        User user = rep.getReferenceById(3L);
        System.out.println(user);
        user.setName("Кадровый");
        System.out.println(rep.save(user));
    }
    @Test
    @Transactional
    public void find(){
        User user = rep.findByName("Елена Сидорова").orElseThrow(()-> new EntityNotFoundException("not found"));
        System.out.println(user);
    }

    @Test
    @Transactional
    public void findByRoleTitle(){
        User user = rep.findByRoleIdAndDepartmentId(1L,1L).orElseThrow(()-> new EntityNotFoundException("not found"));
        System.out.println(user);
    }
}
