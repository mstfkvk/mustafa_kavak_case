package com.insider.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class LeverJobListPage extends BasePage {

    @FindBy(css = ".filter-button-wrapper > .filter-button.filter-button-mlp ")
    private List<WebElement> allFilters; // 4 filtreyi dönecek, indexle tıklatıcaz

    @FindBy(xpath = "//*[contains(@aria-label,'Filter by Location type')]//a")
    private List<WebElement> listOfLocationTypes;

    @FindBy(xpath = "//*[contains(@aria-label,'Filter by Location: All')]//a")
    private List<WebElement> listOfLocations;

    @FindBy(xpath = "//*[contains(@aria-label,'Filter by Team')]//a")
    private List<WebElement> listOfJobLists;

    @FindBy(xpath = "//*[contains(@aria-label,'Filter by Work type')]//a")
    private List<WebElement> listOfWorkTypes;

    @FindBy(css = ".posting>.posting-apply a")
    private List<WebElement> applyButtons;

    @FindBy(css = "h5[data-qa='posting-name']") // verify icin
    private List<WebElement> jobTitles;

    @FindBy(css = ".posting-categories span.sort-by-location") // verify için
    private List<WebElement> jobLocations;

    public LeverJobListPage(WebDriver driver) {
        super(driver);
    }

    public void filterByLocationTypes(String locationTypes) {
        click(allFilters.get(0));
        waitForVisibilityOfAll(listOfLocationTypes).stream()
                .filter(el -> el.getText().contains(locationTypes))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void filterByLocations(String location) {
        dismissBannerIfPresent();
        waitForPageLoad();
        jsClick(allFilters.get(1));
        WebElement locationItem = wait.until(ExpectedConditions.presenceOfElementLocated(
                By.xpath("//*[contains(@aria-label,'Filter by Location: All')]//a[contains(text(),'" + location
                        + "')]")));
        jsClick(locationItem);
    }

    public void filterByJobLists(String jobList) {
        click(allFilters.get(2));
        waitForVisibilityOfAll(listOfJobLists).stream()
                .filter(el -> el.getText().contains(jobList))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    public void filterByWorkTypes(String workType) {
        click(allFilters.get(3));
        waitForVisibilityOfAll(listOfWorkTypes).stream()
                .filter(el -> el.getText().contains(workType))
                .findFirst()
                .ifPresent(WebElement::click);
    }

    // her dropdowndaki seçenekleri aldık, sonra verify için
    public List<String> getLocationTypes() {
        return waitForVisibilityOfAll(listOfLocationTypes)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getLocations() {
        return waitForVisibilityOfAll(listOfLocations)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getJobLists() {
        return waitForVisibilityOfAll(listOfJobLists)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    public List<String> getWorkTypes() {
        return waitForVisibilityOfAll(listOfWorkTypes)
                .stream()
                .map(WebElement::getText)
                .toList();
    }

    // //////////

    public LeverJobDetailPage clickApply(int index) {
        waitForVisibilityOfAll(applyButtons);
        scrollToElement(applyButtons.get(index));
        applyButtons.get(index).click();

        return new LeverJobDetailPage(driver);
    }

    public List<String> getJobTitles() {
        List<String> titles = new ArrayList<>();
        for (WebElement element : waitForVisibilityOfAll(jobTitles)) {
            titles.add(element.getText());
        }
        return titles;
    }

    public List<String> getJobLocations() {
        List<String> locations = new ArrayList<>();
        for (WebElement element : waitForVisibilityOfAll(jobLocations)) {
            locations.add(element.getText());
        }
        return locations;
    }
}