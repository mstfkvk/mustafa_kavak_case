package com.insider.utils;

import com.insider.driver.DriverManager;
import org.junit.jupiter.api.extension.AfterEachCallback;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public class ScreenshotExtension implements AfterEachCallback {

    private static final Logger log = LoggerFactory.getLogger(ScreenshotExtension.class);
    private static final String SCREENSHOT_DIR = "target/screenshots/";

    @Override
    public void afterEach(ExtensionContext context) {
        boolean testFailed = context.getExecutionException().isPresent();

        if (testFailed) {
            WebDriver driver = DriverManager.peekDriver();
            if (driver == null) {
                log.warn("Driver is null, screenshot cannot be taken");
            } else {
                try {
                    Files.createDirectories(Paths.get(SCREENSHOT_DIR));
                    String timestamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss"));
                    String fileName = context.getDisplayName() + "_" + timestamp + ".png";
                    byte[] screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
                    Files.write(Paths.get(SCREENSHOT_DIR + fileName), screenshot);
                    log.info("Screenshot saved -> {}{}", SCREENSHOT_DIR, fileName);
                } catch (IOException e) {
                    log.error("Screenshot failed: {}", e.getMessage());
                }
            }
        }

        log.info("Driver closing");
        DriverManager.quitDriver();
    }
}