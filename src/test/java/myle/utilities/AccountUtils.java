package myle.utilities;

import myle.pages.HomePage;
import myle.pages.SignUpPage;
import org.openqa.selenium.WebDriver;

public class AccountUtils {
    // Backward compatible: original method signature with default test data.
    public static SignUpPage createAccount(WebDriver driver, String name, String email, String password) {
        // Default test data for address and DOB
        return createAccount(driver, name, email, password,
                "23", "12", "2015", // day, month, year
                "thien", "vu", "54 A", "Singapore", // firstName, lastName, address, country
                "bang haha", "cua Sing", "987", "03975423354" // state, city, zip, mobile
        );
    }

    // Creates an account by filling the sign-up form and address form.
    // All data must be provided by the caller. No assertions are performed.
    // Returns the SignUpPage after clicking Continue.
    public static SignUpPage createAccount(WebDriver driver, String name, String email, String password,
                                           String day, String month, String year,
                                           String firstName, String lastName, String address, String country,
                                           String state, String city, String zip, String mobile) {
        HomePage homePage = new HomePage(driver);
        SignUpPage signUpPage = homePage.navigation.moveToLoginPage();
        signUpPage.enterSignUpName(name)
                .enterSignUpEmail(email)
                .clickToSubmitSignUpBtn();
        signUpPage.inputFormAcc(password, day, month, year)
                .inputFormAddress(firstName, lastName, address, country, state, city, zip, mobile);
        signUpPage.clickContinueCreateBtn();
        return signUpPage;
    }
}