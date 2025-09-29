package myle.testcases;

import myle.pages.HomePage;
import myle.pages.SignUpPage;
import myle.utilities.AccountUtils;
import myle.utilities.Generator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class SignUpTest extends BaseTest {
    SignUpPage signUpPage;
    HomePage homePage;

    @Test(priority = 1)
    public void testRegisterUser() {
        //  signUpPage = new SignUpPage(driver);
        homePage = new HomePage(driver);
        signUpPage = homePage.movToLoginPage();
        String email = Generator.randomEmail();
        String password = "12345678";

        AccountUtils.createAccount(driver, "TC1", email, password);

        signUpPage.clickDeleteAccountBtn();
        Assert.assertTrue(signUpPage.isAccountDeletedVisible());
        signUpPage.clickContinueDeleteBtn();
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
    }

    @Test(priority = 2)
    public void testLoginThenDelete() {
        homePage = new HomePage(driver);
        signUpPage = homePage.movToLoginPage();
        String email = Generator.randomEmail();
        String password = "123456";

        AccountUtils.createAccount(driver, "TC2", email, password);

        homePage.clickLogOutBtn();

        signUpPage.enterLoginInfo(email, password);
        signUpPage.clickLoginBtn();

        Assert.assertEquals(signUpPage.getLoggedInUsername(), "TC2");

        signUpPage.clickDeleteAccountBtn();
        Assert.assertTrue(signUpPage.isAccountDeletedVisible());
        signUpPage.clickContinueDeleteBtn();
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");

    }

    @Test(priority = 3)
    public void testLoginFail() {
        //signUpPage = new SignUpPage(driver);
        homePage = new HomePage(driver);
        signUpPage = homePage.movToLoginPage();
        String email = "myle03@gmail.com";
        String password = "12345678Aa";

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.movToLoginPage();
        signUpPage.enterLoginInfo(email, password);
        signUpPage.clickLoginBtn();
        Assert.assertTrue(signUpPage.loginFailMgs());

    }

    @Test(priority = 4)
    public void testLogOutUser() {
        homePage = new HomePage(driver);
        signUpPage = homePage.movToLoginPage();

        String email = "myle01@gmail.com";
        String password = "123456";

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.movToLoginPage();
        signUpPage.enterLoginInfo(email, password);
        signUpPage.clickLoginBtn();
        Assert.assertTrue(signUpPage.isLoggedAsTextDisplay());

        homePage.clickLogOutBtn();
        Assert.assertTrue(signUpPage.isNewSignUpVisible());
    }

    @Test(priority = 5)
    public void testRegisterUserWithExistingEmail() {
        homePage = new HomePage(driver);
        signUpPage = homePage.movToLoginPage();

        String name = "TC5";
        String email = "myle01@gmail.com";

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        signUpPage = homePage.movToLoginPage();
        Assert.assertTrue(signUpPage.isNewSignUpVisible());
        signUpPage.enterSignUpName(name).
                enterSignUpEmail(email);
        signUpPage.clickToSubmitSignUpBtn();
        Assert.assertTrue(signUpPage.isExistingEmailMgs());
    }

}
