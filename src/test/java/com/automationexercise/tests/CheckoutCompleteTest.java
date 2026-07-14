package com.automationexercise.tests;

import io.qameta.allure.Step;
import com.automationexercise.pages.CartPage;
import com.automationexercise.pages.CheckoutPage;
import com.automationexercise.pages.HomePage;
import com.automationexercise.pages.PaymentPage;
import com.automationexercise.pages.SignUpPage;
import com.automationexercise.utils.Generator;
import com.automationexercise.utils.TestDataReader;
import com.automationexercise.utils.WaitUtil;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CheckoutCompleteTest extends BaseTest {

    @Test(description = "TC_16: Complete Checkout Flow - Login, Add to Cart, Checkout, Payment, Delete Account")
    @Step("Execute complete checkout flow: register, add product, checkout and place order")
    public void testCompleteCheckoutFlow() {
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");
        System.out.println("✓ Step 1-3: Home page loaded successfully");

        SignUpPage signUpPage = homePage.openLoginPage();
        Assert.assertTrue(signUpPage.isNewSignUpVisible(), "New User Signup page should be visible");
        System.out.println("✓ Step 4: Signup / Login page opened");

        String email = Generator.randomEmail();
        String password = TestDataReader.getString("accounts.checkoutComplete.password");
        String firstName = TestDataReader.getString("accounts.checkoutComplete.firstName");
        String lastName = TestDataReader.getString("accounts.checkoutComplete.lastName");
        String day = TestDataReader.getString("accounts.checkoutComplete.day");
        String month = TestDataReader.getString("accounts.checkoutComplete.month");
        String year = TestDataReader.getString("accounts.checkoutComplete.year");
        String address = TestDataReader.getString("accounts.checkoutComplete.address");
        String country = TestDataReader.getString("accounts.checkoutComplete.country");
        String state = TestDataReader.getString("accounts.checkoutComplete.state");
        String city = TestDataReader.getString("accounts.checkoutComplete.city");
        String zipcode = TestDataReader.getString("accounts.checkoutComplete.zipcode");
        String mobile = TestDataReader.getString("accounts.checkoutComplete.mobile");
        String username = TestDataReader.getString("accounts.checkoutComplete.name");

        signUpPage.enterSignUpName(firstName)
                .enterSignUpEmail(email);
        signUpPage.clickToSubmitSignUpBtn();
        System.out.println("✓ Step 5a: Entered signup email: " + email);

        Assert.assertTrue(signUpPage.isAccInforVisible(), "Account Information page should be visible");
        signUpPage.inputFormAcc(password, day, month, year);
        System.out.println("✓ Step 5b: Filled account information (password, DOB, checkboxes)");

        signUpPage.inputFormAddress(firstName, lastName, address, country, state, city, zipcode, mobile);
        System.out.println("✓ Step 5c: Filled address information");

        Assert.assertTrue(signUpPage.isCreateAccountVisible(), "Account should be created successfully");
        System.out.println("✓ Step 5d: Account created successfully");

        signUpPage.clickContinueCreateBtn();
        System.out.println("✓ Step 5e: Clicked Continue button");

        WaitUtil.waitForPageReady(driver);
        WaitUtil.waitForElementPresent(driver, By.xpath("//li//b[contains(text(),'" + username + "')]"));
        String loggedInUsername = signUpPage.getLoggedInUsername();
        Assert.assertNotNull(loggedInUsername, "Logged in username should be displayed");
        System.out.println("✓ Step 6: Logged in as: " + loggedInUsername);

        homePage = new HomePage(driver);
        homePage.clickAddToCartBtn();
        System.out.println("✓ Step 7: Product added to cart");

        CartPage cartPage = homePage.clickViewCartBtn();
        Assert.assertFalse(cartPage.getCartItems().isEmpty(), "Cart should have items");
        System.out.println("✓ Step 8-9: Cart page displayed with product(s)");

        CheckoutPage checkoutPage = cartPage.clickProceedToCheckOutBtnAsLoggedIn();
        System.out.println("✓ Step 10: Clicked Proceed To Checkout");

        WaitUtil.waitForElementVisible(driver, By.xpath("//h2[contains(text(),'Address Details')]"));
        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Address Details section should be visible");
        Assert.assertTrue(checkoutPage.isReviewOrderVisible(), "Review Your Order section should be visible");
        System.out.println("✓ Step 11: Address Details and Review Order verified");

        String comment = Generator.randomMessage(TestDataReader.getInt("accounts.checkoutComplete.commentLength"));
        checkoutPage.enterComment(comment);
        System.out.println("✓ Step 12a: Comment entered: " + comment);

        PaymentPage paymentPage = checkoutPage.clickPlaceOrderBtn();
        System.out.println("✓ Step 12b: Clicked Place Order button");

        paymentPage.enterNameOnCard(TestDataReader.getString("accounts.checkoutComplete.payment.nameOnCard"));
        paymentPage.enterCardNumber(TestDataReader.getString("accounts.checkoutComplete.payment.cardNumber"));
        paymentPage.enterCVC(TestDataReader.getString("accounts.checkoutComplete.payment.cvc"));
        paymentPage.enterExpiryMonth(TestDataReader.getString("accounts.checkoutComplete.payment.expiryMonth"));
        paymentPage.enterExpiryYear(TestDataReader.getString("accounts.checkoutComplete.payment.expiryYear"));
        System.out.println("✓ Step 13: Payment details entered");

        paymentPage.clickPayAndConfirmOrderBtn();
        System.out.println("✓ Step 14: Clicked Pay and Confirm Order button");

        WaitUtil.waitForElementVisible(driver, By.xpath("//p[normalize-space()='" + TestDataReader.getString("messages.orderConfirmed") + "']"));
        Assert.assertTrue(paymentPage.isSuccessMessageDisplayed(), "Success message should be displayed");
        String successMsg = paymentPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains(TestDataReader.getString("messages.orderConfirmedContains")),
                "Success message should contain confirmation text. Actual: " + successMsg);
        System.out.println("✓ Step 15: Order placed successfully! Message: " + successMsg);

        signUpPage = new SignUpPage(driver);
        signUpPage.clickDeleteAccountBtn();
        System.out.println("✓ Step 16: Clicked Delete Account button");

        Assert.assertTrue(signUpPage.isAccountDeletedVisible(), "Account should be deleted");
        signUpPage.clickContinueDeleteBtn();
        System.out.println("✓ Step 17: Account deleted successfully");

        System.out.println("\n✓✓✓ TC_16 PASSED - Complete Checkout Flow Successful ✓✓✓");
    }
}

