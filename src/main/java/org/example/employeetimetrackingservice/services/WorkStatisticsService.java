package org.example.employeetimetrackingservice.services;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.dto.DepartmentStatistics;
import org.example.employeetimetrackingservice.dto.UserStatistics;
import org.example.employeetimetrackingservice.entities.Break;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.UserTimeRecord;
import org.example.employeetimetrackingservice.mappers.StatisticsMapper;
import org.example.employeetimetrackingservice.repositories.BreakRepository;
import org.example.employeetimetrackingservice.repositories.UserTimeRecordsRepository;
import org.example.employeetimetrackingservice.repositories.VacationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Service
public class WorkStatisticsService {
    @Autowired
    private UserTimeRecordsRepository userTimeRecordsRepository;
    @Autowired
    private UserService userService;
    @Autowired
    private BreakRepository breakRepository;
    @Autowired
    private VacationRepository vacationRepository;
    @Autowired
    private StatisticsMapper statisticsMapper;

    private static final int DAYS_FOR_STATISTICS = 10;

    @Transactional
    public UserStatistics getUserStatisticsForLastNDays(String userName){
        LocalDate today  = LocalDate.now();
        LocalDate monthAgo = today.minusDays(DAYS_FOR_STATISTICS);
        today = today.minusDays(1);
        List<UserTimeRecord> workRecords = userTimeRecordsRepository
                .findAllBetweenDatesByUser(monthAgo,today,userName);

        List<Break> breaks = breakRepository.findAllBetweenDatesByUser(userName,monthAgo,today);

        int vacationDays = vacationRepository.findVacationDaysBetweenDates(userName, monthAgo,today)==null ? 0 :
                vacationRepository.findVacationDaysBetweenDates(userName, monthAgo,today).intValue();

        return statisticsMapper.convert(workRecords,breaks,vacationDays);
    }

    @Transactional
    public DepartmentStatistics getDepartmentStatistics(Long departmentId){
        List<User> workers =  userService.findByWorkersDepartment(departmentId);
        List<UserStatistics> userStatisticsList = new ArrayList<>();
        for(User worker : workers){
            userStatisticsList.add(getUserStatisticsForLastNDays(worker.getName()));
        }
        return statisticsMapper.convert(userStatisticsList);
    }
}
