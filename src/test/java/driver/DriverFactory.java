package driver;

import config.ConfigurationManager;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;

import java.time.Duration;

/**
 * Driver Factory - Centralized WebDriver creation and management.
 *
 * Responsibilities:
 * - Create WebDriver instances using WebDriverManager
 * - Support multiple browsers (Chrome, Firefox, Edge)
 * - Configure driver with common settings from ConfigurationManager
 * - Handle initialization safely with exception handling
 *
 * Supported browsers: chrome, firefox, edge
 * Browser selection: Comes from config.properties
 *
 * Example usage:
 * WebDriver driver = DriverFactory.initDriver();
 * WebDriver driver = DriverFactory.getDriver();
 * DriverFactory.quitDriver(driver);
 */
public class DriverFactory {

    /**
     * Initialize and create WebDriver instance.
     * Uses configuration from ConfigurationManager for browser selection and settings.
     *
     * @return WebDriver instance
     * @throws IllegalArgumentException if browser type is not supported
     * @throws Exception if WebDriver initialization fails
     */
    public static WebDriver initDriver() {
        String browser = ConfigurationManager.getBrowser();
        System.out.println("\n" + "=".repeat(50));
        System.out.println("Initializing WebDriver: " + browser.toUpperCase());
        System.out.println("=".repeat(50));

        WebDriver driver;

        try {
            switch (browser.toLowerCase()) {
                case "chrome":
                    driver = createChromeDriver();
                    break;
                case "firefox":
                    driver = createFirefoxDriver();
                    break;
                case "edge":
                    driver = createEdgeDriver();
                    break;
                default:
                    throw new IllegalArgumentException("Unsupported browser: " + browser + ". Supported: chrome, firefox, edge");
            }

            // Configure common driver settings
            configureDriver(driver);

            System.out.println("✓ WebDriver initialized successfully: " + browser);
            System.out.println("=".repeat(50) + "\n");

            return driver;

        } catch (Exception e) {
            System.err.println("✗ Failed to initialize WebDriver for browser: " + browser);
            System.err.println("Error: " + e.getMessage());
            throw new RuntimeException("WebDriver initialization failed for browser: " + browser, e);
        }
    }

    /**
     * Create Chrome WebDriver with configured options.
     * Uses WebDriverManager to automatically manage ChromeDriver binary.
     *
     * @return ChromeDriver instance
     */
    private static WebDriver createChromeDriver() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();

        // Page load strategy
        PageLoadStrategy strategy = parsePageLoadStrategy(ConfigurationManager.getPageLoadStrategy());
        options.setPageLoadStrategy(strategy);

        // Disable notifications and popups
        if (ConfigurationManager.getPropertyAsBoolean("browser.notifications.disabled", true)) {
            options.addArguments("--disable-notifications");
        }
        if (ConfigurationManager.getPropertyAsBoolean("browser.infobars.disabled", true)) {
            options.addArguments("--disable-infobars");
        }
        if (ConfigurationManager.getPropertyAsBoolean("browser.password.popup.disabled", true)) {
            options.addArguments("--disable-save-password-bubble");
        }

        // Ad blocking via host resolver rules
        if (ConfigurationManager.isAdBlockingEnabled()) {
            options.addArguments("--host-resolver-rules=MAP doubleclick.net 127.0.0.1");
            options.addArguments("--host-resolver-rules=MAP googlesyndication.com 127.0.0.1");
            options.addArguments("--host-resolver-rules=MAP googleads.g.doubleclick.net 127.0.0.1");
        }

        return new ChromeDriver(options);
    }

    /**
     * Create Firefox WebDriver with configured options.
     * Uses WebDriverManager to automatically manage GeckoDriver binary.
     *
     * @return FirefoxDriver instance
     */
    private static WebDriver createFirefoxDriver() {
        WebDriverManager.firefoxdriver().setup();
        FirefoxOptions options = new FirefoxOptions();

        // Page load strategy
        PageLoadStrategy strategy = parsePageLoadStrategy(ConfigurationManager.getPageLoadStrategy());
        options.setPageLoadStrategy(strategy);

        // Disable notifications
        if (ConfigurationManager.getPropertyAsBoolean("browser.notifications.disabled", true)) {
            options.addPreference("dom.webnotifications.enabled", false);
        }

        return new FirefoxDriver(options);
    }

    /**
     * Create Edge WebDriver with configured options.
     * Uses WebDriverManager to automatically manage EdgeDriver binary.
     *
     * @return EdgeDriver instance
     */
    private static WebDriver createEdgeDriver() {
        WebDriverManager.edgedriver().setup();
        EdgeOptions options = new EdgeOptions();

        // Page load strategy
        PageLoadStrategy strategy = parsePageLoadStrategy(ConfigurationManager.getPageLoadStrategy());
        options.setPageLoadStrategy(strategy);

        // Disable notifications and popups
        if (ConfigurationManager.getPropertyAsBoolean("browser.notifications.disabled", true)) {
            options.addArguments("--disable-notifications");
        }
        if (ConfigurationManager.getPropertyAsBoolean("browser.infobars.disabled", true)) {
            options.addArguments("--disable-infobars");
        }

        return new EdgeDriver(options);
    }

    /**
     * Configure common settings for all drivers.
     * Settings applied: timeouts, window size, etc.
     *
     * @param driver WebDriver instance to configure
     */
    private static void configureDriver(WebDriver driver) {
        // Set implicit wait
        int defaultTimeout = ConfigurationManager.getDefaultTimeout();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(defaultTimeout));

        // Set page load timeout
        int pageLoadTimeout = ConfigurationManager.getPageLoadTimeout();
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(pageLoadTimeout));

        // Set window size
        int width = ConfigurationManager.getWindowWidth();
        int height = ConfigurationManager.getWindowHeight();
        driver.manage().window().setSize(new Dimension(width, height));

        System.out.println("  ✓ Timeouts: Default=" + defaultTimeout + "s, PageLoad=" + pageLoadTimeout + "s");
        System.out.println("  ✓ Window size: " + width + "x" + height);
    }

    /**
     * Get current WebDriver instance.
     * Note: This is a simple factory method. For thread-safe access, use DriverManager with ThreadLocal.
     *
     * @return WebDriver instance created by initDriver()
     */
    public static WebDriver getDriver() {
        return initDriver();
    }

    /**
     * Quit WebDriver safely.
     * Handles null driver and exceptions gracefully.
     *
     * @param driver WebDriver instance to quit
     */
    public static void quitDriver(WebDriver driver) {
        if (driver != null) {
            try {
                driver.quit();
                System.out.println("✓ WebDriver closed successfully");
            } catch (Exception e) {
                System.err.println("⚠ Error closing WebDriver: " + e.getMessage());
            }
        }
    }

    /**
     * Parse PageLoadStrategy from string configuration.
     *
     * @param strategy Strategy name (normal, eager, none)
     * @return PageLoadStrategy enum value
     */
    private static PageLoadStrategy parsePageLoadStrategy(String strategy) {
        try {
            switch (strategy.toLowerCase()) {
                case "eager":
                    return PageLoadStrategy.EAGER;
                case "none":
                    return PageLoadStrategy.NONE;
                case "normal":
                default:
                    return PageLoadStrategy.NORMAL;
            }
        } catch (Exception e) {
            System.err.println("⚠ Invalid page load strategy: " + strategy + ". Using EAGER");
            return PageLoadStrategy.EAGER;
        }
    }
}

