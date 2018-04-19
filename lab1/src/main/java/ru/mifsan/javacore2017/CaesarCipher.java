package ru.mifsan.javacore2017;

import java.lang.StringBuilder;

class CaesarCipher {
    private static final int ARGUMENT_COUNT = 3;
    private static final int ALPHABET_SIZE = 26;
    private static final int FIRST_LETTER_CODE = (int) 'a';
    private static final int LAST_LETTER_CODE = (int) 'z';
    private static final String ENCRYPTION_OPTION = "-e";
    private static final String DECRYPTION_OPTION = "-d";

    public static void main(String[] args) {
        checkArguments(args);
        boolean encrypt = args[0].equals(ENCRYPTION_OPTION);
        int shiftCount = tryToGetShiftCount(encrypt, args[1]);
        processInputString(args[2], shiftCount);
    }

    private static void checkArguments(String[] arguments) {
        if (arguments.length != ARGUMENT_COUNT) {
            printErrorLine("usage: CaesarCipher <-e|-d> <shift count> <input string>", 1);
        }
        if (!arguments[0].equals(ENCRYPTION_OPTION) && !arguments[0].equals(DECRYPTION_OPTION)) {
            printErrorLine("Invalid option " + arguments[0] + ". Use " + ENCRYPTION_OPTION + " for encrypt or " + DECRYPTION_OPTION + " for decrypt", 2);
        }
    }

    private static int tryToGetShiftCount(boolean encrypt, String argument) {
        try {
            int result = Integer.valueOf(argument);
            return encrypt ? result : -result;
        } catch(NumberFormatException ex) {
            printErrorLine("Invalid argument '" + argument + "', number expected", 3);
        }
        return 0;
    }

    private static void processInputString(String inputString, int shiftCount) {
        StringBuilder inputStringBuilder = new StringBuilder(inputString);
        for (int i = 0; i < inputStringBuilder.length(); ++i) {
            int charPos = inputStringBuilder.codePointAt(i);
            int newCharPos = charPos + shiftCount;
            while (newCharPos < FIRST_LETTER_CODE) {
                newCharPos += ALPHABET_SIZE;
            }
            while (newCharPos > LAST_LETTER_CODE) {
                newCharPos -= ALPHABET_SIZE;
            }
            inputStringBuilder.setCharAt(i, (char)newCharPos);
        }
        System.out.println(inputStringBuilder);
    }

    private static void printErrorLine(String msg, int errorCode) {
        System.out.println(String.format("Error: %s", msg));
        System.exit(errorCode);
    }
}
