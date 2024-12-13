package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.Vacation;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.example.employeetimetrackingservice.repositories.VacationRepository;
import org.example.employeetimetrackingservice.services.VacationService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;

@SpringBootTest
public class VacationRepoTest {
    @Autowired
    private UserRepository userRep;
    @Autowired
    private VacationRepository vacationsRepo;
    @Autowired
    private VacationService vacationService;

    @Test
    public void all(){
        System.out.println(vacationsRepo.findAll());
    }

    @Test
    public void findAllBYUsername(){
        System.out.println(vacationsRepo.findAllByUsername("Иван Иванов"));
    }

    @Test
    public void findAllAwaitindByDepartmentId(){
        vacationsRepo.findAllWaitingByDepartmentId(1L,1).stream()
                .forEach(System.out::println);
    }

    @Test
    public void findAllActiveByDepartmentId(){
        vacationsRepo.findAllActiveVacationsByUsername("Иван Иванов").stream()
                .forEach(System.out::println);
    }

    @Test
    public void findAllAwaitingByDepartmentId(){
        vacationService.findAllAwaitingByDepartmentId(2L).forEach(System.out::println);
    }

    @Test
    @Transactional
    public void add(){
        Vacation vacation = new Vacation();
        vacation.setStatus(1);
        LocalDate date = LocalDate.now();
        vacation.setEnd(date);
        vacation.setStart(date);
        User user = userRep.getReferenceById(2L);
        vacation.setUser(user);
        System.out.println(vacationsRepo.save(vacation));
    }

    @Test
    @Transactional
    public void update(){
        Vacation vacation = vacationsRepo.getReferenceById(3L);
        System.out.println(vacation);
        vacation.setStart(LocalDate.now());
        System.out.println(vacationsRepo.save(vacation));
    }
}
