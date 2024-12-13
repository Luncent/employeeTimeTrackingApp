package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserWeekScheduleDTO {
    private boolean isComplete;
    private List<DayScheduleDTO> daysSchedules = new ArrayList<>();

    public boolean isHoliday(String dayName){
        for(DayScheduleDTO daySchedule : daysSchedules){
            if(daySchedule.getDayName().equals(dayName) && daySchedule.isHoliday()){
                return true;
            }
        }
        return false;
    }
}
