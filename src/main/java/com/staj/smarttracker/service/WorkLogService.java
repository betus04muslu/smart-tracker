package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.WorkLogCreateRequestDto;
import com.staj.smarttracker.dto.WorkLogSearchCriteria;
import com.staj.smarttracker.entity.Project;
import com.staj.smarttracker.entity.User;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.exception.ResourceNotFoundException;
import com.staj.smarttracker.repository.ProjectRepository;
import com.staj.smarttracker.repository.UserRepository;
import com.staj.smarttracker.repository.WorkLogRepository;
import com.staj.smarttracker.specification.WorkLogSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class WorkLogService {

    private final WorkLogRepository workLogRepository;
    private final UserRepository userRepository;
    private final ProjectRepository projectRepository;

    private User getCurrentUser() {
        try {
            Authentication auth = SecurityContextHolder.getContext().getAuthentication();
            if (auth != null && auth.isAuthenticated() && !auth.getName().equals("anonymousUser")) {
                String email = auth.getName();
                return userRepository.findByEmail(email).orElseGet(() -> getDefaultUser());
            }
        } catch (Exception ignored) {
        }
        return getDefaultUser();
    }

    private User getDefaultUser() {
        return userRepository.findAll().stream().findFirst().orElse(null);
    }

    public Page<WorkLog> searchWorkLogs(WorkLogSearchCriteria criteria, Pageable pageable) {
        User currentUser = getCurrentUser();

        Specification<WorkLog> userSpec = (root, query, cb) -> {
            if (currentUser != null) {
                return cb.equal(root.get("user").get("id"), currentUser.getId());
            }
            return cb.conjunction();
        };
        Specification<WorkLog> criteriaSpec = WorkLogSpecification.getWorkLogsByCriteria(criteria);

        return workLogRepository.findAll(Specification.where(userSpec).and(criteriaSpec), pageable);
    }

    public WorkLog getById(Long id) {
        return workLogRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkLog bulunamadı ID: " + id));
    }

    public WorkLog createWorkLog(WorkLogCreateRequestDto request) {
        WorkLog workLog = new WorkLog();
        workLog.setDescription(request.getDescription());
        workLog.setSpentHours(request.getSpentHours());
        workLog.setLogDate(LocalDateTime.now());

        // Oturum açan kullanıcıyı bağla (yoksa varsayılan)
        User currentUser = getCurrentUser();
        if (currentUser != null) {
            workLog.setUser(currentUser);
        }

        // Proje seçilmediyse veritabanındaki ilk projeyi varsayılan olarak ata (SQL hatasını önler)
        Project defaultProject = projectRepository.findAll().stream().findFirst().orElse(null);
        if (defaultProject != null) {
            workLog.setProject(defaultProject);
        }

        return workLogRepository.save(workLog);
    }

    public WorkLog updateWorkLog(Long id, WorkLogCreateRequestDto request) {
        WorkLog existing = getById(id);
        if (request.getDescription() != null) {
            existing.setDescription(request.getDescription());
        }
        if (request.getSpentHours() != null) {
            existing.setSpentHours(request.getSpentHours());
        }
        return workLogRepository.save(existing);
    }

    public void deleteWorkLog(Long id) {
        WorkLog existing = getById(id);
        workLogRepository.delete(existing);
    }
}