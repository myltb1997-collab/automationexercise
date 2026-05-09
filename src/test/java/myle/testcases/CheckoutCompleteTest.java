package myle.testcases;

import myle.pages.*;
import myle.utilities.Generator;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

/**
 * TC_16: Complete Checkout Flow
 * - Signup with email/password
 * - Add product to cart
 * - Proceed to checkout
 * - Enter comment & place order
 * - Enter payment details & pay
 * - Verify order success
 * - Delete account
 */
public class CheckoutCompleteTest extends BaseTest {

    @Test(description = "TC_16: Complete Checkout Flow - Login, Add to Cart, Checkout, Payment, Delete Account")
    public void testCompleteCheckoutFlow() {
        // Step 1: Navigate to home page & verify
        HomePage homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Home page should be visible");
        System.out.println("✓ Step 1-3: Home page loaded successfully");

        // Step 4: Click Signup / Login button
        SignUpPage signUpPage = homePage.clickSignupLoginBtn();
        Assert.assertTrue(signUpPage.isNewSignUpVisible(), "New User Signup page should be visible");
        System.out.println("✓ Step 4: Signup / Login page opened");

        // Step 5: Signup with new email & password
        String email = Generator.randomEmail();
        String password = "Test@123456";
        String firstName = "TestUser";
        String lastName = "Automation";
        String day = "15";
        String month = "6";
        String year = "1990";
        String address = "123 Test Street";
        String country = "United States";
        String state = "TestState";
        String city = "TestCity";
        String zipcode = "12345";
        String mobile = "1234567890";

        signUpPage.enterSignUpName(firstName)
                .enterSignUpEmail(email);
        signUpPage.clickToSubmitSignUpBtn();
        System.out.println("✓ Step 5a: Entered signup email: " + email);

        // Fill account information
        Assert.assertTrue(signUpPage.isAccInforVisible(), "Account Information page should be visible");
        signUpPage.inputFormAcc(password, day, month, year);
        System.out.println("✓ Step 5b: Filled account information (password, DOB, checkboxes)");

        // Fill address information
        signUpPage.inputFormAddress(firstName, lastName, address, country, state, city, zipcode, mobile);
        System.out.println("✓ Step 5c: Filled address information");

        // Verify account created
        Assert.assertTrue(signUpPage.isCreateAccountVisible(), "Account should be created successfully");
        System.out.println("✓ Step 5d: Account created successfully");

        signUpPage.clickContinueCreateBtn();
        System.out.println("✓ Step 5e: Clicked Continue button");

        // Step 6: Verify 'Logged in as username' at top
        HomePage homePage2 = new HomePage(driver);
        homePage2.waitForPageStable();
        homePage2.waitForElementPresent(By.xpath("//li//b[contains(text(),'TestUser')]"));
        String username = signUpPage.getLoggedInUsername();
        Assert.assertNotNull(username, "Logged in username should be displayed");
        System.out.println("✓ Step 6: Logged in as: " + username);

        // Step 7: Add product to cart
        homePage = new HomePage(driver);
        homePage.clickAddToCartBtn();
        System.out.println("✓ Step 7: Product added to cart");

        // Step 8: Click Cart button & verify cart page
        CartPage cartPage = homePage.clickViewCartBtn();
        Assert.assertFalse(cartPage.getCartItems().isEmpty(), "Cart should have items");
        System.out.println("✓ Step 8-9: Cart page displayed with product(s)");

        // Step 10: Click Proceed To Checkout
        CheckoutPage checkoutPage = cartPage.clickProceedToCheckOutBtnAsLoggedIn();
        System.out.println("✓ Step 10: Clicked Proceed To Checkout");

        // Step 11: Verify Address Details and Review Your Order
        CheckoutPage checkoutPage2 = new CheckoutPage(driver);
        checkoutPage2.waitForElementVisible(By.xpath("//h2[contains(text(),'Address Details')]"));
        Assert.assertTrue(checkoutPage.isAddressDetailsVisible(), "Address Details section should be visible");
        Assert.assertTrue(checkoutPage.isReviewOrderVisible(), "Review Your Order section should be visible");
        System.out.println("✓ Step 11: Address Details and Review Order verified");

        // Step 12: Enter comment and click Place Order
        String comment = Generator.randomMesseger(3);
        checkoutPage.enterComment(comment);
        System.out.println("✓ Step 12a: Comment entered: " + comment);

        PaymentPage paymentPage = checkoutPage.clickPlaceOrderBtn();
        System.out.println("✓ Step 12b: Clicked Place Order button");

        // Step 13: Enter payment details
        String cardName = "Test User";
        String cardNumber = "4532015112830366";
        String cvc = "123";
        String expiryMonth = "12";
        String expiryYear = "2025";

        paymentPage.enterNameOnCard(cardName);
        paymentPage.enterCardNumber(cardNumber);
        paymentPage.enterCVC(cvc);
        paymentPage.enterExpiryMonth(expiryMonth);
        paymentPage.enterExpiryYear(expiryYear);
        System.out.println("✓ Step 13: Payment details entered");

        // Step 14: Click Pay and Confirm Order
        paymentPage.clickPayAndConfirmOrderBtn();
        System.out.println("✓ Step 14: Clicked Pay and Confirm Order button");

        // Step 15: Verify success message
        PaymentPage paymentPage2 = new PaymentPage(driver);
        paymentPage2.waitForElementVisible(By.xpath("//p[normalize-space()='Congratulations! Your order has been confirmed!']"));
        Assert.assertTrue(paymentPage.isSuccessMessageDisplayed(), "Success message should be displayed");
        String successMsg = paymentPage.getSuccessMessage();
        Assert.assertTrue(successMsg.contains("order has been confirmed") || successMsg.contains("successfully"),
                "Success message should contain confirmation text. Actual: " + successMsg);
        System.out.println("✓ Step 15: Order placed successfully! Message: " + successMsg);

        // Step 16: Click Delete Account
        signUpPage = new SignUpPage(driver);
        signUpPage.clickDeleteAccountBtn();
        System.out.println("✓ Step 16: Clicked Delete Account button");

        // Step 17: Verify Account Deleted and click Continue

        Assert.assertTrue(signUpPage.isAccountDeletedVisible(), "Account should be deleted");
        signUpPage.clickContinueDeleteBtn();
        System.out.println("✓ Step 17: Account deleted successfully");

        System.out.println("\n✓✓✓ TC_16 PASSED - Complete Checkout Flow Successful ✓✓✓");
    }

}

