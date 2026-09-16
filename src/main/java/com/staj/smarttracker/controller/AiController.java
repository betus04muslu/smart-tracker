package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import com.staj.smarttracker.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AiController {

    private final AiService aiService;

    @PostMapping("/analyze")
    public ResponseEntity<AiAnalysisResponseDto> analyzeTask(@RequestBody Map<String, Object> requestMap) {
        String description = (String) requestMap.getOrDefault("taskDescription", requestMap.get("description"));
        if (description == null || description.toString().trim().isEmpty()) {
            description = "Genel yazılım görevi";
        }

        String expertiseArea = (String) requestMap.getOrDefault("expertiseArea", "Backend Geliştirici");

        int experienceYears = 3;
        Object expObj = requestMap.get("experienceYears");
        if (expObj instanceof Number) {
            experienceYears = ((Number) expObj).intValue();
        } else if (expObj != null) {
            try {
                experienceYears = Integer.parseInt(expObj.toString());
            } catch (Exception ignored) {}
        }

        AiAnalysisResponseDto response = aiService.analyzeTaskWithExpertise(
                description.toString(),
                expertiseArea,
                experienceYears
        );

        return ResponseEntity.ok(response);
    }
}