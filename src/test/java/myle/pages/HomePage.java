package myle.pages;

import myle.utilities.WaitUtil;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class HomePage extends BasePage {
    private final NavigationComponent navigation;

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
        this.navigation = new NavigationComponent(driver);
    }

    public SignUpPage openLoginPage() {
        return navigation.openLoginPage();
    }

    public ProductPage openProductsPage() {
        return navigation.openProductsPage();
    }

    public CartPage openCartPage() {
        return navigation.openCartPage();
    }

    public ContactUsPage openContactUsPage() {
        return navigation.openContactUsPage();
    }

    public TestCasesPage openTestCasesPage() {
        return navigation.openTestCasesPage();
    }

    public boolean isHomePageVisible() {
        try {
            WaitUtil.waitForPageReady(driver);
            return WaitUtil.waitForVisible(driver, sliderLocator).isDisplayed();
        } catch (Exception e) {
            return false;
        }
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
        WebElement successMgs = WaitUtil.waitForVisible(driver, successSubscribeMsg);
        String actualMsg = successMgs.getText();
        WaitUtil.waitForInvisible(driver, successSubscribeMsg);
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

    public void clickAddToCartBtn() {
        if (productList.isEmpty()) {
            throw new RuntimeException("No product in this page!");
        }
        WaitUtil.waitForPageReady(driver);
        scrollToTop(featuresItemText);

        ((JavascriptExecutor) driver)
                .executeScript("arguments[0].click();", addToCartBtn);

    }


    public CartPage clickViewCartBtn() {
        // ✓ RULE: Sử dụng waitForElementVisible() từ BasePage thay vì tạo WebDriverWait riêng
        WaitUtil.waitForElementVisible(driver, By.cssSelector("div.modal-dialog.modal-confirm"));
        hoverElement(viewCartBtn);
        viewCartBtn.click();
        return new CartPage(driver);
    }

    @Deprecated
    public SignUpPage clickSignupLoginBtn(){
        return openLoginPage();
    }

}
