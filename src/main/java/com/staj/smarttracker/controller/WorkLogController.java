package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.WorkLogCreateRequestDto;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.repository.WorkLogRepository;
import com.staj.smarttracker.service.WorkLogService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api/worklogs") // Tire işaretini kaldırdık, frontend ile tamamen eşleşti!
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class WorkLogController {
    // ... diğer kodlar aynı kalıyor
    private final WorkLogService workLogService;
    private final WorkLogRepository workLogRepository;

    @GetMapping
    public ResponseEntity<List<WorkLog>> getMyWorkLogs(Authentication authentication) {
        try {
            if (authentication == null || authentication.getName() == null) {
                return ResponseEntity.ok(new ArrayList<>());
            }
            String currentUsername = authentication.getName();

            List<WorkLog> allLogs = workLogRepository.findAll();
            // getUsername yerine getEmail() kullanarak e-posta tabanlı eşleştirme yapıyoruz
            List<WorkLog> userLogs = allLogs.stream()
                    .filter(log -> log.getUser() != null &&
                            currentUsername.equals(log.getUser().getEmail()))
                    .collect(Collectors.toList());

            return ResponseEntity.ok(userLogs);
        } catch (Exception e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }

    @PostMapping
    public ResponseEntity<?> createWorkLog(@RequestBody WorkLogCreateRequestDto request) {
        try {
            WorkLog savedWorkLog = workLogService.createWorkLog(request);
            return ResponseEntity.ok(savedWorkLog);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Hata: " + e.getMessage());
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<?> updateWorkLog(@PathVariable Long id, @RequestBody WorkLogCreateRequestDto request) {
        try {
            WorkLog updated = workLogService.updateWorkLog(id, request);
            return ResponseEntity.ok(updated);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Güncelleme hatası: " + e.getMessage());
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<?> deleteWorkLog(@PathVariable Long id) {
        try {
            workLogService.deleteWorkLog(id);
            return ResponseEntity.ok().build();
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Silme hatası: " + e.getMessage());
        }
    }

    @GetMapping("/all")
    public ResponseEntity<List<WorkLog>> getAllLogsForLeaderboard() {
        try {
            List<WorkLog> allLogs = workLogRepository.findAll();
            return ResponseEntity.ok(allLogs != null ? allLogs : new ArrayList<>());
        } catch (Exception e) {
            return ResponseEntity.ok(new ArrayList<>());
        }
    }
}