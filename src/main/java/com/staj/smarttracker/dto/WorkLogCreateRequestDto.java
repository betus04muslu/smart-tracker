package com.staj.smarttracker.dto;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class WorkLogCreateRequestDto {

    @NotBlank(message = "İş açıklaması boş bırakılamaz")
    private String description;

    @NotNull(message = "Efor süresi zorunludur")
    @Min(value = 1, message = "Efor süresi en az 1 saat olmalıdır")
    private Integer hoursSpent;

    @NotNull(message = "Kullanıcı ID zorunludur")
    private Long userId;
}