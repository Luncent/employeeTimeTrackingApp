package org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions;

public class HolidayExistsException extends RuntimeException{
    public HolidayExistsException(String msg) {
        super(msg);
    }
}
