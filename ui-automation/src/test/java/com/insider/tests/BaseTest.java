package com.insider.tests;

import com.insider.driver.DriverManager;
import com.insider.utils.ScreenshotExtension;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

@ExtendWith(ScreenshotExtension.class)
public class BaseTest {

    private static final Logger log = LoggerFactory.getLogger(BaseTest.class);

    @BeforeEach
    void setUp() {
        log.info("Driver starting -> browser: {}", System.getProperty("browser", "chrome"));
        DriverManager.getDriver();
    }
}