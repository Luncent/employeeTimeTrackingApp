package org.example.employeetimetrackingservice.repositories;

import org.example.employeetimetrackingservice.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface RoleRepository extends JpaRepository<Role,Long> {
    @Query("SELECT r FROM Role r WHERE r.title<>'Начальник'")
    List<Role> findAllExceptManager();
    Optional<Role> findByTitle(String title);
}
