package com.staj.smarttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiAnalysisResponseDto {
    private Double estimatedHours;
    private String suggestedCategory;
    private String summary;
}