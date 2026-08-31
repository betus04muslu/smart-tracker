package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.FeatureAnalyticsDto;
import com.staj.smarttracker.dto.UserAnalyticsDto;
import com.staj.smarttracker.repository.WorkLogRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AnalyticsService {

    private final WorkLogRepository workLogRepository;

    public List<UserAnalyticsDto> getUserAnalytics() {
        return workLogRepository.getUserAnalytics();
    }

    public List<FeatureAnalyticsDto> getFeatureAnalytics() {
        return workLogRepository.getFeatureAnalytics();
    }
}
