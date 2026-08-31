package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.WorkLogSearchCriteria;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.repository.WorkLogRepository;
import com.staj.smarttracker.specification.WorkLogSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;

    public Page<WorkLog> searchWorkLogs(WorkLogSearchCriteria criteria, Pageable pageable) {
        Specification<WorkLog> spec = WorkLogSpecification.getWorkLogsByCriteria(criteria);
        return workLogRepository.findAll(spec, pageable);
    }
}