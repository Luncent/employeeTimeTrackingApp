package org.example.employeetimetrackingservice.utils;

import java.time.DayOfWeek;
import java.time.LocalDate;

public class DayNameConvertions {

    public static LocalDate getDateByDayName(String dayName, Long weeksSkip){
        String correctConstantValue = getConstantDayName(dayName);
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(correctConstantValue);

        LocalDate dateToFind = today.with(dayOfWeek);
        return dateToFind.plusWeeks(weeksSkip);
    }

    public static String getConstantDayName(String rus){
        switch(rus){
            case "Понедельник":
                return "MONDAY";
            case "Вторник":
                return "TUESDAY";
            case "Среда":
                return "WEDNESDAY";
            case "Четверг":
                return "THURSDAY";
            case "Пятница":
                return "FRIDAY";
            case "Суббота":
                return "SATURDAY";
            case "Воскресенье":
                return "SUNDAY";
            default:
                return "err";
        }
    }

    public static String getRusDayName(String enConstantDayName){
        switch(enConstantDayName){
            case "MONDAY":
                return "Понедельник";
            case "TUESDAY":
                return "Вторник";
            case "WEDNESDAY":
                return "Среда";
            case "THURSDAY":
                return "Четверг";
            case "FRIDAY":
                return "Пятница";
            case "SATURDAY":
                return "Суббота";
            case "SUNDAY":
                return "Воскресенье";
            default:
                return "err";
        }
    }

    public static LocalDate getThisYearDate(String holidayDate, Long weeksSkip){
        String dateFormat = "%s-%s";
        int year = LocalDate.now().plusWeeks(weeksSkip).getYear();

        String formatedDate = String.format(dateFormat,year,holidayDate);

        return LocalDate.parse(formatedDate);
    }
}
