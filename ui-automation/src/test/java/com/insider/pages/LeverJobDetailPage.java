package com.insider.pages;

import org.openqa.selenium.WebDriver;

public class LeverJobDetailPage extends BasePage {

    public LeverJobDetailPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLeverPage() {
        return driver.getCurrentUrl().contains("jobs.lever.co");
    }
}