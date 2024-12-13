package org.example.employeetimetrackingservice.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "users_available_holidays")
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class UserHoliday {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer days;

    @OneToOne
    @JoinColumn(name="user_id")
    private User user;
}
