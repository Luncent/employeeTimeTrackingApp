package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User,Long> {

    Optional<User> findByName(String name);

    @Query("SELECT u FROM User u JOIN u.role r JOIN u.department d WHERE r.id = :rid AND d.id = :depid")
    Optional<User> findByRoleIdAndDepartmentId(@Param("rid") Long rid, @Param("depid") Long depid);

    @Query("SELECT u FROM User u JOIN u.department d WHERE d.id = :did AND u.name = :uname")
    Optional<User> findByNameAndDepartmentId(@Param("did") Long departmentId, @Param("uname") String userName);

    @Query("SELECT u FROM User u WHERE u.name = :uname AND u.password = :upassword")
    Optional<User> findByNameAndPassword(@Param("uname") String userName, @Param("upassword") String password);

    @Query("SELECT u FROM User u JOIN u.department d JOIN u.role r WHERE d.id = :did AND r.title<>'Начальник'")
    List<User> findWorkersByDepartmentId(@Param("did") Long departmentId);
}
