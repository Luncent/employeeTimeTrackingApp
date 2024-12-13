package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@AllArgsConstructor
public class DaySchedulesDTO {
    private String dayName;
    private List<ScheduleDTO> schedules;

    public DaySchedulesDTO(){
        this.schedules = new ArrayList<>();
    }
}
