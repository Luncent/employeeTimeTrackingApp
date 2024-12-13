package org.example.employeetimetrackingservice.services;

import jakarta.persistence.EntityNotFoundException;
import jakarta.transaction.Transactional;
import org.example.employeetimetrackingservice.entities.Department;
import org.example.employeetimetrackingservice.exceptions.myexceptions.departments.DepartmentExistsException;
import org.example.employeetimetrackingservice.repositories.DepartmentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class DepartmentService {
    @Autowired
    private DepartmentRepository departmentRepository;

    @Transactional
    public List<Department> getAll() {
        return departmentRepository.findAll();
    }

    @Transactional
    public Department save(String name) {
        if(departmentRepository.findByName(name).isPresent()){
            throw new DepartmentExistsException("Отдел с названием '"+name+"' существует");
        }
        Department department = Department.builder()
                .name(name)
                .build();
        return departmentRepository.save(department);
    }

    @Transactional
    public Department update(Long id, String name) {
        if(departmentRepository.findByName(name).isPresent()){
            throw new DepartmentExistsException("Отдел с названием '"+name+"' существует");
        }
        Department department = departmentRepository.findById(id).orElseThrow(()->new EntityNotFoundException("Отдел не найден"));
        department.setName(name);
        return departmentRepository.save(department);
    }

    @Transactional
    public void delete(Long id) {
        departmentRepository.deleteById(id);
    }
}

