package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import org.springframework.stereotype.Service;

@Service
public class AiService {

    public AiAnalysisResponseDto analyzeTaskDescription(String description) {
        if (description == null || description.isBlank()) {
            return new AiAnalysisResponseDto(0.0, "Unknown", "No description provided");
        }

        double estimatedHours = Math.max(1.0, Math.round((description.length() / 12.0) * 10.0) / 10.0);

        String category = "General Task";
        String lowerDesc = description.toLowerCase();

        if (lowerDesc.contains("sql") || lowerDesc.contains("database") || lowerDesc.contains("index")) {
            category = "Database & Query Optimization";
        } else if (lowerDesc.contains("api") || lowerDesc.contains("com/staj/smarttracker/controller") || lowerDesc.contains("rest")) {
            category = "Backend API Development";
        } else if (lowerDesc.contains("security") || lowerDesc.contains("jwt") || lowerDesc.contains("auth")) {
            category = "Security & Auth Integration";
        }

        String summary = "AI Summary: " + (description.length() > 40 ? description.substring(0, 37) + "..." : description);

        return new AiAnalysisResponseDto(estimatedHours, category, summary);
    }
}
