package myle.pages;

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

    @FindBy(css = "a[href='/products']")
    WebElement navProducts;
    @FindBy(css = "a[href='/view_cart']")
    WebElement navCart;
    @FindBy(css = "a[href='/login']")
    WebElement navsignIn;

    @FindBy(css = "a[href='/test_cases']")
    WebElement navTestcases;
    @FindBy(css = "a[href='/contact_us']")
    WebElement navContactUs;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.actions = new Actions(driver);
        PageFactory.initElements(driver, this);
    }

    public ProductPage movToProductPage() {
        navProducts.isDisplayed();
        navProducts.click();
        return new ProductPage(driver);
    }

    public ContactUsPage moveToTestCasePage() {
        navTestcases.isDisplayed();
        navTestcases.click();
        return new ContactUsPage(driver);
    }

    public CartPage moveToCartPage() {
        navCart.click();
        return new CartPage(driver);
    }

    public SignUpPage movToLoginPage() {
        navsignIn.isEnabled();
        navsignIn.click();
        return new SignUpPage(driver);
    }

    public ContactUsPage movToContactPage() {
        Assert.assertTrue(navContactUs.isDisplayed());
        navContactUs.click();
        return new ContactUsPage(driver);
    }

    public void scrollToBottom() {
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollTo(0,document.body.scrollHeight);");
    }

    public WebElement waitToVisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.visibilityOf(element));
        return element;
    }

    public void waitToInvisible(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.invisibilityOf(element));
    }

    public void waitToBeClickable(WebElement element) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        wait.until(ExpectedConditions.elementToBeClickable(element));
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

                // Scroll nhẹ nếu element chưa trong vùng hiển thị
                Boolean inView = (Boolean) js.executeScript(
                        "var r = arguments[0].getBoundingClientRect();" +
                                "return r.top >= 0 && r.bottom <= window.innerHeight;", element);
                if (!inView) js.executeScript("arguments[0].scrollIntoView({block:'center'});", element);

                waitToBeClickable(element);
                element.click();
                return; // thành công thì thoát

            } catch (Exception e) {
                System.out.println("Retry click: " + (i + 1) + " vì: " + e.getClass().getSimpleName());
                try { Thread.sleep(500); } catch (InterruptedException ignored) {}
            }
        }

        // fallback: click bằng JS nếu thất bại 3 lần
        js.executeScript("arguments[0].click();", element);
        System.out.println("Dung JS click thanh cong!");
    }


}
