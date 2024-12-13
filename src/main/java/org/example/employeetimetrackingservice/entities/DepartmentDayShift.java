package org.example.employeetimetrackingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalTime;

@Entity
@Table(name="departments_days_shifts")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class DepartmentDayShift {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="day_name")
    private String dayName;

    private LocalTime start;

    private LocalTime end;

    @OneToOne
    @JoinColumn(name = "department_id")
    private Department department;
}
