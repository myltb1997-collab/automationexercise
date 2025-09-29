package myle.utilities;

import java.util.Random;

public class Generator {
    public static String randomEmail() {
        String firstname = "tester";
        String domain = "@gmail.com";
        String bodyrandom = getRandomAlphaNumeric(6);
        String email = firstname + bodyrandom + domain;
        return email;

    }

    private static String getRandomAlphaNumeric(int lenght) {
        String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
        StringBuilder sBuilder = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < lenght; i++) {
            sBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
        }
        return sBuilder.toString();
    }

    public static String randomMesseger(int lenght) {
        String words[] = {"hello", "test", "automation", "java", "selenium", "data", "input", "random", "sample", "user"};
        StringBuilder sb = new StringBuilder();
        Random random = new Random();

        for (int i = 0; i < lenght; i++) {
            sb.append(words[random.nextInt(words.length)]).append(" ");
        }
        return sb.toString().trim();
    }

      /*  public static String GenarateEmail(String fristname, String lastname, String domain ) {

            String normalizedFirstNameString = fristname.trim().toLowerCase();
            String normalizedLastNameString = lastname.trim().toLowerCase();
            String normalizedDomainString = domain.trim().toLowerCase();

            String randomSuffix = GenerateRandomString(6);
            String email = normalizedFirstNameString + normalizedLastNameString + randomSuffix + normalizedDomainString;

            return email;
        }
        private static String GenerateRandomString(int lenght) {
            String CHARS = "abcdefghijklmnopqrstuvwxyz0123456789";
            Random random = new Random();
            StringBuilder sBuilder = new StringBuilder();

            for (int i = 0; i<lenght ; i++) {
                sBuilder.append(CHARS.charAt(random.nextInt(CHARS.length())));
            }
            return sBuilder.toString();

        }
*/


}

