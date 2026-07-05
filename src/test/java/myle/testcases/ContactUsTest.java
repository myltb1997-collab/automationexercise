package myle.testcases;

import io.qameta.allure.Step;
import myle.pages.ContactUsPage;
import myle.pages.HomePage;
import myle.pages.TestCasesPage;
import myle.utilities.Generator;
import myle.utilities.TestDataReader;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.nio.file.Paths;

public class ContactUsTest extends BaseTest {
    HomePage homePage;
    ContactUsPage contactUsPage;

    @Test(priority = 1)
    @Step("Submit contact us form with file attachment and verify success")
    public void testContactUsForm() {
        String feedbackFilePath = Paths.get(
                System.getProperty("user.dir"),
                "src",
                "uploadFiles",
                TestDataReader.getString("contactUs.feedbackFile")
        ).toString();

        homePage = new HomePage(driver);
        String name = TestDataReader.getString("contactUs.name");
        String email = Generator.randomEmail();
        String subject = TestDataReader.getString("contactUs.subjectPrefix") + System.currentTimeMillis();
        String message = Generator.randomMessage(TestDataReader.getInt("contactUs.messageLength"));

        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        contactUsPage = homePage.openContactUsPage();
        Assert.assertTrue(contactUsPage.isGetInTouchVisible());

        contactUsPage.enterName(name);
        contactUsPage.enterEmail(email);
        contactUsPage.enterSubject(subject);
        contactUsPage.enterMessage(message);

        contactUsPage.uploadFile(feedbackFilePath);
        contactUsPage.clickSubmitBtn();
        contactUsPage.clickOkBtn();

        Assert.assertTrue(contactUsPage.isSendFeedBackSuccess());
        contactUsPage.clickToHomeBtn();
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
    }

    @Test(priority = 2)
    @Step("Navigate to and verify test cases page")
    public void testTestCasePage() {
        homePage = new HomePage(driver);
        Assert.assertTrue(homePage.isHomePageVisible(), "Verify that home page is visible successfully");
        TestCasesPage testCasesPage = homePage.openTestCasesPage();
        Assert.assertTrue(testCasesPage.isTestCasesPageVisible());
    }
}
