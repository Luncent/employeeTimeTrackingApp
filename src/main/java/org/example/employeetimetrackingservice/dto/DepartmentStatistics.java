package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class DepartmentStatistics {
    private int workersQuantity;
    private int lateTimeInMinutes;
    private int overworkTimeInMinutes;
    private int underWorkTimeInMinutes;
    private int daysInVacation;
    
    private double percentOfWork;
}
