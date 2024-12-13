package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.dto.UserSavedDTO;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.entities.Role;
import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UserManagerAlreadyTaken;
import org.example.employeetimetrackingservice.exceptions.myexceptions.userexeptions.UsernameAlreadyTakenException;
import org.example.employeetimetrackingservice.mappers.UserMapper;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.example.employeetimetrackingservice.repositories.RoleRepository;
import org.example.employeetimetrackingservice.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;
    @Autowired
    private RoleRepository roleRepository;
    @Autowired
    private DepartmentRepository departmentRepository;
    @Autowired
    private UserMapper userMapper;

    @Transactional
    public List<User> getAll(){
        return userRepository.findAll();
    }

    @Transactional
    public User findById(Long id){
        return userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user not found"));
    }

    @Transactional
    public UserSavedDTO save(String name, String password, Long departmentId, Long roleId){
        Department department = departmentRepository.getReferenceById(departmentId);
        Role role = roleRepository.getReferenceById(roleId);
        User user = User.builder()
                .name(name)
                .password(password)
                .department(department)
                .role(role)
                .build();
        if(userRepository.findByName(name).isPresent()){
            throw new UsernameAlreadyTakenException(name);
        }
        if(role.getTitle().equals("Начальник") && userRepository.findByRoleIdAndDepartmentId(roleId,departmentId).isPresent()){
            throw new UserManagerAlreadyTaken("Начальник для данного отедла уже существует");
        }
        return userMapper.getSavedUserDTO(userRepository.save(user));
    }

    @Transactional
    public User update(Long id, byte[] pictureFile){
        User user = userRepository.findById(id).orElseThrow(()-> new EntityNotFoundException("user not found"));
        user.setProfile_picture(pictureFile);
        return user;
    }

    @Transactional
    public User findByName(String name){
        return userRepository.findByName(name).orElseThrow(()-> new EntityNotFoundException("user not found"));
    }

    @Transactional
    public List<User> findByWorkersDepartment(Long departmentId){
        return userRepository.findWorkersByDepartmentId(departmentId);
    }

    @Transactional
    public void delete(Long id){
        userRepository.deleteById(id);
    }

    @Transactional
    public Optional<User> auth(String userName, String password){
        return userRepository.findByNameAndPassword(userName,password);
    }
}
