package myle.testcases;

import myle.pages.HomePage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class HomeTest extends BaseTest {
    HomePage homePage;

    @Test(priority = 1) //Test Case 10: Verify Subscription in home page
    public void verifySubscription() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible());
        homePage.scrollToBottom();
        Assert.assertTrue(homePage.getSubscriptionText().contains("SUBSCRIPTION"));
        homePage.setSubscribeEmail("myle02034568@gmail.com");
        homePage.clickSubscribleBtn();
        Assert.assertTrue(homePage.isSubscribeSuccess().contains("You have been successfully subscribed!"));
    }


}
