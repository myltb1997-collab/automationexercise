package myle.utilities;

import java.util.Random;

public class TestDataGenerator {
    public static String randomEmail() {
        String firstname = "Myle_test";
        String domain = "@gmail.com";
        String bodyRandom = getRandomAlphaNumeric(6);
        return firstname + bodyRandom + domain;
    }

    public static String randomEmail(String testName) {
        String normalized = testName.replaceAll("[^a-zA-Z0-9]", "").toLowerCase();
        String bodyRandom = getRandomAlphaNumeric(6);
        return normalized + "_" + bodyRandom + "@gmail.com";
    }

    private static String getRandomAlphaNumeric(int length) {
        String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            sBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sBuilder.toString();
    }

    public static String randomMessage(int length) {
        String[] words = {"hello", "test", "automation", "java", "selenium", "data", "input", "random", "sample", "user"};
        StringBuilder messageBuilder = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < length; i++) {
            messageBuilder.append(words[random.nextInt(words.length)]).append(" ");
        }
        return messageBuilder.toString().trim();
    }
}