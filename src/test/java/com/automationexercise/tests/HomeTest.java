package com.automationexercise.tests;

import io.qameta.allure.Step;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.PaymentPage;
import com.automationexercise.pages.SignUpPage;
import com.automationexercise.utils.AccountUtils;
import com.automationexercise.utils.Generator;
import com.automationexercise.utils.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    HomePage homePage;
    SignUpPage signUpPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;

    @Test(priority = 1)
    @Step("User subscribes to newsletter")
    public void verifySubscription() {
        String email = Generator.randomEmail();

        homePage = new HomePage(driver);

        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.scrollToBottom();
        Assert.assertTrue(homePage.getSubscriptionText().contains(TestDataReader.getString("messages.subscriptionTitle")));
        homePage.setSubscribeEmail(email);
        homePage.clickSubscribleBtn();
        Assert.assertTrue(homePage.isSubscribeSuccess().contains(TestDataReader.getString("messages.subscriptionSuccess")));
    }

    @Test(priority = 2)
    @Step("User registers account while proceeding to checkout")
    public void registerWhileCheckout() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.clickAddToCartBtn();
        cartPage = homePage.clickViewCartBtn();
        Assert.assertTrue(driver.getCurrentUrl().contains("/view_cart"));
        cartPage.clickProceedToCheckOutBtn();
        signUpPage = cartPage.clickRegisterBtn();

        String email = Generator.randomEmail();
        String password = TestDataReader.getString("accounts.tc1.password");
        String username = TestDataReader.getString("accounts.tc1.name");

        signUpPage = AccountUtils.createAccount(driver, username, email, password);
    }

    @Test(priority = 3)
    @Step("User registers account before checkout and places order")
    public void placeOrderRegisterBeforeCheckout() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");

        signUpPage = homePage.openLoginPage();

        String email = Generator.randomEmail();
        String password = TestDataReader.getString("accounts.registerCheckout.password");
        String username = TestDataReader.getString("accounts.registerCheckout.name");

        Assert.assertTrue(signUpPage.isNewSignUpVisible(), "Verify 'New User Signup!' is visible");
        signUpPage
                .enterSignUpName(username)
                .enterSignUpEmail(email)
                .clickToSubmitSignUpBtn();

        Assert.assertTrue(signUpPage.isAccInforVisible(), "Verify 'ENTER ACCOUNT INFORMATION' is visible");

        signUpPage
                .inputFormAcc(
                        password,
                        TestDataReader.getString("accounts.registerCheckout.day"),
                        TestDataReader.getString("accounts.registerCheckout.month"),
                        TestDataReader.getString("accounts.registerCheckout.year"))
                .inputFormAddress(
                        TestDataReader.getString("accounts.registerCheckout.firstName"),
                        TestDataReader.getString("accounts.registerCheckout.lastName"),
                        TestDataReader.getString("accounts.registerCheckout.address"),
                        TestDataReader.getString("accounts.registerCheckout.country"),
                        TestDataReader.getString("accounts.registerCheckout.state"),
                        TestDataReader.getString("accounts.registerCheckout.city"),
                        TestDataReader.getString("accounts.registerCheckout.zipcode"),
                        TestDataReader.getString("accounts.registerCheckout.mobile"));

        Assert.assertTrue(signUpPage.isCreateAccountVisible(), "Verify account created URL");
        signUpPage.clickContinueCreateBtn();

        Assert.assertTrue(signUpPage.isLoggedAsTextDisplay(), "Verify logged in as username is displayed");

        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.clickAddToCartBtn();

        cartPage = homePage.clickViewCartBtn();
        Assert.assertTrue(driver.getCurrentUrl().contains("/view_cart"), "Verify cart page is displayed");

        checkoutPage = cartPage.clickProceedToCheckOutBtnAsLoggedIn();

        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Verify Address Details is visible");
        Assert.assertTrue(checkoutPage.isReviewOrderVisible(), "Verify Review Your Order is visible");

        checkoutPage.enterComment(TestDataReader.getString("home.comment"));
        paymentPage = checkoutPage.clickPlaceOrderBtn();

        paymentPage.enterNameOnCard(TestDataReader.getString("home.payment.nameOnCard"));
        paymentPage.enterCardNumber(TestDataReader.getString("home.payment.cardNumber"));
        paymentPage.enterCVC(TestDataReader.getString("home.payment.cvc"));
        paymentPage.enterExpiryMonth(TestDataReader.getString("home.payment.expiryMonth"));
        paymentPage.enterExpiryYear(TestDataReader.getString("home.payment.expiryYear"));

        paymentPage.clickPayAndConfirmOrderBtn();

        Assert.assertTrue(paymentPage.isSuccessMessageDisplayed(), "Verify success message is displayed");
        Assert.assertTrue(paymentPage.getSuccessMessage().contains(TestDataReader.getString("messages.orderConfirmed")),
                "Verify success message text");

        signUpPage.clickDeleteAccountBtn();
        Assert.assertTrue(signUpPage.isAccountDeletedVisible(), "Verify account deleted page");
        signUpPage.clickContinueDeleteBtn();
    }
}

