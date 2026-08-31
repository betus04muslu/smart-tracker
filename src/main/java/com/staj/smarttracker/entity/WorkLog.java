package com.staj.smarttracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "work_logs")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class WorkLog {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String description;

    @Column(nullable = false)
    private Double spentHours;

    private LocalDateTime logDate;

    @JsonIgnoreProperties({"workLogs", "hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;

    @JsonIgnoreProperties({"workLogs", "hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "project_id", nullable = false)
    private Project project;

    @JsonIgnoreProperties({"workLogs", "hibernateLazyInitializer", "handler"})
    @ManyToOne
    @JoinColumn(name = "feature_id")
    private Feature feature;
}