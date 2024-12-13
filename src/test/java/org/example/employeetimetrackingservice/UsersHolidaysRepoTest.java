package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.UserHoliday;
import org.example.employeetimetrackingservice.repositories.UserHolidaysRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class UsersHolidaysRepoTest {
    @Autowired
    private UserRepository userRep;
    @Autowired
    private UserHolidaysRepository userHolidaysRepository;

    @Test
    public void all(){
        System.out.println(userHolidaysRepository.findAll());
    }

    @Test
    public void getUsersAll(){
        System.out.println(userHolidaysRepository.findByUserId(1L).get());
    }
    @Test
    @Transactional
    public void add(){
        User user = userRep.getReferenceById(2L);
        UserHoliday userHolidays = UserHoliday.builder()
                .days(20)
                .user(user)
                .build();
        System.out.println(userHolidaysRepository.save(userHolidays));
    }
    @Test
    @Transactional
    public void update(){
        UserHoliday userHolidays = userHolidaysRepository.getReferenceById(3L);
        System.out.println(userHolidays);
        userHolidays.setDays(1);
        System.out.println(userHolidaysRepository.save(userHolidays));
    }
}
