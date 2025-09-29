package myle.utilities;

import myle.pages.HomePage;
import myle.pages.SignUpPage;
import org.openqa.selenium.WebDriver;
import org.testng.Assert;

public class AccountUtils {
    public static void createAccount(WebDriver driver, String name, String email, String password) {
        SignUpPage signUpPage;
        HomePage homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.movToLoginPage();
        Assert.assertTrue(signUpPage.isNewSignUpVisible(), "Verify 'New User Signup!' is visible");

        signUpPage
                .enterSignUpName(name)
                .enterSignUpEmail(email)
                .clickToSubmitSignUpBtn();
        Assert.assertTrue(signUpPage.isAccInforVisible(), "Verify that 'ENTER ACCOUNT INFORMATION' is visible");

        signUpPage
                .inputFormAcc(password, "23", "12", "2015")
                .inputFormAddress("thien", "vu", "54 A", "Singapore", "bang haha", "cua Sing", "987", "03975423354");

        Assert.assertTrue(signUpPage.isCreateAccountVisible(),"tra ve url");
        signUpPage.clickContinueCreateBtn();

    }
}
