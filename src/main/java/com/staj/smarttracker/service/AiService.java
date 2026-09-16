package com.staj.smarttracker.service;

import com.staj.smarttracker.dto.AiAnalysisResponseDto;
import com.staj.smarttracker.entity.Project;
import com.staj.smarttracker.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Service
@RequiredArgsConstructor
public class AiService {

    private final RestTemplate restTemplate = new RestTemplate();
    private final ProjectRepository projectRepository;
    private final String OLLAMA_URL = "http://localhost:11434/api/generate";

    public AiAnalysisResponseDto analyzeTaskWithExpertise(String description, String expertiseArea, int experienceYears) {
        if (description == null || description.trim().isEmpty() || description.equals("\"\"")) {
            return new AiAnalysisResponseDto(8.0, "Genel Analiz", "Lütfen analiz için geçerli bir iş tanımı girin.");
        }

        String cleanDescription = description.replace("\"", "").trim();

        // 1. İşin türünü tespit et (Backend mi?)
        boolean isBackendTask = cleanDescription.toLowerCase().contains("spring") ||
                cleanDescription.toLowerCase().contains("sql") ||
                cleanDescription.toLowerCase().contains("api") ||
                cleanDescription.toLowerCase().contains("backend");

        // 2. Veritabanındaki projeleri tara ve ilgili alandaki proje miktarını hesapla
        List<Project> allProjects = projectRepository.findAll();
        int matchingDomainProjectCount = 0;

        for (Project proj : allProjects) {
            String field = proj.getProjectField() != null ? proj.getProjectField().toLowerCase() : "";
            if (isBackendTask && (field.contains("backend") || field.contains("fullstack"))) {
                matchingDomainProjectCount++;
            } else if (!isBackendTask && (field.contains("frontend") || field.contains("fullstack"))) {
                matchingDomainProjectCount++;
            }
        }

        // 3. Llama 3'e ters orantı kuralını ve verileri net bir şekilde veriyoruz
        String prompt = String.format(
                "Sen akıllı bir yazılım kapasite planlama yapay zekasısın. Aşağıdaki verilere göre bu işin kaç saat süreceğini ve detaylı analiz raporunu belirle:\n\n" +
                        "- İş Tanımı: \"%s\"\n" +
                        "- Çalışanın Rolü: %s\n" +
                        "- Çalışanın Deneyimi: %d yıl (Deneyimi fazla olanlar işi daha kısa sürede bitirmelidir)\n" +
                        "- İlgili Alandaki Geçmiş Proje Sayısı: %d adet (Proje sayısı ve alanı eşleşenler işi daha kısa sürede bitirmelidir)\n\n" +
                        "ÖNEMLİ KURALLAR:\n" +
                        "1. Yanıtının uygun bir yerine kesinlikle 'TAHMİNİ_SAAT: [sayı]' şeklinde toplam saati yaz (Örn: TAHMİNİ_SAAT: 14).\n" +
                        "2. Ardından profesyonel Türkçe analiz raporunu yaz.",
                cleanDescription, expertiseArea, experienceYears, matchingDomainProjectCount
        );

        double aiEstimatedHours = 20.0;
        String aiReport = "";

        try {
            Map<String, Object> requestBody = new HashMap<>();
            requestBody.put("model", "llama3");
            requestBody.put("prompt", prompt);
            requestBody.put("stream", false);

            Map<String, Object> response = restTemplate.postForObject(OLLAMA_URL, requestBody, Map.class);
            if (response != null && response.containsKey("response")) {
                aiReport = response.get("response").toString().trim();

                Pattern pattern = Pattern.compile("TAHMİNİ_SAAT[:\\s]*([0-9]+(?:\\.[0-9]+)?)", Pattern.CASE_INSENSITIVE);
                Matcher matcher = pattern.matcher(aiReport);
                if (matcher.find()) {
                    aiEstimatedHours = Double.parseDouble(matcher.group(1));
                }
            }
        } catch (Exception e) {
            aiReport = "Llama 3 analizi sırasında geçici bir bağlantı sorunu yaşandı.";
        }

        String category = expertiseArea != null ? expertiseArea : "Genel Yazılım";
        return new AiAnalysisResponseDto(aiEstimatedHours, category, aiReport);
    }
}