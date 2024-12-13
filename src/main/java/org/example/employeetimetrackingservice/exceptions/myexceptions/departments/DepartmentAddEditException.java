package org.example.employeetimetrackingservice.exceptions.myexceptions.departments;

public class DepartmentAddEditException extends RuntimeException{
    public DepartmentAddEditException(String field) {
        super("Проверьте поле "+field);
    }
}
