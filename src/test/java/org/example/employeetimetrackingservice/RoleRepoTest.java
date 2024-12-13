package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Break;
import org.example.employeetimetrackingservice.entities.Role;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.repositories.BreakRepository;
import org.example.employeetimetrackingservice.repositories.RoleRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class RoleRepoTest {
    @Autowired
    private UserRepository userRep;
    @Autowired
    private RoleRepository roleRepository;

    @Test
    public void all(){
        System.out.println(roleRepository.findAll());
    }
    @Test
    @Transactional
    public void add(){
        Role role = Role.builder().title("Кекс").build();

        System.out.println(roleRepository.save(role));
    }

    @Test
    @Transactional
    public void update(){
        Role role = roleRepository.getReferenceById(3L);
        System.out.println(role);
        role.setTitle("Менеджер");
        System.out.println(roleRepository.save(role));
    }

}
