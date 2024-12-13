package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Break;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.Vacation;
import org.example.employeetimetrackingservice.repositories.BreakRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.example.employeetimetrackingservice.repositories.VacationRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

@SpringBootTest
public class BreakRepositoryTest {
    @Autowired
    private UserRepository userRep;
    @Autowired
    private BreakRepository breakRepo;

    @Test
    public void all(){
        System.out.println(breakRepo.findAll());
    }
    @Test
    @Transactional
    public void add(){
        User user = userRep.getReferenceById(2L);
        Break newbreak = Break.builder()
                .date(LocalDate.now())
                .dayName("Понедельник")
                .end(LocalTime.now())
                .start(LocalTime.now())
                .user(user)
                .build();

        System.out.println(breakRepo.save(newbreak));
    }
    @Test
    @Transactional
    public void update(){
        Break breakk = breakRepo.getReferenceById(3L);
        System.out.println(breakk);
        breakk.setStart(LocalTime.now());
        System.out.println(breakRepo.save(breakk));
    }

    @Test
    @Transactional
    public void selectAllWithCondition(){
        System.out.println(breakRepo.findAllByUserId(1L));
    }
}
