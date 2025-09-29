package myle.pages;

import myle.utilities.Links;
import org.openqa.selenium.By;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import java.time.Duration;
import java.util.List;
import java.util.Random;

import static myle.utilities.Links.URL_ACCOUNT_CREATED;

public class SignUpPage {

    WebDriver driver;
    @FindBy(name = "name")
    WebElement usernameSignupInput;
    @FindBy(css = "input[data-qa='signup-email']")
    WebElement emailSignupInput;
    @FindBy(css = "button[data-qa='signup-button']")
    WebElement signUpBtn;
    @FindBy(xpath = "//h2[normalize-space()='New User Signup!']")
    WebElement verifySignUpPage;
    @FindBy(xpath = "//b[normalize-space()='Enter Account Information']")
    WebElement verifyAccInfo;
    @FindBy(id = "password")
    WebElement passwordInput;
    @FindBy(id = "days")
    WebElement daysDropdw;
    @FindBy(id = "months")
    WebElement monthsDropdw;
    @FindBy(id = "years")
    WebElement yearsDropdw;
    @FindBy(name = "newsletter")
    WebElement checkBoxNewsletter;
    @FindBy(id = "optin")
    WebElement checkBoxOffer;
    @FindBy(id = "first_name")
    WebElement firstnameInput;
    @FindBy(id = "last_name")
    WebElement lastnameInput;
    @FindBy(id = "company")
    WebElement companyInput;
    @FindBy(id = "address1")
    WebElement address1Input;
    @FindBy(id = "address2")
    WebElement address2Input;
    @FindBy(id = "state")
    WebElement stateInput;
    @FindBy(id = "city")
    WebElement cityInput;
    @FindBy(id = "zipcode")
    WebElement zipcodeInput;
    @FindBy(id = "mobile_number")
    WebElement mobilenumberInput;
    @FindBy(id = "country")
    WebElement countryDropdw;
    @FindBy(xpath = "//button[.='Create Account']")
    WebElement createAccBtn;
    @FindBy(xpath = "//a[.='Continue']")
    WebElement continueCreateBtn;
    @FindBy(xpath = "//a[contains(text(), 'Logged in as')]")
    WebElement loggedInAsText;
    @FindBy(xpath = "//b")
    WebElement usernameLogged;
    @FindBy(xpath = "//a[.=' Delete Account']")
    WebElement deleteAccbtn;
    @FindBy(xpath = "//a[.='Continue']")
    WebElement continueDeleteBtn;
    @FindBy(xpath = "//input[@data-qa='login-email']")
    WebElement emailLoginInput;
    @FindBy(name = "password")
    WebElement passwordLoginInput;
    @FindBy(css = "button[data-qa='login-button']")
    WebElement loginBtn;
    @FindBy(xpath = "//p[normalize-space()='Your email or password is incorrect!']")
    WebElement loginErrorMsg;
    @FindBy(xpath = "//p[.='Email Address already exist!']")
    WebElement emailExistMsg;

    //Ham xay dung
    public SignUpPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public SignUpPage enterSignUpName(String username) {
        usernameSignupInput.clear();
        usernameSignupInput.sendKeys(username);
        return this;
    }

    public SignUpPage enterSignUpEmail(String email) {
        emailSignupInput.clear();
        emailSignupInput.sendKeys(email);
        return this;
    }

    public void enterLoginInfo(String email, String password) {
        emailLoginInput.clear();
        emailLoginInput.sendKeys(email);
        passwordLoginInput.clear();
        passwordLoginInput.sendKeys(password);
    }

    public void clickLoginBtn() {
        loginBtn.isDisplayed();
        loginBtn.click();
    }

    public void clickToSubmitSignUpBtn() {
        signUpBtn.isDisplayed();
        signUpBtn.click();

    }

    public boolean isExistingEmailMgs() {
        return emailExistMsg.isDisplayed();
    }

    /*public String isExistingEmailMgs() {
         emailExistMsg.isDisplayed();
         String t =emailExistMsg.getText();
         return t;
    }
*/
    public boolean isNewSignUpVisible() {
        return verifySignUpPage.isDisplayed();
    }

    /* public boolean isNewSignUpVisible() {
         WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
         try {
             wait.until(ExpectedConditions.visibilityOf(verifySignUpPage));
             return verifySignUpPage.isDisplayed();
         } catch (TimeoutException e) {
             return false;
         }
     }*/
    public boolean isAccInforVisible() {
        return verifyAccInfo.isDisplayed();
    }

    public int random(int x) {
        Random rand = new Random();
        int index = rand.nextInt(x);
        return index;
    }

    public void selectItem(WebElement element, String value) {
        Select select = new Select(element);
        select.selectByValue(value);
    }

    public void inputElement(WebElement element, String value) {
        element.isDisplayed();
        element.clear();
        element.sendKeys(value);
    }

    public SignUpPage inputFormAcc(String password, String day, String month, String year) {
        List<WebElement> genderRadios = driver.findElements(By.cssSelector("input[id*='id_gender']"));
        //Random rand = new Random();
        //int index = rand.nextInt(genderRadios.size());
        int index = random(genderRadios.size());
        WebElement selectedRadio = genderRadios.get(index);
        if (!selectedRadio.isSelected()) {
            selectedRadio.click();
        }
        passwordInput.clear();
        passwordInput.sendKeys(password);

        selectItem(daysDropdw, day);
        selectItem(monthsDropdw, month);
        selectItem(yearsDropdw, year);

        if (!checkBoxNewsletter.isSelected()) {
            checkBoxNewsletter.click();
        }

        if (!checkBoxOffer.isSelected()) {
            checkBoxOffer.click();
        }
        return this;
    }

    public void inputFormAddress(String firstname, String lastname, String address, String country, String state, String city, String zipcode, String mobilenumb) {
        inputElement(firstnameInput, firstname);
        inputElement(lastnameInput, lastname);
        inputElement(address1Input, address);
        selectItem(countryDropdw, country);
        inputElement(stateInput, state);
        inputElement(cityInput, city);
        inputElement(zipcodeInput, zipcode);
        inputElement(mobilenumberInput, mobilenumb);
        createAccBtn.isEnabled();
        createAccBtn.click();
    }

    public boolean isCreateAccountVisible() {
        // return driver.getCurrentUrl().equals(URL_ACCOUNT_CREATED);
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            wait.until(ExpectedConditions.urlContains(URL_ACCOUNT_CREATED));
            return true;

        } catch (TimeoutException e) {
            return false;
        }
    }

    public void clickContinueCreateBtn() {
        continueCreateBtn.isEnabled();
        continueCreateBtn.click();
    }

    public String getLoggedInUsername() {
        Assert.assertTrue(loggedInAsText.isEnabled());
        return usernameLogged.getText();

    }

    public void clickDeleteAccountBtn() {
        Assert.assertTrue(loggedInAsText.isEnabled());
        deleteAccbtn.click();
    }

    public boolean isAccountDeletedVisible() {
        Assert.assertEquals(driver.getCurrentUrl(), Links.URL_ACCOUNT_DELETE);
        return true;
    }

    public void clickContinueDeleteBtn() {
        Assert.assertTrue(continueDeleteBtn.isEnabled());
        continueDeleteBtn.click();
    }

    public boolean isLoggedAsTextDisplay() {
        Assert.assertTrue(loggedInAsText.isDisplayed());
        return true;
    }

    public boolean loginFailMgs() {
        Assert.assertTrue(loginErrorMsg.isDisplayed());
        return true;
    }
}
