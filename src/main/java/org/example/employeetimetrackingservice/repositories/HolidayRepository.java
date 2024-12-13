package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.Holiday;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface HolidayRepository extends JpaRepository<Holiday,Long> {
    Optional<Holiday> findByDate(String date);
    Optional<Holiday> findByName(String name);
}
