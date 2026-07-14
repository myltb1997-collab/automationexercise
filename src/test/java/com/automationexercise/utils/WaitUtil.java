package com.automationexercise.utils;

import config.ConfigurationManager;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public final class WaitUtil {
    private WaitUtil() {
    }

    public static WebDriverWait createWait(WebDriver driver) {
        return createWait(driver, defaultTimeout());
    }

    public static WebDriverWait createWait(WebDriver driver, int timeoutSeconds) {
        return createWait(driver, Duration.ofSeconds(timeoutSeconds));
    }

    public static WebDriverWait createWait(WebDriver driver, Duration timeout) {
        return new WebDriverWait(driver, timeout);
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element) {
        return waitForVisible(driver, element, defaultTimeout());
    }

    public static WebElement waitForVisible(WebDriver driver, WebElement element, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.visibilityOf(element));
    }

    public static void waitForInvisible(WebDriver driver, WebElement element) {
        waitForInvisible(driver, element, defaultTimeout());
    }

    public static void waitForInvisible(WebDriver driver, WebElement element, Duration timeout) {
        createWait(driver, timeout).until(ExpectedConditions.invisibilityOf(element));
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element) {
        return waitForClickable(driver, element, defaultTimeout());
    }

    public static WebElement waitForClickable(WebDriver driver, WebElement element, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(element));
    }

    public static void waitForPageReady(WebDriver driver) {
        waitForPageReady(driver, defaultTimeout());
    }

    public static void waitForPageReady(WebDriver driver, Duration timeout) {
        createWait(driver, timeout).until(webDriver ->
                "complete".equals(String.valueOf(
                        ((JavascriptExecutor) webDriver).executeScript("return document.readyState"))));
    }

    public static WebElement waitForElementVisible(WebDriver driver, By by) {
        return waitForElementVisible(driver, by, defaultTimeout());
    }

    public static WebElement waitForElementVisible(WebDriver driver, By by, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.visibilityOfElementLocated(by));
    }

    public static WebElement waitForElementPresent(WebDriver driver, By by) {
        return waitForElementPresent(driver, by, defaultTimeout());
    }

    public static WebElement waitForElementPresent(WebDriver driver, By by, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.presenceOfElementLocated(by));
    }

    public static WebElement waitForElementClickable(WebDriver driver, By by) {
        return waitForElementClickable(driver, by, defaultTimeout());
    }

    public static WebElement waitForElementClickable(WebDriver driver, By by, Duration timeout) {
        return createWait(driver, timeout).until(ExpectedConditions.elementToBeClickable(by));
    }

    public static void waitForElementInvisible(WebDriver driver, By by) {
        waitForElementInvisible(driver, by, defaultTimeout());
    }

    public static void waitForElementInvisible(WebDriver driver, By by, Duration timeout) {
        createWait(driver, timeout).until(ExpectedConditions.invisibilityOfElementLocated(by));
    }

    public static void waitForTextInElement(WebDriver driver, By by, String text) {
        waitForTextInElement(driver, by, text, defaultTimeout());
    }

    public static void waitForTextInElement(WebDriver driver, By by, String text, Duration timeout) {
        createWait(driver, timeout).until(ExpectedConditions.textToBePresentInElementLocated(by, text));
    }

    public static void waitForUrlContains(WebDriver driver, String urlPart) {
        waitForUrlContains(driver, urlPart, defaultTimeout());
    }

    public static void waitForUrlContains(WebDriver driver, String urlPart, Duration timeout) {
        createWait(driver, timeout).until(ExpectedConditions.urlContains(urlPart));
    }

    private static Duration defaultTimeout() {
        return Duration.ofSeconds(ConfigurationManager.getDefaultTimeout());
    }
}

