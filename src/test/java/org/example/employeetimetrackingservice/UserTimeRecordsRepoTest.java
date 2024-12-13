package org.example.employeetimetrackingservice;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.UserTimeRecord;
import org.example.employeetimetrackingservice.repositories.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.example.employeetimetrackingservice.utils.DayNameConvertions.getRusDayName;

@SpringBootTest
public class UserTimeRecordsRepoTest {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private UserTimeRecordsRepository repo;
    @Autowired
    private ScheduleRepository scheduleRepository;
    @Autowired
    private BreakRepository breakRepository;
    @Autowired
    private VacationRepository vacationRepository;

    @Test
    public void all(){
        System.out.println(repo.findAll());
    }

    @Test
    @Transactional
    public void findByDate(){
        System.out.println(repo.findByDateAndUser(LocalDate.of(2024,11,1)
                ,"Иван Иванов").get());
    }
    @Test
    @Transactional
    public void add(){
        User user = userRepository.getReferenceById(2L);
        UserTimeRecord userTimeRecord = UserTimeRecord.builder()
                .user(user)
                .dayName("Понедельник")
                .delay(LocalTime.now())
                .overworkedTime(LocalTime.now())
                .underworkedTime(LocalTime.now())
                .date(LocalDate.now())
                .build();

        System.out.println(repo.save(userTimeRecord));
    }
    @Test
    @Transactional
    public void update(){
        UserTimeRecord userTimeRecord = repo.findById(43L).get();
        System.out.println(userTimeRecord);
        userTimeRecord.setDelay(LocalTime.now());
        System.out.println(repo.save(userTimeRecord));
    }

    @Test
    @Transactional
    public void testGetUserScheduleByDate(){
        System.out.println(scheduleRepository
                .findAllByUserAndDay("Иван Иванов","Понедельник").get());
    }

    @Test
    @Transactional
    public void testGetDayName(){
        System.out.println(getRusDayName(LocalDate.now().getDayOfWeek().toString()));
    }

    @Test
    @Transactional
    public void testGet(){
        LocalTime currentTime = LocalTime.of(12,40,10);
        LocalTime startTime = LocalTime.of(8,50,10);
        LocalTime overwork = currentTime.plusHours(startTime.getHour());
        overwork = overwork.plusMinutes(startTime.getMinute());
        overwork=overwork.plusSeconds(startTime.getSecond());
        System.out.println(overwork);
    }

    @Test
    @Transactional
    public void testGetLastBreak(){
        //breakRepository.findLatestByUserAndDay("")
    }

    @Test
    public void tetse(){
        LocalDate today  = LocalDate.now();
        LocalDate monthAgo = today.minusDays(10);
        repo.findAllBetweenDatesByUser(monthAgo,today.minusDays(1),"Иван Иванов")
                .forEach(System.out::println);
    }

    @Test
    public void tetse2(){
        LocalDate today  = LocalDate.now();
        LocalDate monthAgo = today.minusDays(10);
        today = today.minusDays(1);
        breakRepository.findAllBetweenDatesByUser("Иван Иванов",monthAgo,today)
                .forEach(System.out::println);
    }

    @Test
    public void tetse3(){
        System.out.println(66%60);
    }



}
