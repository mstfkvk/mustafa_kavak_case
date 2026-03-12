package com.insider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class CareersPage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(CareersPage.class);

    @FindBy(xpath = "//a[contains(text(),\"See all teams\")]")
    private WebElement seeAllButton;

    @FindBy(css = "[data-department='Quality Assurance'] a.insiderone-icon-cards-grid-item-btn")
    private WebElement qaLink;

    public CareersPage(WebDriver driver) {
        super(driver);
    }

    public void open(String url) {
        log.info("Opening: {}", url);
        driver.get(url);
    }

    public boolean isSeeAllButtonDisplayed() {
        scrollToElementWithOffset(seeAllButton, -120);
        return isDisplayed(seeAllButton);
    }

    public void clickSeeAllButton() {
        scrollToElementWithOffset(seeAllButton, -120);
        jsClick(seeAllButton);
    }

    public boolean isQaLinkDisplayed() {
        wait.until(ExpectedConditions.attributeContains(qaLink, "href", "lever.co"));
        scrollToElementWithOffset(qaLink, -120);
        return isDisplayed(qaLink);
    }

    public LeverJobListPage clickQualityAssurance() {
        wait.until(ExpectedConditions.attributeContains(qaLink, "href", "lever.co"));
        String href = qaLink.getAttribute("href");
        log.info("QA href: {}", href);
        driver.get(href);
        return new LeverJobListPage(driver);
    }
}