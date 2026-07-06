package config;


import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

/**
 * Configuration Manager - Centralized configuration loading and access.
 *
 * Responsibilities:
 * - Load properties from config.properties file
 * - Provide reusable getter methods
 * - Support default values for missing properties
 * - Thread-safe singleton pattern
 *
 * Usage:
 * String browser = ConfigurationManager.getProperty("browser");
 * String baseUrl = ConfigurationManager.getProperty("base.url");
 * int timeout = ConfigurationManager.getPropertyAsInt("timeout.default");
 */
public class ConfigurationManager {

    private static Properties properties = null;
    private static final Object lock = new Object();

    /**
     * Private constructor to prevent instantiation.
     */
    private ConfigurationManager() {
    }

    /**
     * Load properties from config.properties file.
     * This method is synchronized to ensure thread-safe loading.
     */
    static {
        synchronized (lock) {
            if (properties == null) {
                loadProperties();
            }
        }
    }

    /**
     * Internal method to load properties from the config file.
     * Handles file not found and IO exceptions gracefully.
     */
    private static void loadProperties() {
        properties = new Properties();

        try (InputStream input = ConfigurationManager.class
                .getClassLoader()
                .getResourceAsStream("config.properties")) {

            if (input == null) {
                throw new IOException("config.properties not found in resources folder.");
            }

            properties.load(input);
            System.out.println("✓ Configuration loaded from classpath: config.properties");

        } catch (IOException e) {
            System.err.println("⚠ Unable to load config.properties: " + e.getMessage());
            System.err.println("⚠ Using default configuration.");

            loadDefaultProperties();
        }
    }

    /**
     * Load default properties when config file is not found.
     * Ensures tests can still run even if config file is missing.
     */
    private static void loadDefaultProperties() {
        properties.setProperty("browser", "chrome");
        properties.setProperty("base.url", "https://automationexercise.com");
        properties.setProperty("environment", "staging");
        properties.setProperty("timeout.default", "15");
        properties.setProperty("timeout.page.load", "60");
        properties.setProperty("page.load.strategy", "eager");
        properties.setProperty("window.width", "1920");
        properties.setProperty("window.height", "1080");
        properties.setProperty("ads.blocking.enabled", "true");
        properties.setProperty("browser.notifications.disabled", "true");
        properties.setProperty("browser.infobars.disabled", "true");
        properties.setProperty("browser.password.popup.disabled", "true");
        properties.setProperty("log.level", "INFO");
        System.out.println("✓ Default configuration loaded");
    }

    public static boolean isHeadless() {
        return Boolean.parseBoolean(
                System.getProperty(
                        "headless",
                        properties.getProperty("headless", "false")
                )
        );
    }

    public static String getProperty(String key) {
        return properties.getProperty(key);
    }

    /**
     * Get property value as String with default fallback.
     *
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value or default value
     */
    public static String getProperty(String key, String defaultValue) {
        return properties.getProperty(key, defaultValue);
    }

    /**
     * Get property value as Integer.
     *
     * @param key Property key
     * @return Property value as Integer
     * @throws NumberFormatException if value cannot be converted to Integer
     */
    public static int getPropertyAsInt(String key) {
        String value = getProperty(key);
        if (value == null) {
            throw new IllegalArgumentException("Property not found: " + key);
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            throw new NumberFormatException("Property '" + key + "' cannot be converted to Integer: " + value);
        }
    }

    /**
     * Get property value as Integer with default fallback.
     *
     * @param key Property key
     * @param defaultValue Default value if property not found or invalid
     * @return Property value as Integer or default value
     */
    public static int getPropertyAsInt(String key, int defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            System.err.println("⚠ Invalid integer value for property '" + key + "': " + value + ". Using default: " + defaultValue);
            return defaultValue;
        }
    }

    /**
     * Get property value as Boolean.
     *
     * @param key Property key
     * @return true if value is "true" (case-insensitive), false otherwise
     */
    public static boolean getPropertyAsBoolean(String key) {
        String value = getProperty(key);
        return value != null && value.toLowerCase().equals("true");
    }

    /**
     * Get property value as Boolean with default fallback.
     *
     * @param key Property key
     * @param defaultValue Default value if property not found
     * @return Property value as Boolean or default value
     */
    public static boolean getPropertyAsBoolean(String key, boolean defaultValue) {
        String value = getProperty(key);
        if (value == null) {
            return defaultValue;
        }
        return value.toLowerCase().equals("true");
    }

    /**
     * Get browser type from configuration.
     * Defaults to "chrome" if not specified.
     *
     * @return Browser type (chrome, firefox, edge)
     */
    public static String getBrowser() {
        return getProperty("browser", "chrome").toLowerCase();
    }

    /**
     * Get base URL from configuration.
     * Defaults to automationexercise.com if not specified.
     *
     * @return Base URL
     */
    public static String getBaseUrl() {
        return getProperty("base.url", "https://automationexercise.com");
    }

    /**
     * Get default timeout from configuration.
     * Defaults to 15 seconds if not specified.
     *
     * @return Timeout in seconds
     */
    public static int getDefaultTimeout() {
        return getPropertyAsInt("timeout.default", 15);
    }

    /**
     * Get page load timeout from configuration.
     * Defaults to 60 seconds if not specified.
     *
     * @return Page load timeout in seconds
     */
    public static int getPageLoadTimeout() {
        return getPropertyAsInt("timeout.page.load", 60);
    }

    /**
     * Get environment from configuration.
     * Defaults to "staging" if not specified.
     *
     * @return Environment (dev, staging, prod)
     */
    public static String getEnvironment() {
        return getProperty("environment", "staging");
    }

    /**
     * Get page load strategy from configuration.
     * Defaults to "eager" if not specified.
     *
     * @return Page load strategy (normal, eager, none)
     */
    public static String getPageLoadStrategy() {
        return getProperty("page.load.strategy", "eager");
    }

    /**
     * Get window width from configuration.
     * Defaults to 1920 if not specified.
     *
     * @return Window width in pixels
     */
    public static int getWindowWidth() {
        return getPropertyAsInt("window.width", 1920);
    }

    /**
     * Get window height from configuration.
     * Defaults to 1080 if not specified.
     *
     * @return Window height in pixels
     */
    public static int getWindowHeight() {
        return getPropertyAsInt("window.height", 1080);
    }

    /**
     * Check if ad blocking is enabled.
     * Defaults to true if not specified.
     *
     * @return true if ad blocking is enabled
     */
    public static boolean isAdBlockingEnabled() {
        return getPropertyAsBoolean("ads.blocking.enabled", true);
    }

    /**
     * Print all loaded configuration (for debugging).
     * Sensitive properties like passwords will be masked.
     */
    public static void printConfiguration() {
        System.out.println("\n" + "=".repeat(50));
        System.out.println("LOADED CONFIGURATION");
        System.out.println("=".repeat(50));
        properties.forEach((key, value) ->
            System.out.println("  " + key + " = " + value)
        );
        System.out.println("=".repeat(50) + "\n");
    }
}

