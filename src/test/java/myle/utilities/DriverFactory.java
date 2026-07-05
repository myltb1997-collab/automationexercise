package myle.utilities;

import org.openqa.selenium.WebDriver;

/**
 * DEPRECATED: Use driver.DriverFactory instead.
 *
 * This class is kept for backward compatibility only.
 * New code should use the centralized DriverFactory in the driver package.
 *
 * The new DriverFactory provides:
 * - Configuration management integration
 * - Multi-browser support (Chrome, Firefox, Edge)
 * - WebDriverManager integration
 * - Better error handling
 *
 * @deprecated Use driver.DriverFactory instead
 */
@Deprecated(since = "2.0", forRemoval = true)
public class DriverFactory {

    /**
     * @deprecated Use driver.DriverFactory.initDriver() instead
     */
    @Deprecated
    public static WebDriver createChromeDriver() {
        System.err.println(" DEPRECATED: myle.utilities.DriverFactory is deprecated.");
        System.err.println(" Please use driver.DriverFactory.initDriver() instead.");
        return driver.DriverFactory.initDriver();
    }
}

