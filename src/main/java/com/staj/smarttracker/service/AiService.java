package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.Map;

@Service
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public AiAnalysisResponseDto analyzeTaskDescription(String description) {
        if (description == null || description.isBlank()) {
            return new AiAnalysisResponseDto(0.0, "Belirsiz", "Açıklama girilmedi.");
        }

        String aiSummary;
        try {

            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3");
            requestBody.put("prompt", "Sen uzman bir Proje Yöneticisisin. Aşağıda verilen iş tanımını/çalışma kaydını incele ve Türkçe olarak tek cümleyle özetle: " + description);
            requestBody.put("stream", false);

            Map<String, Object> response = restTemplate.postForObject(OLLAMA_URL, requestBody, Map.class);

            if (response != null && response.containsKey("response")) {
                aiSummary = response.get("response").toString().trim();
            } else {
                aiSummary = "AI yanıtı alınamadı.";
            }
        } catch (Exception e) {
            aiSummary = "AI servisine bağlanılamadı (Ollama'nın çalıştığından emin olun): " + description;
        }

        double estimatedHours = Math.max(1.0, Math.round((description.length() / 12.0) * 10.0) / 10.0);

        String category = "Genel Görev";
        String lowerDesc = description.toLowerCase();

        if (lowerDesc.contains("sql") || lowerDesc.contains("database") || lowerDesc.contains("veritabanı")) {
            category = "Database & Query Optimization";
        } else if (lowerDesc.contains("api") || lowerDesc.contains("controller") || lowerDesc.contains("rest")) {
            category = "Backend API Development";
        } else if (lowerDesc.contains("security") || lowerDesc.contains("jwt") || lowerDesc.contains("auth") || lowerDesc.contains("güvenlik")) {
            category = "Security & Auth Integration";
        }

        return new AiAnalysisResponseDto(estimatedHours, category, aiSummary);
    }
}