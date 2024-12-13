package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.Break;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;


import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

public interface BreakRepository extends JpaRepository<Break, Long> {
    List<Break> findAllByUserId(Long userId);
    @Query("SELECT b FROM Break b JOIN b.user u WHERE u.name = :user_name AND b.date = :date ORDER BY b.id DESC")
    List<Break> findLatestByUserAndDay(@Param("user_name") String userName,
                                           @Param("date") LocalDate today,
                                           Pageable pageable);

    @Query("SELECT b FROM Break b JOIN b.user u WHERE u.name = :user_name AND b.date BETWEEN :start AND :end")
    List<Break> findAllBetweenDatesByUser(@Param("user_name") String userName,
                                          @Param("start") LocalDate start,
                                          @Param("end") LocalDate end);
}
