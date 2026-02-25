package myle.testcases;

import myle.utilities.Links;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;

import java.io.File;
import java.time.Duration;

public class BaseTest {
    protected WebDriver driver;

    @BeforeMethod
    public void setUp() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();

        //options.addArguments("--incognito");
        options.addArguments("--headless=new");

        options.addArguments("--disable-notifications");
        options.addArguments("--disable-infobars");
        options.addArguments("--disable-save-password-bubble");
        options.addArguments("--host-resolver-rules=MAP doubleclick.net 127.0.0.1");
        options.addArguments("--host-resolver-rules=MAP googlesyndication.com 127.0.0.1");
        options.addArguments("--host-resolver-rules=MAP googleads.g.doubleclick.net 127.0.0.1");
        driver = new ChromeDriver(options);
        driver.manage().window().maximize();

        driver.get(Links.DOMAIN);

    }

    @AfterMethod
    public void tearDown() throws InterruptedException {
        Thread.sleep(3000);
        driver.quit();
    }

}
