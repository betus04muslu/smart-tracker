package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.WorkLogCreateRequestDto;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.service.WorkLogService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/work-logs")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class WorkLogController {

    private final WorkLogService workLogService;

    @GetMapping
    public ResponseEntity<List<WorkLog>> getAllWorkLogs() {
        try {
            PageRequest pageRequest = PageRequest.of(0, 100, Sort.by("id").descending());
            return ResponseEntity.ok(workLogService.searchWorkLogs(null, pageRequest).getContent());
        } catch (Exception e) {
            // Eğer kullanıcı oturumu zorunluluğuna takılırsa boş liste dönerek uygulamanın patlamasını önlüyoruz
            return ResponseEntity.ok(List.of());
        }
    }

    @PostMapping
    public ResponseEntity<?> createWorkLog( @RequestBody WorkLogCreateRequestDto request) {
        try {
            WorkLog savedWorkLog = workLogService.createWorkLog(request);
            return ResponseEntity.ok(savedWorkLog);
        } catch (Exception e) {
            // Oturum açılmadığı için kullanıcı bulunamazsa hatayı yakalayıp detayını iletiyoruz
            return ResponseEntity.badRequest().body("Hata: " + e.getMessage());
        }
    }
}