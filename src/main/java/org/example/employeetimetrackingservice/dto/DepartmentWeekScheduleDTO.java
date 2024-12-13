package org.example.employeetimetrackingservice.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DepartmentWeekScheduleDTO {
    private Long departmentId;
    private Boolean isComplete;
    private List<DaySchedulesDTO> daySchedulesDTOList = new ArrayList<>();
}
