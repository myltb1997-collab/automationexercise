package myle.testcases;

import myle.utilities.Links;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import myle.utilities.DriverFactory;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        driver = DriverFactory.createChromeDriver();
        try {
            driver.get(Links.DOMAIN);
        } catch (TimeoutException e) {
            ((JavascriptExecutor) driver).executeScript("window.stop();");
        }

    }

    @AfterMethod
    public void tearDown() {
        driver.quit();
    }

}
