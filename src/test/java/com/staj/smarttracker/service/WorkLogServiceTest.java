package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.WorkLogSearchCriteria;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.repository.WorkLogRepository;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class WorkLogServiceTest {

    @Mock
    private WorkLogRepository workLogRepository;

    @InjectMocks
    private WorkLogService workLogService;

    @Test
    @DisplayName("Dinamik arama yapıldıında filtrelenmiş sayfayı dönmeli")
    void searchWorkLogs_ShouldReturnPageOfWorkLogs() {
        // GIVEN (Hazırlık)
        WorkLogSearchCriteria criteria = new WorkLogSearchCriteria();
        criteria.setDescription("bug");

        Pageable pageable = PageRequest.of(0, 10);
        WorkLog mockWorkLog = new WorkLog();
        mockWorkLog.setId(1L);
        mockWorkLog.setDescription("Fixed a critical bug");

        Page<WorkLog> mockPage = new PageImpl<>(List.of(mockWorkLog));

        when(workLogRepository.findAll(any(Specification.class), eq(pageable))).thenReturn(mockPage);

        // WHEN (İşlem)
        Page<WorkLog> result = workLogService.searchWorkLogs(criteria, pageable);

        // THEN (Doğrulama)
        assertNotNull(result);
        assertEquals(1, result.getTotalElements());
        assertEquals("Fixed a critical bug", result.getContent().get(0).getDescription());
        verify(workLogRepository, times(1)).findAll(any(Specification.class), eq(pageable));
    }
}
