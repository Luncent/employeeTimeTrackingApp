package org.example.employeetimetrackingservice.services;

import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Role;
import org.example.employeetimetrackingservice.exceptions.myexceptions.roleexeptions.RoleExistsException;
import org.example.employeetimetrackingservice.repositories.RoleRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RoleService {
    @Autowired
    private RoleRepository roleRepository;

    @Transactional
    public Role save(String title){
        if(roleRepository.findByTitle(title).isPresent()){
            throw new RoleExistsException(title);
        }
        Role role = Role.builder().title(title).build();
        return roleRepository.save(role);
    }

    @Transactional
    public List<Role> findAllExceptManager(){
        return roleRepository.findAllExceptManager();
    }

    @Transactional
    public List<Role> findAll(){
        return roleRepository.findAll();
    }

    @Transactional
    public Role update(String title, Long id){
        if(roleRepository.findByTitle(title).isPresent()){
            throw new RoleExistsException(title);
        }
        Role role = roleRepository.findById(id).get();
        role.setTitle(title);
        return roleRepository.save(role);
    }

    @Transactional
    public void delete(Long id){
        roleRepository.deleteById(id);
    }
}
