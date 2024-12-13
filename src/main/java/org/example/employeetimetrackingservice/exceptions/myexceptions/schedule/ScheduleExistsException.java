package org.example.employeetimetrackingservice.exceptions.myexceptions.schedule;

public class ScheduleExistsException extends RuntimeException {
    public ScheduleExistsException(String s) {
        super(s);
    }
}
