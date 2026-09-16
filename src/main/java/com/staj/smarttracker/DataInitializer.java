package com.staj.smarttracker;

import com.staj.smarttracker.entity.User;
import com.staj.smarttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataInitializer implements CommandLineRunner {

    private final UserRepository userRepository;

    @Override
    public void run(String... args) throws Exception {
        // Tabloyu tamamen temizleyip güncel verileri baştan yüklemesi için count şartını kaldırıyoruz
        // veya mevcut kullanıcıları güncelliyoruz:

        saveOrUpdateUser("Ali Bey", "ali@sirket.com", "Backend Geliştirici", 13);
        saveOrUpdateUser("Ayşe Hanım", "ayse@sirket.com", "Frontend Geliştirici", 4);
        saveOrUpdateUser("Mehmet Bey", "mehmet@sirket.com", "Fullstack Geliştirici", 10);
        saveOrUpdateUser("Betül Hanım", "betul@sirket.com", "Backend Geliştirici", 2);
        saveOrUpdateUser("Serkan Bey", "serkan@sirket.com", "Frontend Geliştirici", 5);

        System.out.println(">>> Tüm çalışanlar güncel uzmanlık alanlarıyla veritabanına işlendi!");
    }

    private void saveOrUpdateUser(String name, String email, String expertiseArea, int experienceYears) {
        User user = userRepository.findByEmail(email).orElse(new User());
        user.setName(name);
        user.setEmail(email);
        user.setPassword("password");
        user.setExpertiseArea(expertiseArea);
        user.setExperienceYears(experienceYears);
        userRepository.save(user);
    }
}