package com.staj.smarttracker.dto;

public class AiAnalysisResponseDto {
    private double estimatedHours;
    private String suggestedCategory;
    private String summary;

    // Boş Constructor (Spring / Jackson için şarttır)
    public AiAnalysisResponseDto() {
    }

    // Dolu Constructor
    public AiAnalysisResponseDto(double estimatedHours, String suggestedCategory, String summary) {
        this.estimatedHours = estimatedHours;
        this.suggestedCategory = suggestedCategory;
        this.summary = summary;
    }

    // Getters ve Setters
    public double getEstimatedHours() {
        return estimatedHours;
    }

    public void setEstimatedHours(double estimatedHours) {
        this.estimatedHours = estimatedHours;
    }

    public String getSuggestedCategory() {
        return suggestedCategory;
    }

    public void setSuggestedCategory(String suggestedCategory) {
        this.suggestedCategory = suggestedCategory;
    }

    public String getSummary() {
        return summary;
    }

    public void setSummary(String summary) {
        this.summary = summary;
    }
}