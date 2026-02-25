package myle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;

public class HomePage extends BasePage {

    @FindBy(xpath = "//a[text()=' Logout']")
    WebElement signOutBtn;
    @FindBy(xpath = "//img[@alt='Website for automation practice']")
    WebElement sliderLocator;

    @FindBy(css = "#susbscribe_email")
    WebElement subcribeEmailField;
    @FindBy(css = "#subscribe")
    WebElement subscribeBtn;
    @FindBy(css = "div[class='single-widget'] h2")
    WebElement subscriptionTitle;
    @FindBy(css = "#success-subscribe")
    WebElement successSubscribeMsg;
    @FindBy(css = "div.product-image-wrapper")
    List<WebElement> productList;
    @FindBy(css = "div.product-overlay >div > a[data-product-id='1']")
    WebElement addToCartBtn;
    @FindBy(xpath = "//u[normalize-space()='View Cart']")
    WebElement viewCartBtn;
    @FindBy(xpath = "//h2[text()='Features Items']")
    WebElement featuresItemText;

    public HomePage(WebDriver driver) {
        super(driver);
    }

    public boolean isHomePageVisible() {
        return sliderLocator.isDisplayed();
    }

    public void clickLogOutBtn() {
        signOutBtn.isDisplayed();
        signOutBtn.click();
    }

    public String getSubscriptionText() {
        return subscriptionTitle.getText();
    }

    public void setSubscribeEmail(String email) {
        subcribeEmailField.isEnabled();
        subcribeEmailField.clear();
        subcribeEmailField.sendKeys(email);
    }

    public void clickSubscribleBtn() {
        subscribeBtn.isEnabled();
        subscribeBtn.click();
    }

    public String isSubscribeSuccess() {
        //Lay Webelement de getText cua message ve kiem tra
        WebElement successMgs = waitToVisible(successSubscribeMsg);
        String actualMsg = successMgs.getText();
        waitToInvisible(successSubscribeMsg);
        return actualMsg;
    }

    public ProductPage clickViewFirstProduct(int indexNumber) {
        if (productList.isEmpty()) {
            throw new RuntimeException("No products found on this page!");
        }
        WebElement getProduct = productList.get(indexNumber);
        WebElement viewProductBtn = getProduct.findElement(By.cssSelector(".choose a"));
        safeClick(viewProductBtn);
        return new ProductPage(driver);
    }

    public void clickAddToCartBtn() throws InterruptedException {
        if (productList.isEmpty()) {
            throw new RuntimeException("No product in this page!");
        }
        waitForPageStable();
        scrollToTop(featuresItemText);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", addToCartBtn);

//        hoverElement(addToCartBtn);
//        waitToBeClickable(addToCartBtn);
//        addToCartBtn.click();

    }


    public CartPage clickViewCartBtn() throws InterruptedException {
        driver.findElement(By.cssSelector("div.modal-dialog.modal-confirm")).isDisplayed();
        Thread.sleep(2000);
        hoverElement(viewCartBtn);
        viewCartBtn.click();
        return new CartPage(driver);
    }


}