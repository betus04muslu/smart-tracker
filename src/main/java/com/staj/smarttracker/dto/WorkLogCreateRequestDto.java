package com.staj.smarttracker.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkLogCreateRequestDto {

    @NotBlank(message = "Açıklama boş olamaz")
    private String description;

    @NotNull(message = "Harcanan süre boş olamaz")
    private Double spentHours;

    private Long projectId;
}