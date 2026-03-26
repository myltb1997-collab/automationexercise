package myle.testcases;

import myle.pages.CartPage;
import myle.pages.CheckoutPage;
import myle.pages.HomePage;
import myle.pages.PaymentPage;
import myle.pages.SignUpPage;
import myle.utilities.AccountUtils;
import myle.utilities.Generator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    HomePage homePage;
    SignUpPage signUpPage;
    CartPage cartPage;
    CheckoutPage checkoutPage;
    PaymentPage paymentPage;

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

    @Test(priority = 3) //Test Case 15: Place Order: Register before Checkout
    public void placeOrderRegisterBeforeCheckout() throws InterruptedException {
        // 1. Launch browser - done by BaseTest setUp()
        // 2. Navigate to url - done by BaseTest setUp()
        
        // 3. Verify that home page is visible successfully
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        
        // 4. Click 'Signup / Login' button
        signUpPage = homePage.clickSignupLoginBtn();
        
        // 5. Fill all details in Signup and create account
        String email = Generator.randomEmail();
        String password = "12345678";
        String username = "TC15";
        
        Assert.assertTrue(signUpPage.isNewSignUpVisible(), "Verify 'New User Signup!' is visible");
        signUpPage
                .enterSignUpName(username)
                .enterSignUpEmail(email)
                .clickToSubmitSignUpBtn();
        
        Assert.assertTrue(signUpPage.isAccInforVisible(), "Verify that 'ENTER ACCOUNT INFORMATION' is visible");
        
        signUpPage
                .inputFormAcc(password, "23", "12", "2015")
                .inputFormAddress("John", "Doe", "54 A Main Street", "Singapore", "Singapore State", "Singapore City", "987654", "03975423354");
        
        // 6. Verify 'ACCOUNT CREATED!' and click 'Continue' button
        Assert.assertTrue(signUpPage.isCreateAccountVisible(), "Verify account created URL");
        signUpPage.clickContinueCreateBtn();
        
        // 7. Verify ' Logged in as username' at top
        Assert.assertTrue(signUpPage.isLoggedAsTextDisplay(), "Verify logged in as username is displayed");
        
        // 8. Add products to cart
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.clickAddToCartBtn();
        
        // 9. Click 'Cart' button
        cartPage = homePage.clickViewCartBtn();
        
        // 10. Verify that cart page is displayed
        Assert.assertTrue(driver.getCurrentUrl().contains("/view_cart"), "Verify cart page is displayed");
        
        // 11. Click Proceed To Checkout
        checkoutPage = cartPage.clickProceedToCheckOutBtnAsLoggedIn();
        
        // 12. Verify Address Details and Review Your Order
        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Verify Address Details is visible");
        Assert.assertTrue(checkoutPage.isReviewOrderVisible(), "Verify Review Your Order is visible");
        
        // 13. Enter description in comment text area and click 'Place Order'
        checkoutPage.enterComment("This is a test order for automation exercise");
        paymentPage = checkoutPage.clickPlaceOrderBtn();
        
        // 14. Enter payment details: Name on Card, Card Number, CVC, Expiration date
        paymentPage.enterNameOnCard("John Doe");
        paymentPage.enterCardNumber("4111111111111111");
        paymentPage.enterCVC("123");
        paymentPage.enterExpiryMonth("12");
        paymentPage.enterExpiryYear("2025");
        
        // 15. Click 'Pay and Confirm Order' button
        paymentPage.clickPayAndConfirmOrderBtn();
        
        // 16. Verify success message 'Your order has been placed successfully!'
        Assert.assertTrue(paymentPage.isSuccessMessageDisplayed(), "Verify success message is displayed");
        Assert.assertTrue(paymentPage.getSuccessMessage().contains("Congratulations! Your order has been confirmed!"), 
                "Verify success message text");
        
        // 17. Click 'Delete Account' button
        signUpPage.clickDeleteAccountBtn();
        
        // 18. Verify 'ACCOUNT DELETED!' and click 'Continue' button
        Assert.assertTrue(signUpPage.isAccountDeletedVisible(), "Verify account deleted page");
        signUpPage.clickContinueDeleteBtn();
    }

}
