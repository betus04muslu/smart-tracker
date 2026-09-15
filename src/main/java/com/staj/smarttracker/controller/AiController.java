package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import com.staj.smarttracker.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
@CrossOrigin(origins = "http://localhost:5173")
public class AiController {

    private final AiService aiService;

    @PostMapping("/analyze")
    public ResponseEntity<AiAnalysisResponseDto> analyzeTask(@RequestBody String description) {
        AiAnalysisResponseDto response = aiService.analyzeTaskDescription(description);
        return ResponseEntity.ok(response);
    }
}