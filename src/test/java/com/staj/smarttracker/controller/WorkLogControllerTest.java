package com.staj.smarttracker.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.staj.smarttracker.dto.WorkLogSearchCriteria;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.service.WorkLogService;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest
@AutoConfigureMockMvc(addFilters = false)
class WorkLogControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private WorkLogService workLogService;

    @Test
    @DisplayName("POST /api/worklogs/search isteği atıldığında 200 OK ve JSON sonuç dönmeli")
    void searchWorkLogs_ShouldReturnOkAndPageResult() throws Exception {

        WorkLogSearchCriteria criteria = new WorkLogSearchCriteria();
        criteria.setDescription("bug");

        WorkLog mockWorkLog = new WorkLog();
        mockWorkLog.setId(1L);
        mockWorkLog.setDescription("Fixed a critical bug");

        Page<WorkLog> mockPage = new PageImpl<>(List.of(mockWorkLog));

        when(workLogService.searchWorkLogs(any(WorkLogSearchCriteria.class), any(Pageable.class))).thenReturn(mockPage);


        mockMvc.perform(post("/api/worklogs/search")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(criteria)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].description").value("Fixed a critical bug"));
    }
}