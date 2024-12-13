package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.dto.UserSavedDTO;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UserAddException;
import org.example.employeetimetrackingservice.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    private UserService userService;

    @GetMapping
    public List<User> getAll(){
        return userService.getAll();
    }

    @GetMapping("/{name}")
    public User getByName(@PathVariable String name){
        return userService.findByName(name);
    }

    @PostMapping
    public ResponseEntity<UserSavedDTO> save(@RequestParam String name,
                                             @RequestParam String password,
                                             @RequestParam Long departmentId,
                                             @RequestParam Long roleId){
        if (name == null || name.trim().isEmpty()) {
            throw new UserAddException("имя");
        }
        if (password == null || password.trim().isEmpty()) {
            throw new UserAddException("пароль");
        }
        if (departmentId == null || roleId == null) {
            throw new UserAddException("департамент, должность");
        }
        UserSavedDTO userSavedDTO = userService.save(name,password,departmentId,roleId);
        return ResponseEntity.ok(userSavedDTO);
    }

    @PatchMapping User setPicture(@RequestParam byte[] picture, @RequestParam Long id){
        return userService.update(id,picture);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        userService.delete(id);
    }
}
