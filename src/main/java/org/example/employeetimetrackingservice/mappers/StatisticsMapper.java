package org.example.employeetimetrackingservice.mappers;

import org.example.employeetimetrackingservice.dto.DepartmentStatistics;
import org.example.employeetimetrackingservice.dto.UserStatistics;
import org.example.employeetimetrackingservice.entities.Break;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.UserTimeRecord;
import org.springframework.stereotype.Component;

import java.time.LocalTime;
import java.time.temporal.ChronoUnit;
import java.util.List;

@Component
public class StatisticsMapper {
    private static final int BREAK_NORM = 30;
    private static final int WORK_DAYS_NORM_FOR_TEN_DAYS = 8;

    public UserStatistics convert(List<UserTimeRecord> workRecords, List<Break> breaks, int vacationDays){
        UserStatistics userStatistics = new UserStatistics();

        int lateDaysNumber = 0;
        int lateMinutesSum = 0;

        int overworkDaysNumber = 0;
        int overworkMinutesSum = 0;

        int underWorkDaysNumber = 0;
        int underWorkMinutesSum = 0;

        int workDays = 0;

        LocalTime zero = LocalTime.of(0,0,0);

        for(UserTimeRecord record : workRecords){
            if(!record.getDelay().equals(zero)){
                lateDaysNumber++;
                lateMinutesSum+=record.getDelay().getHour()*60;
                lateMinutesSum+=record.getDelay().getMinute();
            }
            if(!record.getOverworkedTime().equals(zero)){
                overworkDaysNumber++;
                overworkMinutesSum+=record.getOverworkedTime().getHour()*60;
                overworkMinutesSum+=record.getOverworkedTime().getMinute();
            }
            if(!record.getUnderworkedTime().equals(zero)){
                underWorkDaysNumber++;
                underWorkMinutesSum+=record.getUnderworkedTime().getHour()*60;
                underWorkMinutesSum+=record.getUnderworkedTime().getMinute();
            }
            workDays++;
        }

        int breakTimeInMinutes=0;
        int breakTimeNorm = BREAK_NORM*workDays;
        for(Break userBreak : breaks){
            breakTimeInMinutes+= (int) ChronoUnit.MINUTES.between(userBreak.getStart(),userBreak.getEnd());
        }



        int workedTimeInMinutes= (workDays+vacationDays)*8*60+overworkMinutesSum-underWorkMinutesSum-lateMinutesSum-(breakTimeInMinutes-breakTimeNorm);

        userStatistics.setLateNumber(lateDaysNumber);
        userStatistics.setLateTimeInMinutes(lateMinutesSum);

        userStatistics.setOverworkDaysNumber(overworkDaysNumber);
        userStatistics.setOverworkTimeInMinutes(overworkMinutesSum);

        userStatistics.setUnderWorkDaysNumber(underWorkDaysNumber);
        userStatistics.setUnderWorkTimeInMinutes(underWorkMinutesSum);

        userStatistics.setBreakTimeInMinutes(breakTimeInMinutes);
        userStatistics.setBreakTimeNorm(breakTimeNorm);

        userStatistics.setDaysInVacation(vacationDays);

        double percent = (double) workedTimeInMinutes /(WORK_DAYS_NORM_FOR_TEN_DAYS*8*60);
        int overallWorkedTimeHours = workedTimeInMinutes/60;
        workedTimeInMinutes = workedTimeInMinutes % 60;

        userStatistics.setOverallWorkedTimeMinutes(workedTimeInMinutes);
        userStatistics.setOverallWorkedTimeHours(overallWorkedTimeHours);
        userStatistics.setOverallWorkedTimeNormInHours(WORK_DAYS_NORM_FOR_TEN_DAYS*8);
        userStatistics.setPercentOfWorkNorm(percent);

        return userStatistics;
    }

    public DepartmentStatistics convert(List<UserStatistics> userStatistics){
        DepartmentStatistics departmentStatistics = new DepartmentStatistics();

        int lateTimeMinutes = 0;
        int overworkTimeMinutes = 0;
        int underWorkTimeMinutes = 0;
        int daysInVacation = 0;
        double percentOfWorkSum = 0;

        for(UserStatistics userStatistic : userStatistics){
            lateTimeMinutes+=userStatistic.getLateTimeInMinutes();
            overworkTimeMinutes+=userStatistic.getOverworkTimeInMinutes();
            underWorkTimeMinutes+=userStatistic.getUnderWorkTimeInMinutes();
            daysInVacation+=userStatistic.getDaysInVacation();
            percentOfWorkSum+=userStatistic.getPercentOfWorkNorm();
        }

        departmentStatistics.setWorkersQuantity(userStatistics.size());
        departmentStatistics.setLateTimeInMinutes(lateTimeMinutes);
        departmentStatistics.setOverworkTimeInMinutes(overworkTimeMinutes);
        departmentStatistics.setUnderWorkTimeInMinutes(underWorkTimeMinutes);
        departmentStatistics.setDaysInVacation(daysInVacation);
        departmentStatistics.setPercentOfWork(percentOfWorkSum/ userStatistics.size());

        return departmentStatistics;
    }
}
