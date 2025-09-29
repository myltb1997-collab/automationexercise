package myle.pages;

import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;

public class BasePage {
    WebDriver driver;

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


}
