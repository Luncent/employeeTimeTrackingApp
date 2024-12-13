package org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions;

public class HolidayAddEditException extends RuntimeException{
    public HolidayAddEditException(String field) {
        super("Проверьте поле "+field);
    }
}
