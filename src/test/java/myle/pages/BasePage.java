package myle.pages;

import myle.utilities.WaitUtil;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected Actions actions;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void scrollToTop(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }

    public void hoverElement(WebElement element) {
        actions.moveToElement(element).perform();
    }

    public String normalizeText(WebElement element, String textToRemove) {
        return element.getText().replace(textToRemove, "").trim();
    }

    public void hideAds() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript(
                "document.querySelectorAll(" +
                        "'iframe[id^=\"aswift_\"],' +" +
                        "'div[id^=\"aswift_\"],' +" +
                        "'div[id^=\"google_ads_iframe\"],' +" +
                        "'iframe[src*=\"doubleclick\"],' +" +
                        "'iframe[src*=\"ads\"]'" +
                        ").forEach(function(e){ e.style.display='none'; e.remove(); });"
        );
    }

    public void safeClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 3; i++) {
            try {
                hideAds();

                Boolean inView = (Boolean) js.executeScript(
                        "var r = arguments[0].getBoundingClientRect();" +
                                "return r.top >= 0 && r.bottom <= window.innerHeight;", element);
                if (!inView) {
                    js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);
                }

                WaitUtil.waitForClickable(driver, element);
                element.click();
                return;
            } catch (Exception e) {
                System.out.println("Retry click: " + (i + 1) + " reason: " + e.getClass().getSimpleName());
                try {
                    WaitUtil.waitForClickable(driver, element, Duration.ofMillis(500));
                } catch (Exception ignored) {
                }
            }
        }

        js.executeScript("arguments[0].click();", element);
    }
}
