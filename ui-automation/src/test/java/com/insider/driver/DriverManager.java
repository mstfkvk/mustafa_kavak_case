package com.insider.driver;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DriverManager {

    private static final Logger log = LoggerFactory.getLogger(DriverManager.class);
    private static final ThreadLocal<WebDriver> driver = new ThreadLocal<>();

    private DriverManager() {
    }

    public static WebDriver getDriver() {
        if (driver.get() == null) {
            String browser = System.getProperty("browser", "chrome");
            driver.set(createDriver(browser));
            log.info("Initializing driver -> browser: {}", browser);
        }
        return driver.get();
    }

    private static WebDriver createDriver(String browser) {
        switch (browser.toLowerCase()) {
            case "firefox":
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.addArguments("--start-maximized");
                log.info("Firefox driver created");
                return new FirefoxDriver(firefoxOptions);
            default:
                ChromeOptions chromeOptions = new ChromeOptions();
                chromeOptions.addArguments("--start-maximized");
                log.info("Chrome driver created");
                return new ChromeDriver(chromeOptions);
        }
    }

    public static void quitDriver() {
        if (driver.get() != null) {
            log.info("Quitting driver");
            driver.get().quit();
            driver.remove();
        }
    }

    public static WebDriver peekDriver() {
        return driver.get(); // yeni driver açmaz, null dönebilir
    }
}