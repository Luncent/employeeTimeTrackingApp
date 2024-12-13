package org.example.employeetimetrackingservice.exceptions;

import jakarta.persistence.EntityNotFoundException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departmentdayshift.DepartmentDayShiftTimeException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departments.DepartmentAddEditException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departments.DepartmentExistsException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions.HolidayAddEditException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.holidayexceptions.HolidayExistsException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.roleexeptions.RoleExistsException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.schedule.ScheduleExistsException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UserAddException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UserManagerAlreadyTaken;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UsernameAlreadyTakenException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.vacation.VacationDateException;
import org.example.employeetimetrackingservice.exceptions.myexceptions.vacation.VacationDeleteException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.sql.SQLException;

@RestControllerAdvice
public class GlobalExceptionHandler {
    @ExceptionHandler(UsernameAlreadyTakenException.class)
    public ResponseEntity<Object> handleUsernameAlreadyTakenException(UsernameAlreadyTakenException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                "/users"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(UserAddException.class)
    public ResponseEntity<Object> handleUserAddException(UserAddException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                "/users"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UserManagerAlreadyTaken.class)
    public ResponseEntity<Object> handleUserManagerAlreadyTaken(UserManagerAlreadyTaken ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                "/users"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(RoleExistsException.class)
    public ResponseEntity<Object> handleRoleExistsException(RoleExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                "/roles"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HolidayExistsException.class)
    public ResponseEntity<Object> handleHolidayExistsException(HolidayExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                "/holidays"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(HolidayAddEditException.class)
    public ResponseEntity<Object> handleHolidayAddException(HolidayAddEditException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                "/holidays"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(DepartmentExistsException.class)
    public ResponseEntity<Object> handleDepartmentExistsException(DepartmentExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                "/departments"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(DepartmentAddEditException.class)
    public ResponseEntity<Object> handleDepartmentAddEditException(DepartmentAddEditException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                "/departments"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(EntityNotFoundException.class)
    public ResponseEntity<Object> handleEntityNotFoundException(EntityNotFoundException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.NOT_FOUND.value(),
                "Not found",
                ex.getMessage(),
                ""
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.NOT_FOUND);
    }


    @ExceptionHandler(DepartmentDayShiftTimeException.class)
    public ResponseEntity<Object> handleDepartmentDayShiftTimeException(DepartmentDayShiftTimeException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                ""
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VacationDateException.class)
    public ResponseEntity<Object> handleVacationDateException(VacationDateException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                "/vacations"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(VacationDeleteException.class)
    public ResponseEntity<Object> handleVacationDeleteException(VacationDeleteException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                "vacations"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(ScheduleExistsException.class)
    public ResponseEntity<Object> handleScheduleExistsException(ScheduleExistsException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.CONFLICT.value(),
                "Conflict",
                ex.getMessage(),
                "/schedule"
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.CONFLICT);
    }

    @ExceptionHandler(SQLException.class)
    public ResponseEntity<Object> handleSQLException(SQLException ex) {
        ErrorResponse errorResponse = new ErrorResponse(
                HttpStatus.BAD_REQUEST.value(),
                "Bad request",
                ex.getMessage(),
                ""
        );
        return new ResponseEntity<>(errorResponse, HttpStatus.BAD_REQUEST);
    }
}
