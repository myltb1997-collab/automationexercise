package myle.testcases;

import myle.pages.HomePage;
import myle.utilities.Generator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    HomePage homePage;

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


}
