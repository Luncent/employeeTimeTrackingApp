package org.example.employeetimetrackingservice;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.DayOfWeek;
import java.time.LocalDate;
import java.time.temporal.ChronoUnit;

@SpringBootTest
public class getDayDateByName {
    @Test
    public void test(){
        String dayName="Воскресенье";
        String correctConstantValue = getEnglishConstantDayName(dayName);
        LocalDate today = LocalDate.now();
        DayOfWeek dayOfWeek = DayOfWeek.valueOf(correctConstantValue);

        LocalDate dateToFind = today.with(dayOfWeek);
        System.out.println(dateToFind.plusWeeks(1));
    }

    public String getEnglishConstantDayName(String rus){
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

    @Test
    public void test2(){
        String dateFormat = "%s-%s";

        String input = "01-22";
        int year = LocalDate.now().getYear();

        String formatedDate = String.format(dateFormat,year,input);

        System.out.println(LocalDate.parse(formatedDate));
    }

    @Test
    public void test3(){
        LocalDate end = LocalDate.of(2024,12,1);
        LocalDate start = LocalDate.of(2024,11,28);
        System.out.println(ChronoUnit.DAYS.between(start,end)+1);
    }
}
