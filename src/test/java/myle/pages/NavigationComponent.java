package myle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.testng.Assert;

public class NavigationComponent {
    private final WebDriver driver;

    @FindBy(css = "a[href='/products']")
    WebElement productsLink;
    @FindBy(css = "a[href='/view_cart']")
    WebElement cartLink;
    @FindBy(css = "a[href='/login']")
    WebElement loginLink;
    @FindBy(css = "a[href='/test_cases']")
    WebElement testCasesLink;
    @FindBy(css = "a[href='/contact_us']")
    WebElement contactUsLink;

    public NavigationComponent(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public ProductPage openProductsPage() {
        productsLink.isDisplayed();
        productsLink.click();
        return new ProductPage(driver);
    }

    public CartPage openCartPage() {
        cartLink.click();
        return new CartPage(driver);
    }

    public SignUpPage openLoginPage() {
        loginLink.isEnabled();
        loginLink.click();
        return new SignUpPage(driver);
    }

    public ContactUsPage openContactUsPage() {
        Assert.assertTrue(contactUsLink.isDisplayed());
        contactUsLink.click();
        return new ContactUsPage(driver);
    }

    public TestCasesPage openTestCasesPage() {
        testCasesLink.isDisplayed();
        testCasesLink.click();
        return new TestCasesPage(driver);
    }

    @Deprecated
    public ProductPage moveToProductPage() {
        return openProductsPage();
    }

    @Deprecated
    public CartPage moveToCartPage() {
        return openCartPage();
    }

    @Deprecated
    public SignUpPage moveToLoginPage() {
        return openLoginPage();
    }

    @Deprecated
    public ContactUsPage moveToContactPage() {
        return openContactUsPage();
    }
}

