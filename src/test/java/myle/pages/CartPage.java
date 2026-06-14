package myle.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

import static myle.utilities.Links.DOMAIN;

public class CartPage extends BasePage {
    private static final By CART_ITEM_ROWS = By.cssSelector("tbody tr[id^='product-']");
    private static final By EMPTY_CART_MESSAGE = By.cssSelector("#empty_cart");
    private static final By CART_INFO = By.cssSelector("#cart_info");

    @FindBy(css = "tbody tr[id^='product-']")
    List<WebElement> cartItems;
    @FindBy(css = "tbody tr[id^='product-'] td[class='cart_price']")
    WebElement productPrice;
    @FindBy(css = "tbody tr[id^='product-'] td[class='cart_quantity']")
    WebElement productQuantity;
    @FindBy(css = "tbody tr[id^='product-'] td[class='cart_total']")
    WebElement productTotal;
    @FindBy(css = "a.btn.btn-default.check_out")
    WebElement checkOutBtn;
    @FindBy(xpath = "//u[normalize-space()='Register / Login']")
    WebElement registerBtn;
    @FindBy(css = "#empty_cart")
    WebElement emptyCartMessage;


    public CartPage(WebDriver driver) {
        super(driver);

    }

    public boolean isCartPageDisplayed() {
        try {
            waitForUrlContains("/view_cart");
            return waitForElementVisible(CART_INFO).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public List<WebElement> getCartItems() {
        return cartItems;
    }

    public int getCartItemCount() {
        return driver.findElements(CART_ITEM_ROWS).size();
    }

    public String getProductIdByIndex(int index) {
        List<WebElement> items = driver.findElements(CART_ITEM_ROWS);
        if (items.isEmpty()) {
            throw new RuntimeException("No product in cart!");
        }
        if (index < 0 || index >= items.size()) {
            throw new RuntimeException("Product index is out of cart item range!");
        }
        return items.get(index).getAttribute("id").replace("product-", "");
    }

    public void removeProductById(String productId) {
        By productRow = By.cssSelector("#product-" + productId);
        By deleteButton = By.cssSelector("a.cart_quantity_delete[data-product-id='" + productId + "']");
        waitForElementVisible(productRow);
        WebElement button = waitForElementVisible(deleteButton);
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block: 'center'});" +
                        "arguments[0].dispatchEvent(new MouseEvent('click', {bubbles: true, cancelable: true, view: window}));",
                button
        );
        try {
            waitForElementInvisible(productRow);
        } catch (TimeoutException e) {
            driver.navigate().to(DOMAIN + "/delete_cart/" + productId);
            driver.navigate().to(DOMAIN + "/view_cart");
            waitForElementInvisible(productRow);
        }
    }

    public boolean isProductRemoved(String productId) {
        return driver.findElements(By.cssSelector("#product-" + productId)).isEmpty();
    }

    public boolean isEmptyCartMessageVisible() {
        try {
            return waitForElementVisible(EMPTY_CART_MESSAGE).isDisplayed()
                    && emptyCartMessage.getText().contains("Cart is empty!");
        } catch (TimeoutException e) {
            return false;
        }
    }

    public double getPriceProduct(WebElement productRow) {
        return Double.parseDouble(normalizeText(productPrice,"Rs."));
    }
    public int getQuantityProduct(WebElement productRow) {
        return Integer.parseInt(normalizeText(productQuantity,""));
    }
    public double getTotalProduct(WebElement productRow) {
        return Double.parseDouble(normalizeText(productTotal,"Rs."));
    }

    public int getProductQuantity() {
        return Integer.parseInt(productQuantity.getText().trim());
    }

    public void clickProceedToCheckOutBtn(){
        checkOutBtn.isDisplayed();
        checkOutBtn.click();
    }

    public CheckoutPage clickProceedToCheckOutBtnAsLoggedIn(){
        checkOutBtn.isDisplayed();
        checkOutBtn.click();
        return new CheckoutPage(driver);
    }

    public SignUpPage clickRegisterBtn(){
        registerBtn.isDisplayed();
        registerBtn.click();
        return new SignUpPage(driver);
    }

}
