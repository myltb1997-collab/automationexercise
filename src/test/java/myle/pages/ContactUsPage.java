package myle.pages;

import org.openqa.selenium.Alert;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class ContactUsPage {
    WebDriver driver;

    @FindBy(css = "input[placeholder='Name']")
    WebElement inputName;
    @FindBy(css = "input[placeholder='Email']")
    WebElement inputEmail;
    @FindBy(css = "input[placeholder='Subject']")
    WebElement inputSubject;
    @FindBy(css = "#message")
    WebElement inputMessage;
    @FindBy(css = "input[name='upload_file']")
    WebElement uploadfileBtn;
    @FindBy(css = "input[value='Submit']")
    WebElement submitBtn;
    @FindBy(xpath = "//h2[normalize-space()='Get In Touch']")
    WebElement getInTouchText;
    @FindBy(xpath = "//div[@class='status alert alert-success']")
    WebElement alertMsgSuccess;
    @FindBy(css = "a[class$='btn btn-success']")
    WebElement homeBtn;
    @FindBy(xpath = "//b[contains(.,'Test Cases')]")
    WebElement testcaseText;

    public ContactUsPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isGetInTouchVisible() {
        return getInTouchText.isDisplayed();
    }

    public void enterName(String name) {
        inputName.isDisplayed();
        inputName.clear();
        inputName.sendKeys(name);
    }

    public void enterEmail(String email) {
        inputEmail.isDisplayed();
        inputEmail.clear();
        inputEmail.sendKeys(email);
    }

    public void enterSubject(String value) {
        inputSubject.isDisplayed();
        inputSubject.clear();
        inputSubject.sendKeys(value);
    }

    public void enterMessage(String message) {
        inputMessage.isDisplayed();
        inputMessage.clear();
        inputMessage.sendKeys(message);
    }

    public void uploadFile() {
        uploadfileBtn.sendKeys("C:\\Users\\DELL\\OneDrive\\Máy tính\\testAuto\\check.txt");
    }

    public void clickSubmitBtn() {
        submitBtn.isDisplayed();
        submitBtn.click();
    }

    public void clickOkBtn() {
        Alert alert = driver.switchTo().alert();
        alert.accept();
    }

    public boolean isSendFeedBackSuccess() {
        return alertMsgSuccess.isEnabled();
    }

    public void clickToHomeBtn() {
        homeBtn.isDisplayed();
        homeBtn.click();
    }

    public boolean isTestCasePageVisible(){
       return testcaseText.isDisplayed();
    }

}
