package myle.pages;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class TestCasesPage {
    @FindBy(xpath = "//b[text()='Test Cases']")
    WebElement testCasesText;

    public TestCasesPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
    }

    public boolean isTestCasesPageVisible() {
        return testCasesText.isDisplayed();
    }

    @Deprecated
    public boolean isTestCasePageVisible() {
        return isTestCasesPageVisible();
    }
}
