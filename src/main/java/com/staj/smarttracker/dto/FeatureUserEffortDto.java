package com.staj.smarttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureUserEffortDto {
    private String featureTitle;
    private String projectName;
    private String userEmail;
    private Double totalHours;
}