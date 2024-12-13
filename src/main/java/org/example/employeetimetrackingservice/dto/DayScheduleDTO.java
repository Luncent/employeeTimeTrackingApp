package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DayScheduleDTO {
    private boolean isHoliday;
    private LocalDate date;
    private String dayName;
    private LocalTime start;
    private LocalTime end;
}
