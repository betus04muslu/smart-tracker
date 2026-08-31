package com.staj.smarttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class FeatureAnalyticsDto {
    private String featureTitle;
    private String projectName;
    private Double totalHours;

    public FeatureAnalyticsDto(String featureTitle, String projectName, Number totalHours) {
        this.featureTitle = featureTitle;
        this.projectName = projectName;
        this.totalHours = totalHours != null ? totalHours.doubleValue() : 0.0;
    }
}