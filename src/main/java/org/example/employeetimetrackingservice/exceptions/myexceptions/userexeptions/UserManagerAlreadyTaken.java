package org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions;

public class UserManagerAlreadyTaken extends RuntimeException{
    public UserManagerAlreadyTaken(String msg) {
        super(msg);
    }
}
