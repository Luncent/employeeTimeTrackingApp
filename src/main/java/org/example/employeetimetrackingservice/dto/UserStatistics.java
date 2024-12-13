package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class UserStatistics {
    private int lateNumber;
    private int lateTimeInMinutes;

    private int overworkDaysNumber;
    private int overworkTimeInMinutes;

    private int underWorkDaysNumber;
    private int underWorkTimeInMinutes;

    private int breakTimeInMinutes;
    private int breakTimeNorm;
    
    private int daysInVacation;

    private int overallWorkedTimeMinutes;
    private int overallWorkedTimeHours;
    private int overallWorkedTimeNormInHours;
    private double percentOfWorkNorm;
}
