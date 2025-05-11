package com.Payment_Reminder_API.models;

import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Reminder {
    @Id
    @GeneratedValue(strategy = jakarta.persistence.GenerationType.IDENTITY)
    private Long id;
    private String email;
    private Boolean isPaid;
    private String description;
    private LocalDateTime createdAt;
    private Integer remindersSent;
    private Integer amount;
    @ManyToOne
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
}

