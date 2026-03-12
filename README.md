# QA Automation Portfolio

A collection of QA automation projects covering UI automation, load testing, and API testing.

---

## Projects

### 1. UI Automation — `ui-automation/`
Selenium-based end-to-end UI test suite built with Java and JUnit 5.

- Page Object Model (POM) design pattern
- Cross-browser support (Chrome, Firefox)
- Screenshot on failure
- HTML test reports via Maven Surefire

**Tech:** Java 17, Selenium 4, JUnit 5, AssertJ, Maven

---

### 2. Load Testing — `load-testing/`
Locust-based load test suite for an e-commerce search module.

- Multiple search scenarios (valid, empty, special characters)
- Response time analysis
- Bot protection findings documented

**Tech:** Python 3.12, Locust 2.43

---

### 3. API Testing — `api-automation/`
REST API test suite covering full CRUD operations.

- Full pet lifecycle (Create → FindByStatus → UploadImage → Update → Delete)
- Positive and negative scenarios
- Known API bugs documented and skipped gracefully via assumeFalse
- Dynamic test data via JavaFaker, no hardcoded values

**Tech:** Java-RestAssured, Junit5, Lombok, Jackson Databind, Maven, Javafaker, Logback


## Structure

```
├── ui-automation/
│   ├── src/
│   └── pom.xml
├── load-testing/
│   ├── locustfile.py
│   └── requirements.txt
└── api-automation/
    ├── src/
    └── pom.xml
```
