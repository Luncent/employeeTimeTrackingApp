package org.example.employeetimetrackingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name="breaks")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Break {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="day_name")
    private String dayName;

    @Column(name="day_date")
    private LocalDate date;

    private LocalTime start;

    private LocalTime end;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
