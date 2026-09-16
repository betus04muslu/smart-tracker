package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import com.staj.smarttracker.entity.User;
import com.staj.smarttracker.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.http.client.SimpleClientHttpRequestFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AiService {

    private final UserRepository userRepository;
    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    private RestTemplate getRestTemplateWithTimeout() {
        SimpleClientHttpRequestFactory factory = new SimpleClientHttpRequestFactory();
        factory.setConnectTimeout(3000);
        factory.setReadTimeout(5000);
        return new RestTemplate(factory);
    }

    private double calculateHours(int expYears, boolean isDomainMatch) {
        double hours = 24.0 - (expYears * 0.9);
        if (isDomainMatch) {
            hours = hours * 0.85;
        }
        return Math.round(Math.max(hours, 3.0) * 10.0) / 10.0;
    }

    public AiAnalysisResponseDto analyzeTaskWithExpertise(String description, String expertiseArea, int experienceYears) {
        if (description == null || description.trim().isEmpty() || description.equals("\"\"")) {
            return new AiAnalysisResponseDto(16.0, "Genel Analiz", "Lütfen analiz için geçerli bir iş tanımı girin.", "Ayşe Hanım", 9.5);
        }

        String cleanDescription = description.replace("\"", "").trim();
        String targetArea = expertiseArea != null ? expertiseArea.toLowerCase().trim() : "genel";

        List<User> allUsers = userRepository.findAll();

        if (allUsers == null || allUsers.isEmpty()) {
            return new AiAnalysisResponseDto(14.0, expertiseArea, "Veritabanında kayıtlı kullanıcı bulunamadı.", "Varsayılan Uzman", 10.0);
        }

        User bestUser = null;
        int bestPriority = 999; // Düşük sayı = Daha yüksek öncelik

        boolean isTargetFrontend = targetArea.contains("frontend") || targetArea.contains("react") || targetArea.contains("ui");
        boolean isTargetBackend = targetArea.contains("backend") || targetArea.contains("api") || targetArea.contains("java") || targetArea.contains("spring");

        for (User u : allUsers) {
            String uArea = u.getExpertiseArea() != null ? u.getExpertiseArea().toLowerCase().trim() : "";
            int priority = 100;

            boolean isUserFrontend = uArea.contains("frontend");
            boolean isUserBackend = uArea.contains("backend");
            boolean isUserFullstack = uArea.contains("fullstack");

            if (isTargetFrontend) {
                if (isUserFrontend) {
                    priority = 1; // En iyi eşleşme (Saf Frontend)
                } else if (isUserFullstack) {
                    priority = 3; // Alternatif (Fullstack)
                } else {
                    priority = 100; // Uymuyor (Backend)
                }
            } else if (isTargetBackend) {
                if (isUserBackend) {
                    priority = 1; // En iyi eşleşme (Saf Backend)
                } else if (isUserFullstack) {
                    priority = 3; // Alternatif (Fullstack)
                } else {
                    priority = 100; // Uymuyor (Frontend)
                }
            } else {
                priority = 2; // Kategori belirtilmediyse herkes uygun
            }

            // Aynı öncelik seviyesindeyse deneyimi fazla olanı seçmek için küçük bir kıstas
            if (bestUser == null || priority < bestPriority) {
                bestPriority = priority;
                bestUser = u;
            } else if (priority == bestPriority && bestUser != null) {
                if (u.getExperienceYears() != null && bestUser.getExperienceYears() != null) {
                    if (u.getExperienceYears() > bestUser.getExperienceYears()) {
                        bestUser = u;
                    }
                }
            }
        }

        if (bestUser == null || bestPriority == 100) {
            bestUser = allUsers.get(0);
        }

        String recommendedEmployeeName = bestUser.getName() != null ? bestUser.getName() : "Ayşe Hanım";
        int recommendedExp = bestUser.getExperienceYears() != null ? bestUser.getExperienceYears() : 4;
        double userFormHours = calculateHours(experienceYears, true);
        double recommendedFinalHours = calculateHours(recommendedExp, true);

        // Profesyonel Rapor
        String aiReport = String.format(
                "Akıllı Kaynak Analizi Raporu:\n\n" +
                        "1. Görev Analizi: \"%s\" görevi incelenmiştir.\n" +
                        "2. Önerilen Kaynak: Seçilen uzmanlık alanına uygun, %d yıllık deneyime sahip **%s** (%s) bu iş için en uygun profildir.\n" +
                        "3. Süre Optimizasyonu: Uzman personelin yetkinliği sayesinde efor süresi %.1f saate optimize edilmiştir.",
                cleanDescription, recommendedExp, recommendedEmployeeName, bestUser.getExpertiseArea(), recommendedFinalHours
        );

        // Ollama Entegrasyonu
        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3");
            requestBody.put("prompt", "Türkçe yanıt ver: " + cleanDescription + " görevi için " + recommendedEmployeeName + " neden uygundur?");
            requestBody.put("stream", false);

            HttpHeaders headers = new HttpHeaders();
            headers.setContentType(MediaType.APPLICATION_JSON);
            HttpEntity<Map<String, Object>> entity = new HttpEntity<>(requestBody, headers);

            RestTemplate restTemplate = getRestTemplateWithTimeout();
            @SuppressWarnings("rawtypes")
            ResponseEntity<Map> responseEntity = restTemplate.postForEntity(OLLAMA_URL, entity, Map.class);
            @SuppressWarnings("unchecked")
            Map<String, Object> response = responseEntity.getBody();

            if (response != null && response.containsKey("response")) {
                aiReport = response.get("response").toString().trim();
            }
        } catch (Exception e) {
            System.out.println("Bilgilendirme: Ollama yanıt vermedi, sistem akıllı kural tabanlı raporlama modunda çalışıyor.");
        }

        return new AiAnalysisResponseDto(userFormHours, expertiseArea, aiReport, recommendedEmployeeName, recommendedFinalHours);
    }
}