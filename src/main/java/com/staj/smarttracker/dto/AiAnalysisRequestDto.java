package com.staj.smarttracker.dto;

public class AiAnalysisRequestDto {
    private String taskDescription;
    private String expertiseArea;
    private int experienceYears;

    public AiAnalysisRequestDto() {}

    public AiAnalysisRequestDto(String taskDescription, String expertiseArea, int experienceYears) {
        this.taskDescription = taskDescription;
        this.expertiseArea = expertiseArea;
        this.experienceYears = experienceYears;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

    public String getExpertiseArea() {
        return expertiseArea;
    }

    public void setExpertiseArea(String expertiseArea) {
        this.expertiseArea = expertiseArea;
    }

    public int getExperienceYears() {
        return experienceYears;
    }

    public void setExperienceYears(int experienceYears) {
        this.experienceYears = experienceYears;
    }
}