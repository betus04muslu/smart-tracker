package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.WorkLogSearchCriteria;
import com.staj.smarttracker.entity.WorkLog;
import com.staj.smarttracker.service.WorkLogService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/worklogs")
@RequiredArgsConstructor
@Tag(name = "Work Log Controller", description = "İş raporları ve efor takibi için dinamik arama ve yönetim API'leri")
public class WorkLogController {

    private final WorkLogService workLogService;

    @Operation(summary = "Gelişmiş Filtreli İş Raporu Arama", description = "Kullanıcı id, açıklama metni ve tarih aralığına göre sayfalanmış iş loglarını getirir.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Arama başarılı, filtrelenmiş veriler sayfa yapısında döndü."),
            @ApiResponse(responseCode = "400", description = "Geçersiz arama kriteri gönderildi.")
    })
    @PostMapping("/search")
    public ResponseEntity<Page<WorkLog>> searchWorkLogs(
            @RequestBody WorkLogSearchCriteria criteria,
            @PageableDefault(page = 0, size = 10, sort = "id") Pageable pageable) {

        Page<WorkLog> results = workLogService.searchWorkLogs(criteria, pageable);
        return ResponseEntity.ok(results);
    }
}