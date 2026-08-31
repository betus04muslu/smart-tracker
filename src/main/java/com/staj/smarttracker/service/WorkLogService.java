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
    public WorkLog getById(Long id) {
        return workLogRepository.findById(id)
                .orElseThrow(() -> new com.staj.smarttracker.exception.ResourceNotFoundException("WorkLog bulunamadı ID: " + id));
    }


    public WorkLog updateWorkLog(Long id, com.staj.smarttracker.dto.WorkLogCreateRequestDto request) {
        WorkLog existing = getById(id);
        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }
        return workLogRepository.save(existing);
    }

    public void deleteWorkLog(Long id) {
        WorkLog existing = getById(id);
        workLogRepository.delete(existing);
    }
    public WorkLog createWorkLog(com.staj.smarttracker.dto.WorkLogCreateRequestDto request) {
        WorkLog workLog = new WorkLog();
        workLog.setDescription(request.getDescription());
        return workLogRepository.save(workLog);
    }
}
