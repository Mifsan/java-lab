package ru.mifsan.javacore2017;

import java.util.Random;

class PasswordGenerator {
    private static final int ARGUMENT_COUNT = 2;

    public static void main(String[] args) {
        checkArgumentCount(args);
        System.out.println(generatePassword(TryToGetPasswordLength(args[0]), args[1]));
    }

    private static void checkArgumentCount(String[] args) {
        if (args.length != ARGUMENT_COUNT) {
            System.out.println(String.format("usage: PasswordGenerator <password length> <password symbols>"));
            System.exit(1);
        }
    }

    private static int TryToGetPasswordLength(String argument) {
        try {
            return Integer.parseInt(argument);
        } catch (NumberFormatException exception) {
            System.out.println("Failed to convert password length to number format");
            System.exit(2);
        }
        return 0;
    }

    private static String generatePassword(int passwordLength, String passwordSymbols) {
        StringBuilder result = new StringBuilder();
        Random random = new Random();
        for (int i = 0; i < passwordLength; ++i) {
            result.append(passwordSymbols.charAt(random.nextInt(passwordSymbols.length())));
        }
        return result.toString();
    }
}