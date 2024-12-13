package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.User;
import org.example.employeetimetrackingservice.entities.UserHoliday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.Optional;

public interface UserHolidaysRepository extends JpaRepository<UserHoliday,Long> {
    Optional<UserHoliday> findByUserId(Long userId);
}
