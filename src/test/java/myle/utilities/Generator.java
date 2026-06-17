package myle.utilities;

public class Generator {
    public static String randomEmail() {
        return TestDataGenerator.randomEmail();
    }
    public static String randomEmail(String testName) {
        return TestDataGenerator.randomEmail(testName);
    }
    public static String randomMessage(int length) {
        return TestDataGenerator.randomMessage(length);
    }
    /**
     * @deprecated Use randomMessage(int length) instead. Kept for backward compatibility.
     */
    @Deprecated
    public static String randomMesseger(int length) {
        return TestDataGenerator.randomMessage(length);
    }
}
