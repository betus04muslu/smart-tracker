package com.staj.smarttracker.entity;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Entity
@Table(name = "projects") // veya mevcut tablo adın neyse
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class Project {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String name;

    private String description;

    // YENİ EKLEDİĞİMİZ ALAN: Projenin alanı (Örn: Backend, Frontend, DevOps vb.)
    private String projectField;

    @JsonIgnoreProperties({"project", "hibernateLazyInitializer", "handler"})
    @OneToMany(mappedBy = "project")
    private List<WorkLog> workLogs;
}