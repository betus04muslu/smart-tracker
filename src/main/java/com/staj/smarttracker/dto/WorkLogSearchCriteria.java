package com.staj.smarttracker.dto;

import lombok.Data;
import java.time.LocalDate;

@Data
public class WorkLogSearchCriteria {
    private String description;
    private Long userId;
    private Long featureId;
    private LocalDate startDate;
    private LocalDate endDate;
}