package org.example.employeetimetrackingservice.mappers;

import org.example.employeetimetrackingservice.dto.*;
import org.example.employeetimetrackingservice.entities.DepartmentDayShift;
import org.example.employeetimetrackingservice.entities.Holiday;
import org.example.employeetimetrackingservice.entities.Schedule;
import org.example.employeetimetrackingservice.entities.Vacation;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.example.employeetimetrackingservice.utils.DayNameConvertions.getDateByDayName;
import static org.example.employeetimetrackingservice.utils.DayNameConvertions.getThisYearDate;

@Component
public class WeekSchedulesMapper {
    private static final int WORKING_DAYS_COUNT = 5;

    public DepartmentWeekScheduleDTO convert(List<DepartmentDayShift> dayShifts, Long departmentID){
        DepartmentWeekScheduleDTO departmentWeekScheduleDTO = new DepartmentWeekScheduleDTO();
        departmentWeekScheduleDTO.setIsComplete(false);
        departmentWeekScheduleDTO.setDepartmentId(departmentID);
        if(dayShifts.size()==0){
            return departmentWeekScheduleDTO;
        }
        int daysWithSchedule = 0;
        //from db
        for(DepartmentDayShift dayShift : dayShifts){
            //dayOfWeek separation
            boolean containsThisDay = false;
            for(DaySchedulesDTO daySchedulesDTO : departmentWeekScheduleDTO.getDaySchedulesDTOList()){
               containsThisDay = daySchedulesDTO.getDayName().equals(dayShift.getDayName());
               if(containsThisDay){
                   daySchedulesDTO.getSchedules().add(new ScheduleDTO(dayShift.getId(),dayShift.getStart(),dayShift.getEnd()));
                   break;
               }
            }
            if(!containsThisDay){
                daysWithSchedule+=1;
                DaySchedulesDTO daySchedulesDTO = DaySchedulesDTO.builder()
                        .dayName(dayShift.getDayName())
                        .schedules(new ArrayList<>())
                        .build();
                ScheduleDTO scheduleDTO = new ScheduleDTO(dayShift.getId(),dayShift.getStart(),dayShift.getEnd());
                daySchedulesDTO.getSchedules().add(scheduleDTO);
                departmentWeekScheduleDTO.getDaySchedulesDTOList().add(daySchedulesDTO);
            }
        }
        if(daysWithSchedule>=WORKING_DAYS_COUNT){
            departmentWeekScheduleDTO.setIsComplete(true);
        }
        return  departmentWeekScheduleDTO;
    }

    public UserWeekScheduleDTO convert(List<Vacation> activeVacations, List<Holiday> holidays,
                                       List<Schedule> schedules, Long weeksSkip){
        List<String> daysOfWeek= new ArrayList<>(Arrays
                .asList("Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"));

        UserWeekScheduleDTO weekDTO = new UserWeekScheduleDTO();
        int daysWithSchedule = 0;
        for(Schedule schedule : schedules){
            daysWithSchedule+=1;
            String dayName = schedule.getDayShift().getDayName();
            LocalTime start = schedule.getDayShift().getStart();
            LocalTime end = schedule.getDayShift().getEnd();

            DayScheduleDTO dayDTO = new DayScheduleDTO();

            dayDTO.setDayName(dayName);
            daysOfWeek.remove(dayName);
            dayDTO.setStart(start);
            dayDTO.setEnd(end);

            LocalDate dayDate = getDateByDayName(dayName,weeksSkip);
            dayDTO.setDate(dayDate);
            boolean isHoliday = false;

            if(start==null && end==null){
                dayDTO.setHoliday(true);
                weekDTO.getDaysSchedules().add(dayDTO);
                continue;
            }

            for(Vacation vacation : activeVacations){
                boolean isBefore = dayDate.isBefore(vacation.getStart());
                boolean isAfter = dayDate.isAfter(vacation.getEnd());
                if((!isBefore && !isAfter) || dayDate.isEqual(vacation.getEnd()) || dayDate.isEqual(vacation.getStart())){
                    dayDTO.setHoliday(true);
                    isHoliday = true;
                    break;
                }
            }
            if(!isHoliday){
                for(Holiday holiday : holidays){
                    LocalDate holidayDate = getThisYearDate(holiday.getDate(),weeksSkip);
                    if(holidayDate.isEqual(dayDate)){
                        dayDTO.setHoliday(true);
                        break;
                    }
                }
            }
            if(daysWithSchedule>=WORKING_DAYS_COUNT){
                weekDTO.setComplete(true);
            }
            weekDTO.getDaysSchedules().add(dayDTO);
        }

        for(String day : daysOfWeek){
            LocalDate dayDate = getDateByDayName(day,weeksSkip);
            DayScheduleDTO dayDTO = new DayScheduleDTO();
            dayDTO.setDayName(day);
            dayDTO.setHoliday(true);
            dayDTO.setDate(dayDate);
            weekDTO.getDaysSchedules().add(dayDTO);
        }

        return weekDTO;
    }

}

