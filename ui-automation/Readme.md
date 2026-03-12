# Insider One UI Automation

Selenium tabanlı UI test otomasyon projesi. [Insider One](https://insiderone.com) careers sayfasını ve [Lever.co](https://jobs.lever.co/insiderone) iş ilanlarını test eder.

---

## 🛠 Teknoloji Stack

| Teknoloji | Versiyon |
|---|---|
| Java | 17 |
| Selenium | 4.18.1 |
| JUnit Jupiter | 5.10.0 |
| AssertJ | 3.24.2 |
| Logback | 1.4.11 |
| Maven | 3.x |

---

## 📁 Proje Yapısı

```
src/test/java/com/insider/
├── driver/
│   └── DriverManager.java        # ThreadLocal WebDriver yönetimi
├── pages/
│   ├── BasePage.java             # Ortak sayfa metodları (click, wait, scroll...)
│   ├── HomePage.java             # insiderone.com ana sayfa
│   ├── CareersPage.java          # /careers sayfası
│   ├── LeverJobListPage.java     # jobs.lever.co iş listesi sayfası
│   └── LeverJobDetailPage.java   # İş detay sayfası
├── tests/
│   ├── BaseTest.java             # @BeforeEach driver setup
│   └── InsiderTest.java          # Test senaryoları
└── utils/
    ├── ConfigReader.java         # config.properties okuyucu
    └── ScreenshotExtension.java  # Hata durumunda screenshot

src/test/resources/
└── config.properties             # base.url, careers.url
```

---

## ✅ Test Senaryoları

### 1. `shouldLoadHomePage`
- Insider One ana sayfasını açar
- Header'ın görünür olduğunu doğrular
- Ana sayfanın yüklendiğini doğrular

### 2. `shouldListAndVerifyQAJobsForIstanbul`
- Careers sayfasını açar
- "See all teams" butonunun görünür olduğunu doğrular
- "See all teams" butonuna tıklar
- Quality Assurance linkinin görünür olduğunu doğrular
- QA linkine tıklayarak Lever.co'ya yönlendirildiğini doğrular
- Location filtresinden "Istanbul" seçer
- URL'de "Istanbul" parametresinin olduğunu doğrular
- Tüm iş ilanı başlıklarının "Quality Assurance" veya "QA" içerdiğini doğrular
- Tüm iş ilanı lokasyonlarının "Istanbul" içerdiğini doğrular
- İlk ilana Apply butonuna tıklayarak detay sayfasına yönlendirildiğini doğrular

---

## ⚙️ Kurulum

### Gereksinimler
- Java 17+
- Maven 3.x
- Chrome / ChromeDriver (otomatik yönetilir)

### Bağımlılıkları Yükle
```bash
mvn clean install -DskipTests
```

---

## 🚀 Testleri Çalıştırma

### Tüm testleri çalıştır
```bash
mvn test
```

### Firefox ile çalıştır
```bash
mvn test -Dbrowser=firefox
```

### Testleri çalıştır + HTML rapor oluştur
```bash
mvn test site --fail-at-end
```

> Rapor: `target/site/surefire-report.html`

---

## 📸 Screenshot

Test başarısız olduğunda otomatik olarak screenshot alınır.

> Konum: `target/screenshots/<test_adı>_<timestamp>.png`

`ScreenshotExtension`, `AfterEachCallback` ile `context.getExecutionException().isPresent()` kontrolü yaparak sadece başarısız testlerde screenshot alır, driver kapanmadan önce.

---

## 📊 Raporlama

`mvn test site --fail-at-end` komutu sonrası:

```
target/
├── screenshots/          # Hatalı test screenshot'ları
├── surefire-reports/     # Ham XML/HTML raporlar
└── site/
    └── surefire-report.html  # Özet HTML rapor
```

`surefire-report.html` içeriği:
- Toplam test sayısı
- Başarılı / Başarısız / Hata / Atlanan sayıları
- Test süreleri
- Hata detayları ve stack trace

---

## 🔧 Konfigürasyon

`src/test/resources/config.properties`:
```properties
base.url=https://insiderone.com
careers.url=https://insiderone.com/careers/
```

---

## 🏗 Mimari

Page Object Model (POM) pattern kullanılmıştır.

- **BasePage**: Tüm page class'larının extend ettiği base class. `WebDriverWait`, `click`, `jsClick`, `scrollToElement`, `waitForVisibilityOfAll` gibi ortak metodları barındırır.
- **DriverManager**: `ThreadLocal<WebDriver>` ile thread-safe driver yönetimi sağlar. `getDriver()` lazy init, `peekDriver()` null-safe erişim, `quitDriver()` temizlik için kullanılır.
- **ScreenshotExtension**: JUnit 5 `AfterEachCallback` ile entegre, sadece başarısız testlerde screenshot alır.