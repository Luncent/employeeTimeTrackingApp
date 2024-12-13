package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.UserTimeRecord;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface UserTimeRecordsRepository extends JpaRepository<UserTimeRecord,Long> {
    @Query("SELECT utr FROM UserTimeRecord utr JOIN utr.user u WHERE utr.date = :dayDate AND u.name = :uName")
    Optional<UserTimeRecord> findByDateAndUser(@Param("dayDate") LocalDate dayDate, @Param("uName") String name);

    @Query("SELECT utr FROM UserTimeRecord utr JOIN utr.user u WHERE utr.date BETWEEN :start AND :end AND u.name = :uName")
    List<UserTimeRecord> findAllBetweenDatesByUser(@Param("start") LocalDate start,
                                                   @Param("end") LocalDate end,
                                                   @Param("uName") String name);
    //SELECT * FROM users_time_records WHERE user_id = 1 AND day_date BETWEEN '2024-11-30' AND '2024-12-09';
}
