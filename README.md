# Smart Tracker - İş Raporu ve Efor Analiz Sistemi

**Smart Tracker**; yazılım geliştirme ekiplerinin günlük iş raporlarını (WorkLog) kaydetmelerini, bu veriler üzerinden efor analizleri yapmalarını ve yapay zeka entegrasyonu ile serbest metinlerden otomatik metrik çıkarmalarını sağlayan kurumsal bir RESTful Spring Boot backend servisidir.

---

##  Teknolojiler ve Kütüphaneler

* **Java 17 / 21 & Spring Boot 3**
* **Spring Data JPA & Hibernate** (PostgreSQL / H2)
* **Spring Security & JWT** (JSON Web Token tabanlı kimlik doğrulama)
* **Spring AI / NLP Engine** (Serbest metin analizi ve efor tahmini)
* **Lombok**
* **Swagger UI / OpenAPI 3** (İnteraktif API dokümantasyonu)
* **JUnit 5 & Mockito** (Birim testleri)
* **Docker & Docker Compose** (Containerization)

---

##  Mimari ve Öne Çıkan Özellikler

* **Katmanlı Mimari (Layered Architecture):** `Controller`, `Service`, `Repository` ve `DTO` katmanları ile sorumlulukların ayrıştırılması (*Separation of Concerns*).
* **N+1 Sorgu Optimizasyonu:** `WorkLogRepository` üzerinde `@EntityGraph` ve custom JPQL (`JOIN FETCH`) kullanılarak ilişkili verilerin tek SQL sorgusu ile performanslı şekilde çekilmesi.
* **SQL Aggregation & Analitik Raporlama:** Feature ve Kullanıcı bazlı toplam eforların veritabanı seviyesinde `GROUP BY` ve aggregation fonksiyonları kullanılarak yüksek performansla hesaplanması.
* **Global Exception Handling:** `@RestControllerAdvice` ile merkezi hata yönetimi ve özelleştirilmiş `ErrorResponseDto` çıktıları.

---

## 🤖 AI & NLP Analiz Servisi

* **Teknoloji:** Rule-Based Natural Language Processing (NLP) Engine / Spring AI Integration
* **Açıklama:** Kullanıcıların girdiği serbest metin formatındaki iş raporlarını (WorkLog) analiz eder. Metindeki teknik kelimeleri ve efor parametrelerini tarayarak iş kategorisini, özet bilgiyi ve tahmini tamamlama süresini otomatik hesaplar.
* **Mimarisi:** Esnek `AiService` arayüzü sayesinde ileride Spring AI veya OpenAI REST istemcisine doğrudan tak-çıkar (*pluggable*) olarak entegre edilebilecek yapıda tasarlanmıştır.

---

##  Öne Çıkan REST API Endpoint'leri

| Metot | Endpoint | Açıklama | Access |
|---|---|---|---|
| `POST` | `/api/v1/auth/login` | Kullanıcı girişi ve JWT Token alımı | Public |
| `POST` | `/api/v1/work-logs` | Yeni iş raporu ekleme | Authenticated |
| `GET` | `/api/v1/work-logs/search` | Filtreli iş raporu arama (Specification) | Authenticated |
| `POST` | `/api/v1/ai/analyze` | Serbest metinden AI analizi ve metrik çıkarma | Authenticated |
| `GET` | `/api/v1/analytics/feature-effort` | Feature bazlı efor karşılaştırma raporu | Authenticated |
| `GET` | `/api/v1/analytics/user-effort` | Kullanıcı bazlı toplam efor analizi | Authenticated |

---

##  İnteraktif API Dokümantasyonu (Swagger)

Uygulama çalıştıktan sonra tüm API endpoint'lerini interaktif olarak test etmek için aşağıdaki adresi ziyaret edebilirsiniz:

* **Swagger UI:** [http://localhost:8080/swagger-ui/index.html](http://localhost:8080/swagger-ui/index.html)

---

##  Projeyi Yerel Ortamda Çalıştırma

### Gereksinimler
* **Java 17+**
* **Maven 3.8+**
* **Docker & Docker Compose** (Opsiyonel - PostgreSQL için)

### 1. Yöntem: Maven Wrapper ile Çalıştırma

Proje dizininde aşağıdaki komutu çalıştırarak uygulamayı ayağa kaldırabilirsiniz:

```bash
# Linux / macOS
./mvnw spring-boot:run

# Windows (CMD / PowerShell)
mvnw.cmd spring-boot:run