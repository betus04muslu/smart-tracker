package com.staj.smarttracker;

import com.staj.smarttracker.entity.*;
import com.staj.smarttracker.repository.*;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;
    private final FeatureRepository featureRepository;

    @Override
    public void run(String... args) throws Exception {
        if (userRepository.count() == 0) {

            User ali = new User();
            ali.setName("Ali Yılmaz");
            ali.setEmail("ali@firma.com");
            ali.setPassword("123456");
            userRepository.save(ali);

            Project sampleProject = new Project();
            sampleProject.setName("E-Ticaret API");
            sampleProject.setDescription("Spring Boot Backend Projesi");
            sampleProject.setProjectField("Backend");
            projectRepository.save(sampleProject);

            Feature dbOptimization = new Feature();
            dbOptimization.setTitle("Database Optimization");
            featureRepository.save(dbOptimization);

            System.out.println(">>> PROJE VE PORTFÖY ÖZELLİKLİ TEST VERİLERİ YÜKLENDİ! <<<");
        }
    }
}