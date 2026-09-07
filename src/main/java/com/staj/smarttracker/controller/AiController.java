package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import com.staj.smarttracker.service.AiService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/ai")
@RequiredArgsConstructor
public class AiController {

    private final AiService aiService;

    @PostMapping("/analyze")
    public AiAnalysisResponseDto analyzeTask(@RequestBody String description) {
        return aiService.analyzeTaskDescription(description);
    }
}