package org.example.employeetimetrackingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.time.LocalTime;

@Entity
@Table(name = "users_time_records")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserTimeRecord {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name="day_name")
    private String dayName;

    @Column(name="day_date")
    private LocalDate date;

    private LocalTime delay;

    @Column(name = "overworked_time")
    private LocalTime overworkedTime;

    @Column(name = "underworked_time")
    private LocalTime underworkedTime;

    private int status;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
