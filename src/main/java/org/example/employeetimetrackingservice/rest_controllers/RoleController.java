package org.example.employeetimetrackingservice.rest_controllers;

import org.example.employeetimetrackingservice.entities.Role;
import org.example.employeetimetrackingservice.services.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/roles")
public class RoleController {
    @Autowired
    private RoleService roleService;

    @GetMapping
    public List<Role> getAllExceptManager(){
        return roleService.findAllExceptManager();
    }

    @GetMapping("/allroles")
    public List<Role> getAll(){
        return roleService.findAll();
    }

    @PostMapping
    public Role save(@RequestParam String title){
        return roleService.save(title);
    }

    @PutMapping
    public Role update(@RequestParam String title,
                       @RequestParam Long id){
        return roleService.update(title, id);
    }

    @DeleteMapping
    public void delete(@RequestParam Long id){
        roleService.delete(id);
    }
}
