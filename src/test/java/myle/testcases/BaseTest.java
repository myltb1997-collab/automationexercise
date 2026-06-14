package myle.testcases;

import myle.utilities.Links;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() {
        ChromeOptions options = new ChromeOptions();
        options.setPageLoadStrategy(PageLoadStrategy.EAGER);

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--host-resolver-rules=MAP doubleclick.net 127.0.0.1");
        options.addArguments("--host-resolver-rules=MAP googlesyndication.com 127.0.0.1");
        options.addArguments("--host-resolver-rules=MAP googleads.g.doubleclick.net 127.0.0.1");
        driver = new ChromeDriver(options);
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(60));
        driver.manage().window().setSize(new Dimension(1920, 1080));

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
