package com.insider.tests;

import com.insider.driver.DriverManager;
import com.insider.pages.CareersPage;
import com.insider.pages.HomePage;
import com.insider.pages.LeverJobDetailPage;
import com.insider.pages.LeverJobListPage;
import com.insider.utils.ConfigReader;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static org.assertj.core.api.Assertions.assertThat;

public class InsiderTest extends BaseTest {

        private static final Logger log = LoggerFactory.getLogger(InsiderTest.class);

        @Test
        @DisplayName("Insider home page should load with all main blocks")
        void shouldLoadHomePage() {

                WebDriver driver = DriverManager.getDriver();
                HomePage homePage = new HomePage(driver);

                log.info("Opening home page");
                homePage.open(ConfigReader.get("base.url"));

                assertThat(homePage.isHeaderDisplayed())
                                .as("Header should be visible")
                                .isTrue();

                assertThat(homePage.isMainPageDisplayed())
                                .as("Main page should be visible")
                                .isTrue();

        }

        @Test
        @DisplayName("QA jobs should be listed and verified for Istanbul")
        void shouldListAndVerifyQAJobsForIstanbul() {

                WebDriver driver = DriverManager.getDriver();
                HomePage homePage = new HomePage(driver);

                log.info("Opening careers page");
                homePage.open(ConfigReader.get("careers.url"));

                CareersPage careersPage = new CareersPage(driver);

                assertThat(careersPage.isSeeAllButtonDisplayed())
                                .as("'See all teams' button should be visible")
                                .isTrue();

                careersPage.clickSeeAllButton();
                assertThat(careersPage.isQaLinkDisplayed())
                                .as("'Quality Assurance' link should be visible")
                                .isTrue();
                log.info("Clicking Quality Assurance section");

                LeverJobListPage jobListPage = careersPage.clickQualityAssurance();

                assertThat(driver.getCurrentUrl())
                                .as("Should redirect to Lever job list page")
                                .contains("lever.co");

                log.info("Filtering by location: Istanbul");
                jobListPage.filterByLocations("Istanbul");

                assertThat(jobListPage.getCurrentUrl())
                                .as("Should be on Lever job list page")
                                .contains("Istanbul");

                log.info("Verifying job titles");
                assertThat(jobListPage.getJobTitles())
                                .as("All job titles should contain Quality Assurance")
                                .allMatch(title -> title.contains("Quality Assurance") || title.contains("QA"),
                                                "title contains QA or Quality Assurance");

                log.info("Verifying job locations");
                assertThat(jobListPage.getJobLocations())
                                .as("All locations should contain Istanbul")
                                .allMatch(location -> location.toUpperCase().contains("ISTANBUL"),
                                                "location contains Istanbul");

                log.info("Clicking apply on first job");
                LeverJobDetailPage jobDetailPage = jobListPage.clickApply(0);

                assertThat(jobDetailPage.isLeverPage())
                                .as("Should redirect to job detail page")
                                .isTrue();
        }
}