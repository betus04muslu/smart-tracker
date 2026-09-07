package com.staj.smarttracker.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserAnalyticsDto {
    private String userName;
    private Double totalHours;

    public UserAnalyticsDto(String userName, Number totalHours) {
        this.userName = userName;
        this.totalHours = totalHours != null ? totalHours.doubleValue() : 0.0;
    }
}