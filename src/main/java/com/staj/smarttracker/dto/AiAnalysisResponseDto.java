package com.staj.smarttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class AiAnalysisResponseDto {
    private double estimatedHours;
    private String category;
    private String summary;
    private String recommendedEmployee;
    private double recommendedEmployeeHours;
}