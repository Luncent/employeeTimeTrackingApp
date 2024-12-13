package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.Vacation;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface VacationRepository extends JpaRepository<Vacation,Long> {

    @Query("SELECT v FROM Vacation v JOIN v.user u WHERE u.name = :username")
    List<Vacation> findAllByUsername(@Param("username") String username);

    @Query("SELECT v FROM Vacation v JOIN v.user u WHERE u.name = :username AND v.status=2 AND v.end>CURRENT_DATE")
    List<Vacation> findAllActiveVacationsByUsername(@Param("username") String username);

    @Query("SELECT v FROM Vacation v JOIN v.user u JOIN u.department d WHERE d.id = :did AND v.status=:awaitingStatus")
    List<Vacation> findAllWaitingByDepartmentId(@Param("did") Long did, @Param("awaitingStatus") int awaitingStatus);

    @Query("SELECT v FROM Vacation v JOIN v.user u JOIN u.department d WHERE d.id = :did AND v.status=:awaitingStatus AND u.name = :uname")
    List<Vacation> findAllWaitingByDepartmentIdAndUser(@Param("did") Long did, @Param("awaitingStatus") int awaitingStatus,
                                                       @Param("uname") String userName);

    @Query("SELECT SUM(DATEDIFF(LEAST(v.end, :end), GREATEST(v.start, :start)) + 1) " +
            "FROM Vacation v JOIN v.user u " +
            "WHERE u.name = :uname AND v.start <= :end AND v.end >= :start AND v.status IN (2,4)")
    Long findVacationDaysBetweenDates(@Param("uname") String userName,
                                      @Param("start") LocalDate start,
                                      @Param("end") LocalDate end);
}
