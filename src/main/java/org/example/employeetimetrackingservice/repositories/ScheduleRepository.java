package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.Schedule;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;
import java.util.Optional;

public interface ScheduleRepository extends JpaRepository<Schedule,Long> {
    @Query("SELECT s FROM Schedule s JOIN s.user u WHERE u.name = :username")
    List<Schedule> findAllByUserName(@Param("username") String userName);

    @Query("SELECT s FROM Schedule s JOIN s.user u JOIN s.dayShift ds WHERE u.name = :username AND ds.dayName = :dayName")
    Optional<Schedule> findAllByUserAndDay(@Param("username") String userName,
                                           @Param("dayName") String dayName);

}
