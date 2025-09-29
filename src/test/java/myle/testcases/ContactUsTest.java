package myle.testcases;

import myle.pages.ContactUsPage;
import myle.pages.HomePage;
import myle.utilities.Generator;
import org.testng.Assert;
import org.testng.annotations.Test;

public class ContactUsTest extends BaseTest {
    HomePage homePage;
    ContactUsPage contactUsPage;

    @Test(priority = 1)
    public void testContactUsForm() throws InterruptedException {

        homePage = new HomePage(driver);
        //contactUsPage = new ContactUsPage(driver);
        String name = "TC6";
        String email = Generator.randomEmail();
        String subject = "Test TC6" + System.currentTimeMillis();
        String message = Generator.randomMesseger(30);

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        contactUsPage = homePage.movToContactPage();
        Assert.assertTrue(contactUsPage.isGetInTouchVisible());

        contactUsPage.enterName(name);
        contactUsPage.enterEmail(email);
        contactUsPage.enterSubject(subject);
        contactUsPage.enterMessage(message);

        contactUsPage.uploadFile();
        contactUsPage.clickSubmitBtn();
        //chuyeern ve alert popup
        contactUsPage.clickOkBtn();

        Assert.assertTrue(contactUsPage.isSendFeedBackSuccess());
        contactUsPage.clickToHomeBtn();
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
    }

    @Test(priority = 2)
    public void testTestCasePage() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        contactUsPage = homePage.moveToTestCasePage();
        Assert.assertTrue(contactUsPage.isTestCasePageVisible());
    }


}
