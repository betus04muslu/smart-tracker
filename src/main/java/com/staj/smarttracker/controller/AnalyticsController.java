package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.FeatureAnalyticsDto;
import com.staj.smarttracker.dto.UserAnalyticsDto;
import com.staj.smarttracker.service.AnalyticsService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/analytics")
@RequiredArgsConstructor
public class AnalyticsController {

    private final AnalyticsService analyticsService;

    @GetMapping("/users")
    public List<UserAnalyticsDto> getUserAnalytics() {
        return analyticsService.getUserAnalytics();
    }

    @GetMapping("/features")
    public List<FeatureAnalyticsDto> getFeatureAnalytics() {
        return analyticsService.getFeatureAnalytics();
    }
}
