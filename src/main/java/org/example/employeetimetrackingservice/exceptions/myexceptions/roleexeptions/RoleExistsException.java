package org.example.employeetimetrackingservice.exceptions.myexceptions.roleexeptions;

public class RoleExistsException extends RuntimeException{
    public RoleExistsException(String title) {
        super("Роль '"+title+"' уже существует");
    }
}
