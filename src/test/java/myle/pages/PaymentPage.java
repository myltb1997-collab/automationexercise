package myle.pages;

import myle.utilities.WaitUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class PaymentPage extends BasePage {

    @FindBy(css = "input[data-qa='name-on-card']")
    WebElement nameOnCardInput;
    @FindBy(css = "input[data-qa='card-number']")
    WebElement cardNumberInput;
    @FindBy(css = "input[data-qa='cvc']")
    WebElement cvcInput;
    @FindBy(css = "input[data-qa='expiry-month']")
    WebElement expiryMonthInput;
    @FindBy(css = "input[data-qa='expiry-year']")
    WebElement expiryYearInput;
    @FindBy(css = "button[data-qa='pay-button']")
    WebElement payConfirmOrderBtn;
    @FindBy(xpath = "//p[contains(text(),'Congratulations! Your order has been confirmed!')]")
    WebElement successMessage;

    public PaymentPage(WebDriver driver) {
        super(driver);
    }

    public void enterNameOnCard(String name) {
        nameOnCardInput.isDisplayed();
        nameOnCardInput.clear();
        nameOnCardInput.sendKeys(name);
    }

    public void enterCardNumber(String cardNumber) {
        cardNumberInput.isDisplayed();
        cardNumberInput.clear();
        cardNumberInput.sendKeys(cardNumber);
    }

    public void enterCVC(String cvc) {
        cvcInput.isDisplayed();
        cvcInput.clear();
        cvcInput.sendKeys(cvc);
    }

    public void enterExpiryMonth(String month) {
        expiryMonthInput.isDisplayed();
        expiryMonthInput.clear();
        expiryMonthInput.sendKeys(month);
    }

    public void enterExpiryYear(String year) {
        expiryYearInput.isDisplayed();
        expiryYearInput.clear();
        expiryYearInput.sendKeys(year);
    }

    public void clickPayAndConfirmOrderBtn() {
        WaitUtil.waitForClickable(driver, payConfirmOrderBtn);
        payConfirmOrderBtn.click();
        WaitUtil.waitForPageReady(driver);
    }

    public boolean isSuccessMessageDisplayed() {
        try {
            WaitUtil.waitForVisible(driver, successMessage);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    public String getSuccessMessage() {
        return successMessage.getText();
    }
}
