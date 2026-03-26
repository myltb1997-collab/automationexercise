package myle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class CheckoutPage extends BasePage {

    @FindBy(xpath = "//h2[text()='Address Details']")
    WebElement addressDetailsHeader;
    @FindBy(xpath = "//h2[text()='Review Your Order']")
    WebElement reviewOrderHeader;
    @FindBy(css = "textarea[name='message']")
    WebElement commentTextArea;
    @FindBy(css = "a.btn.btn-default.check_out")
    WebElement placeOrderBtn;

    public CheckoutPage(WebDriver driver) {
        super(driver);
    }

    public boolean isAddressDetailsVisible() {
        return addressDetailsHeader.isDisplayed();
    }

    public boolean isReviewOrderVisible() {
        return reviewOrderHeader.isDisplayed();
    }

    public void enterComment(String comment) {
        commentTextArea.isDisplayed();
        commentTextArea.clear();
        commentTextArea.sendKeys(comment);
    }

    public PaymentPage clickPlaceOrderBtn() {
        waitToBeClickable(placeOrderBtn);
        placeOrderBtn.click();
        return new PaymentPage(driver);
    }
}


