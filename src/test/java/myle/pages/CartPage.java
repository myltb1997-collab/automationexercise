package myle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class CartPage extends BasePage {

    @FindBy(css = "tbody tr[id^='product-']")
    List<WebElement> cartItems;
    @FindBy(css = "tbody tr[id^='product-'] td[class='cart_price']")
    WebElement productPrice;
    @FindBy(css = "tbody tr[id^='product-'] td[class='cart_quantity']")
    WebElement productQuantity;
    @FindBy(css = "tbody tr[id^='product-'] td[class='cart_total']")
    WebElement productTotal;


    public CartPage(WebDriver driver) {
        super(driver);

    }

    public List<WebElement> getCartItems() {
        return cartItems;
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

}
