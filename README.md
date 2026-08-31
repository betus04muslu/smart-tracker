 Smart Tracker - İş Raporu ve Efor Analiz Sistemi

Smart Tracker; yazılım geliştirme ekiplerinin günlük iş raporlarını (WorkLog) kaydetmelerini, bu veriler üzerinden efor analizleri yapmalarını ve yapay zeka entegrasyonu ile serbest metinlerden otomatik metrik çıkarmalarını sağlayan kurumsal bir RESTful Spring Boot backend servisidir.



 Teknolojiler ve Kütüphaneler

* **Java 17** & **Spring Boot 3**
* **Spring Data JPA** & **Hibernate** (PostgreSQL / H2)
* **Spring Security** & **JWT (JSON Web Token)**
* **Spring AI** (Serbest metin analizi ve efor tahmini)
* **Lombok**
* **Swagger UI / OpenAPI 3** (Interaktif API dokümantasyonu)
* **JUnit 5** & **Mockito** (Birim testleri)



 Mimari ve Öne Çıkan Özellikler

* **Katmanlı Mimari (Layered Architecture):** Controller, Service, Repository ve DTO katmanları ile sorumlulukların ayrıştırılması (Separation of Concerns).
* **N+1 Sorgu Optimizasyonu:** `WorkLogRepository` üzerinde `@EntityGraph` ve custom JPQL kullanılarak ilişkili verilerin tek SQL `JOIN FETCH` sorgusu ile çekilmesi.
* **SQL Aggregation & Analitik Raporlama:** Feature ve Kullanıcı bazlı toplam eforların veritabanı seviyesinde gruplanarak yüksek performansla hesaplanması.
* **Global Exception Handling:** `@RestControllerAdvice` ile merkezi hata yönetimi ve özelleştirilmiş `ErrorResponseDto` çıktıları.


AI & NLP Analiz Servisi
* **Teknoloji:** Rule-Based Natural Language Processing (NLP) Engine
* **Açıklama:** Kullanıcıların girdiği serbest metin formatındaki iş raporlarını (WorkLog) analiz eder. Metindeki teknik kelimeleri ve efor parametrelerini tarayarak iş kategorisini, özet bilgiyi ve tahmini tamamlama süresini otomatik hesaplar.
* **Mimarisi:** Esnek `AiService` arayüzü sayesinde ileride Spring AI veya OpenAI REST istemcisine doğrudan tak-çıkar (pluggable) olarak entegre edilebilecek yapıda tasarlanmıştır.


Projeyi Yerel Ortamda Çalıştırma

 1. Gereksinimler
* Java 17+
* Maven 3.8+

 2. Uygulamayı Başlatma
Proje dizininde aşağıdaki komutu çalıştırarak uygulamayı ayağa kaldırabilirsiniz:

```bash
./mvnw spring-boot:run