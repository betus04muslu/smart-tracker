package com.staj.smarttracker.config;

import com.staj.smarttracker.entity.*;
import com.staj.smarttracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final FeatureRepository featureRepository;
    private final WorkLogRepository workLogRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {

            User ali = userRepository.save(new User(null, "Ali Yılmaz", "ali@firma.com", null));
            User ayse = userRepository.save(new User(null, "Ayşe Kaya", "ayse@firma.com", null));


            Project smartTracker = projectRepository.save(new Project(null, "Smart Tracker", "AI Destekli Efor Takip", null));


            Feature dbOptimization = featureRepository.save(new Feature(null, "Database Optimization", null));


            workLogRepository.save(new WorkLog(null, "Database Optimization - İndeksleme", 3.0, LocalDateTime.now(), ali, smartTracker, dbOptimization));
            workLogRepository.save(new WorkLog(null, "Database Optimization - Sorgu İyileştirme", 5.0, LocalDateTime.now(), ayse, smartTracker, dbOptimization));

            System.out.println(">>> TEST VERİLERİ BAŞARIYLA YÜKLENDİ! <<<");
        }
    }
}