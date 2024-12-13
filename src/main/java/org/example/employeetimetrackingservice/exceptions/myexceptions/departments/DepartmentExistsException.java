package org.example.employeetimetrackingservice.exceptions.myexceptions.departments;

public class DepartmentExistsException extends RuntimeException{
    public DepartmentExistsException(String msg) {
        super(msg);
    }
}
