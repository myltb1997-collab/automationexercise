package myle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class NavigationComponent {
    private WebDriver driver;

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

    public NavigationComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductPage moveToProductPage() {
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

    public SignUpPage moveToLoginPage() {
        navsignIn.isEnabled();
        navsignIn.click();
        return new SignUpPage(driver);
    }

    public ContactUsPage moveToContactPage() {
        Assert.assertTrue(navContactUs.isDisplayed());
        navContactUs.click();
        return new ContactUsPage(driver);
    }
}

