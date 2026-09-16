package com.staj.smarttracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Table(name = "users")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name; // Eski yapındaki name alanı korundu

    @Column(nullable = false, unique = true)
    private String email;

    @Column(nullable = false)
    private String password;

    // Yapay zeka efor ve çalışan önerisi için eklenen alanlar:
    private String expertiseArea;    // Örn: "Backend Geliştirici", "Frontend Geliştirici"
    private Integer experienceYears; // Örn: 5

    @JsonIgnoreProperties({"user", "hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "user")
    private List<WorkLog> workLogs;
}