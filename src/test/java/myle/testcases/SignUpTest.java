package myle.testcases;

import io.qameta.allure.Step;
import myle.pages.HomePage;
import myle.pages.SignUpPage;
import myle.utilities.AccountUtils;
import myle.utilities.Generator;
import myle.utilities.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTest extends BaseTest {
    SignUpPage signUpPage;
    HomePage homePage;

    @Test(priority = 1)
    @Step("Register new user account and then delete it")
    public void testRegisterUser() {
        homePage = new HomePage(driver);
        signUpPage = homePage.openLoginPage();

        String email = Generator.randomEmail();
        String name = TestDataReader.getString("accounts.tc1.name");
        String password = TestDataReader.getString("accounts.tc1.password");

        signUpPage = AccountUtils.createAccount(driver, name, email, password);
        signUpPage.clickDeleteAccountBtn();
        Assert.assertTrue(signUpPage.isAccountDeletedVisible());
        signUpPage.clickContinueDeleteBtn();
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
    }

    @Test(priority = 2)
    @Step("Register user, logout, login with valid credentials, then delete account")
    public void testLoginThenDelete() {
        homePage = new HomePage(driver);
        signUpPage = homePage.openLoginPage();

        String email = Generator.randomEmail();
        String name = TestDataReader.getString("accounts.tc2.name");
        String password = TestDataReader.getString("accounts.tc2.password");

        signUpPage = AccountUtils.createAccount(driver, name, email, password);
        homePage.clickLogOutBtn();
        signUpPage.enterLoginInfo(email, password);
        signUpPage.clickLoginBtn();
        Assert.assertEquals(signUpPage.getLoggedInUsername(), name);
        signUpPage.clickDeleteAccountBtn();
        Assert.assertTrue(signUpPage.isAccountDeletedVisible());
        signUpPage.clickContinueDeleteBtn();
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
    }

    @Test(priority = 3)
    @Step("Attempt login with invalid credentials")
    public void testLoginFail() {
        homePage = new HomePage(driver);
        signUpPage = homePage.openLoginPage();

        String email = TestDataReader.getString("accounts.loginFail.email");
        String password = TestDataReader.getString("accounts.loginFail.password");

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.openLoginPage();
        signUpPage.enterLoginInfo(email, password);
        signUpPage.clickLoginBtn();
        Assert.assertTrue(signUpPage.loginFailMgs());
    }

    @Test(priority = 4)
    @Step("Login and logout from existing account")
    public void testLogOutUser() {
        homePage = new HomePage(driver);

        String email = TestDataReader.getString("accounts.logoutUser.email");
        String password = TestDataReader.getString("accounts.logoutUser.password");
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.openLoginPage();
        signUpPage.enterLoginInfo(email, password);
        signUpPage.clickLoginBtn();
        Assert.assertTrue(signUpPage.isLoggedAsTextDisplay());
        homePage.clickLogOutBtn();
        Assert.assertTrue(signUpPage.isNewSignUpVisible());
    }

    @Test(priority = 5)
    @Step("Register with existing email address (should fail)")
    public void testRegisterUserWithExistingEmail() {
        homePage = new HomePage(driver);

        String name = TestDataReader.getString("accounts.tc5.name");
        String email = TestDataReader.getString("accounts.tc5.email");

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.openLoginPage();
        Assert.assertTrue(signUpPage.isNewSignUpVisible());
        signUpPage.enterSignUpName(name)
                .enterSignUpEmail(email);
        signUpPage.clickToSubmitSignUpBtn();
        Assert.assertTrue(signUpPage.isExistingEmailMgs());
    }
}
