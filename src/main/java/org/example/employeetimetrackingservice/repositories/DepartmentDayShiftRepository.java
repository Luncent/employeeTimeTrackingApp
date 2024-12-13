package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.DepartmentDayShift;
import org.example.employeetimetrackingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface DepartmentDayShiftRepository extends JpaRepository<DepartmentDayShift,Long> {
    List<DepartmentDayShift> findAllByDepartmentId(Long departmentID);
}
