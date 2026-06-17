package myle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {
    protected WebDriver driver;
    protected Actions actions;

    protected WebDriverWait wait;
    private static final int TIMEOUT = 15;


    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(TIMEOUT));
    }


    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public void scrollToTop(WebElement element) {
        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].scrollIntoView(true);", element);
    }


    public WebElement waitToVisible(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    public void waitToInvisible(WebElement element) {
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitToBeClickable(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForPageStable() {
        wait.until(d ->
                ((JavascriptExecutor) d)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    /**
     * Chờ element hiển thị dựa trên locator By
     * @param by Locator của element
     * @return WebElement khi visible
     */
    public WebElement waitForElementVisible(By by) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    /**
     * Chờ element có mặt trong DOM (không cần visible)
     * @param by Locator của element
     * @return WebElement khi present
     */
    public WebElement waitForElementPresent(By by) {
        return wait.until(ExpectedConditions.presenceOfElementLocated(by));
    }

    /**
     * Chờ element có thể click được
     * @param by Locator của element
     */
    public void waitForElementClickable(By by) {
        wait.until(ExpectedConditions.elementToBeClickable(by));
    }

    /**
     * Chờ element biến mất khỏi DOM
     * @param by Locator của element
     */
    public void waitForElementInvisible(By by) {
        wait.until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    /**
     * Chờ text xuất hiện trong element
     * @param by Locator của element
     * @param text Text cần chờ
     */
    public void waitForTextInElement(By by, String text) {
        wait.until(ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

    /**
     * Chờ URL chứa text nhất định
     * @param urlPart URL fragment cần chờ
     */
    public void waitForUrlContains(String urlPart) {
        wait.until(ExpectedConditions.urlContains(urlPart));
    }

    /**
     * Tạo WebDriverWait với timeout custom
     * @param timeoutSeconds Thời gian timeout (giây)
     * @return WebDriverWait instance
     */
    public WebDriverWait createWaitWithTimeout(int timeoutSeconds) {
        return new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
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
        //Cuon xuong 1 chut tranh vung quang cao che
        //  js.executeScript("window.scrollBy(0, 400);");
    }


    public void safeClick(WebElement element) {
        JavascriptExecutor js = (JavascriptExecutor) driver;

        for (int i = 0; i < 3; i++) {
            try {
                hideAds();

                // Scroll element into view if not currently visible
                Boolean inView = (Boolean) js.executeScript(
                        "var r = arguments[0].getBoundingClientRect();" +
                                "return r.top >= 0 && r.bottom <= window.innerHeight;", element);
                if (!inView) js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                waitToBeClickable(element);
                element.click();
                return;

            } catch (Exception e) {
                System.out.println("Retry click: " + (i + 1) + " reason: " + e.getClass().getSimpleName());
                try {
                    Thread.sleep(500);
                } catch (InterruptedException ignored) {
                }
            }
        }

        // Fallback: JavaScript click if 3 attempts fail
        js.executeScript("arguments[0].click();", element);
    }

}
