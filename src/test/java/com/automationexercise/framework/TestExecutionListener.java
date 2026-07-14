package com.automationexercise.framework;

import io.qameta.allure.Allure;
import com.automationexercise.tests.BaseTest;
import com.automationexercise.utils.ScreenshotUtil;
import org.openqa.selenium.WebDriver;
import org.testng.ITestListener; // Sử dụng giao diện ITestListener chuẩn
import org.testng.ITestResult;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.Files;
import java.nio.file.Path;

public class TestExecutionListener implements ITestListener {

    @Override
    public void onTestFailure(ITestResult result) {
        String testName = buildTestName(result);
        WebDriver driver = resolveDriver(result);
        Path screenshot = ScreenshotUtil.captureFailedScreenshot(driver, testName);

        if (screenshot != null) {
            attachToAllure("Failed screenshot - " + testName, screenshot);
        }
    }

    @Override
    public void onTestSuccess(ITestResult result) {
        String testName = buildTestName(result);
        WebDriver driver = resolveDriver(result);
        // capture final screenshot for passed tests (last step)
        Path screenshot = ScreenshotUtil.captureScreenshot(driver, testName + "_passed");

        if (screenshot != null) {
            attachToAllure("Success screenshot - " + testName, screenshot);
        }
    }

    @Override
    public void onTestSkipped(ITestResult result) {
        String testName = buildTestName(result);
        WebDriver driver = resolveDriver(result);
        Path screenshot = ScreenshotUtil.captureScreenshot(driver, testName + "_skipped");

        if (screenshot != null) {
            attachToAllure("Skipped screenshot - " + testName, screenshot);
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

    private void attachToAllure(String attachmentName, Path screenshot) {
        try (InputStream inputStream = Files.newInputStream(screenshot)) {
            // Sử dụng Allure Lifecycle API để cưỡng chế nhét file vào report an toàn
            Allure.getLifecycle().addAttachment(
                    attachmentName,
                    "image/png",
                    "png",
                    inputStream
            );
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
