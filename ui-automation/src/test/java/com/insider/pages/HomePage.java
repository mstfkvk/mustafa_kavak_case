package com.insider.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class HomePage extends BasePage {

    private static final Logger log = LoggerFactory.getLogger(HomePage.class);

    public HomePage(WebDriver driver) {
        super(driver);
    }

    @FindBy(css = "#navigation")
    private WebElement header;

    @FindBy(css = "#navigation + .flexible-layout")
    private WebElement body;

    @FindBy(css = "#wt-cli-cookie-banner #wt-cli-accept-all-btn")
    private WebElement acceptCookiesButton;

    public void open(String url) {
        log.info("Opening: {}", url);
        driver.get(url);
        waitForPageLoad(); // bu tüm sayfanın yüklendiğini bekleyen komut aslında, bundan sonraki adımların doğruluğunu kanıtlayacaktır
        acceptCookies();
    }

    public void acceptCookies() {
        if (isDisplayed(acceptCookiesButton)) {
            click(acceptCookiesButton);
        }
    }

    public boolean isHeaderDisplayed() {
        return isDisplayed(header);
    }

    public boolean isMainPageDisplayed() {
        return isDisplayed(body);
    }

}