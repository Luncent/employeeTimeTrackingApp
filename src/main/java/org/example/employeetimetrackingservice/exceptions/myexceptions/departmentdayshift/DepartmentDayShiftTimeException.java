package org.example.employeetimetrackingservice.exceptions.myexceptions.departmentdayshift;

public class DepartmentDayShiftTimeException extends RuntimeException{
    public DepartmentDayShiftTimeException(String msg) {
        super("Ошибка со временем: "+msg);
    }
}
