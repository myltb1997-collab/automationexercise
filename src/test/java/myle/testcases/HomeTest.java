package myle.testcases;

import myle.pages.CartPage;
import myle.pages.HomePage;
import myle.pages.SignUpPage;
import myle.utilities.AccountUtils;
import myle.utilities.Generator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    HomePage homePage;
    SignUpPage signUpPage;
    CartPage cartPage;

    @Test(priority = 1) //Test Case 10: Verify Subscription in home page
    public void verifySubscription() {
        String email = Generator.randomEmail();

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.scrollToBottom();
        Assert.assertTrue(homePage.getSubscriptionText().contains("SUBSCRIPTION"));
        homePage.setSubscribeEmail(email);
        homePage.clickSubscribleBtn();
        Assert.assertTrue(homePage.isSubscribeSuccess().contains("You have been successfully subscribed!"));
    }

    @Test(priority = 2) //Test Case 14: Place Order: Register while Checkout
    public void registerWhileCheckout() throws InterruptedException {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.clickAddToCartBtn();
        cartPage = homePage.clickViewCartBtn();
        Assert.assertTrue(driver.getCurrentUrl().contains("/view_cart"));
        cartPage.clickProceedToCheckOutBtn();
        signUpPage = cartPage.clickRegisterBtn();
        String email = Generator.randomEmail();
        String password = "12345678";

        AccountUtils.createAccount(driver, "TC1", email, password);

    }


}
