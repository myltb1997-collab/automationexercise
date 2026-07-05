package myle.utilities;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

public final class ScreenshotUtil {
    private static final Path SCREENSHOT_DIR = Paths.get(System.getProperty("user.dir"), "target", "screenshots");
    private static final Path FAILED_SCREENSHOT_DIR = SCREENSHOT_DIR.resolve("failed");
    private static final DateTimeFormatter TIMESTAMP = DateTimeFormatter.ofPattern("yyyyMMdd_HHmmss_SSS");

    private ScreenshotUtil() {
    }

    public static Path captureScreenshot(WebDriver driver, String fileNamePrefix) {
        return capture(driver, SCREENSHOT_DIR, fileNamePrefix);
    }

    public static Path captureFailedScreenshot(WebDriver driver, String testName) {
        return capture(driver, FAILED_SCREENSHOT_DIR, testName);
    }

    private static Path capture(WebDriver driver, Path directory, String fileNamePrefix) {
        if (driver == null || !(driver instanceof TakesScreenshot)) {
            return null;
        }

        try {
            Files.createDirectories(directory);

            String baseName = sanitizeFileName(fileNamePrefix);
            String timestamp = LocalDateTime.now().format(TIMESTAMP);
            Path targetFile = directory.resolve(baseName + "_" + timestamp + ".png");

            File screenshot = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(screenshot.toPath(), targetFile, StandardCopyOption.REPLACE_EXISTING);
            return targetFile;
        } catch (IOException | RuntimeException e) {
            System.err.println("Error capturing screenshot: " + e.getMessage());
            return null;
        }
    }

    private static String sanitizeFileName(String value) {
        if (value == null || value.isBlank()) {
            return "screenshot";
        }
        return value.replaceAll("[^a-zA-Z0-9._-]", "_");
    }
}
