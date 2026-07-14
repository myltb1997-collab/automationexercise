package com.automationexercise.tests;

import config.ConfigurationManager;
import driver.DriverFactory;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod(alwaysRun = true)
    public void setUp() {
        driver = DriverFactory.initDriver();
        maximizeBrowser();
        navigateToBaseUrlIfNeeded();
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (!result.isSuccess() && driver != null) {
            System.out.println("⚠️ Test failed: " + result.getMethod().getMethodName());
        }
        
        try {
            DriverFactory.quitDriver(driver);
        } catch (Exception e) {
            System.err.println("Error during test cleanup: " + e.getMessage());
        } finally {
            driver = null;
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    private void maximizeBrowser() {
        try {
            driver.manage().window().maximize();
        } catch (Exception e) {
            System.err.println("Could not maximize browser window: " + e.getMessage());
        }
    }

    private void navigateToBaseUrlIfNeeded() {
        String baseUrl = ConfigurationManager.getBaseUrl();
        if (baseUrl == null || baseUrl.isBlank()) {
            return;
        }

        try {
            String currentUrl = driver.getCurrentUrl();
            if (!baseUrl.equalsIgnoreCase(currentUrl)) {
                driver.get(baseUrl);
            }
        } catch (TimeoutException e) {
            if (driver instanceof JavascriptExecutor) {
                ((JavascriptExecutor) driver).executeScript("window.stop();");
            }
        }
    }
}

