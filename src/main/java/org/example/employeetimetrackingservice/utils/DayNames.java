package org.example.employeetimetrackingservice.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class DayNames {
    private static final List<String> daysOfWeek = new ArrayList<>(
            Arrays.asList("Понедельник","Вторник","Среда","Четверг","Пятница","Суббота","Воскресенье"));

    public static boolean correctDayName(String dayName){
        return daysOfWeek.contains(dayName);
    }
}
