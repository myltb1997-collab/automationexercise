package myle.listeners;

import io.qameta.allure.Allure;
import myle.testcases.BaseTest;
import myle.utilities.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;
import org.testng.TestListenerAdapter;

import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestExecutionListener extends TestListenerAdapter {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = buildTestName(result);
        WebDriver driver = resolveDriver(result);
        Path screenshot = ScreenshotUtil.captureFailedScreenshot(driver, testName);

        if (screenshot != null) {
            attachToAllure(screenshot, testName);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = buildTestName(result);
        WebDriver driver = resolveDriver(result);
        // capture final screenshot for passed tests (last step)
        Path screenshot = ScreenshotUtil.captureScreenshot(driver, testName + "_passed");

        if (screenshot != null) {
            attachToAllure(screenshot, testName + " (passed)");
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = buildTestName(result);
        WebDriver driver = resolveDriver(result);
        Path screenshot = ScreenshotUtil.captureScreenshot(driver, testName + "_skipped");

        if (screenshot != null) {
            attachToAllure(screenshot, testName + " (skipped)");
        }
    }

    private WebDriver resolveDriver(ITestResult result) {
        if (result == null || result.getInstance() == null) {
            return null;
        }

        Object testInstance = result.getInstance();
        if (testInstance instanceof BaseTest) {
            return ((BaseTest) testInstance).getDriver();
        }

        return null;
    }

    private void attachToAllure(Path screenshot, String testName) {
        try (InputStream inputStream = Files.newInputStream(screenshot)) {
            Allure.addAttachment("Failed screenshot - " + testName, "image/png", inputStream, "png");
        } catch (IOException e) {
            System.err.println("Error attaching screenshot to Allure: " + e.getMessage());
        }
    }

    private String buildTestName(ITestResult result) {
        if (result == null || result.getTestClass() == null || result.getMethod() == null) {
            return "failed_test";
        }

        return result.getTestClass().getRealClass().getSimpleName()
                + "_"
                + result.getMethod().getMethodName();
    }
}
