package org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions;

public class UsernameAlreadyTakenException extends RuntimeException{
    public UsernameAlreadyTakenException(String username) {
        super("Пользователь '"+username+"' существует.");
    }
}
