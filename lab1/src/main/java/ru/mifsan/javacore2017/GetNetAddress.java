package ru.mifsan.javacore2017;

class GetNetAddress {
    private static final int ARGUMENT_COUNT = 2;
    private static final int BLOCK_COUNT = 4;

    public static void main(String[] args) {
        checkArguments(args);
        byte[] ipAddress = tryToGetArgument(args[0]);
        byte[] networkMask = tryToGetArgument(args[1]);
        System.out.println(getNetAddress(ipAddress, networkMask));
    }

    private static void checkArguments(String[] arguments) {
        if (arguments.length != ARGUMENT_COUNT) {
            printErrorLine("usage: GetNetAddress <ip address> <network mask>", 1);
        }
    }

    private static byte[] tryToGetArgument(String argument) {
        byte[] result = new byte[BLOCK_COUNT];
        String[] bytesStrings = tryToSplitByteString(argument);
        for (int i = 0; i < bytesStrings.length; ++i) {
            try {
                result[i] = (byte)Integer.parseInt(bytesStrings[i]);
            } catch (NumberFormatException exception) {
                printErrorLine("Invalid number: " + bytesStrings[i], 3);
            }
        }
        return result;
    }

    private static String[] tryToSplitByteString(String byteString) {
        String[] result = byteString.split("[.]");
        if (result.length != BLOCK_COUNT) {
            printErrorLine("Invalid argument: '" + byteString + "', " + BLOCK_COUNT + " blocks expected", 2);
        }
        return result;
    }

    private static StringBuilder getNetAddress(byte[] ipAddress, byte[] networkMask) {
        StringBuilder result = new StringBuilder();
        for (int i = 0; i < BLOCK_COUNT; ++i) {
            result.append(ipAddress[i] & networkMask[i] & 0xFF);
            result.append(".");
        }
        result.deleteCharAt(result.length() - 1);
        return result;
    }

    private static void printErrorLine(String msg, int errorCode) {
        System.out.println(String.format("Error: %s", msg));
        System.exit(errorCode);
    }
}
