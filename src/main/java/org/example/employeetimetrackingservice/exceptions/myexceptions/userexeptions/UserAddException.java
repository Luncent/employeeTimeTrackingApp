package org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions;

public class UserAddException extends RuntimeException{
    public UserAddException(String field) {
        super("Проверьте поле "+field);
    }
}
