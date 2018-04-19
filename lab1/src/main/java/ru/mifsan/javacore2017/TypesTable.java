package ru.mifsan.javacore2017;

public class TypesTable {
    public static void main(String [] args) {
        printType("Type", "Min", "Max", "Size");
        printType("Long", toString(Long.MIN_VALUE), toString(Long.MAX_VALUE), toString(Long.SIZE));
        printType("Integer", toString(Integer.MIN_VALUE), toString(Integer.MAX_VALUE), toString(Integer.SIZE));
        printType("Short", toString(Short.MIN_VALUE), toString(Short.MAX_VALUE), toString(Short.SIZE));
        printType("Byte", toString(Byte.MIN_VALUE), toString(Byte.MAX_VALUE), toString(Byte.SIZE));
        printType("Double", toString(-Double.MAX_VALUE), toString(Double.MAX_VALUE), toString(Double.SIZE));
        printType("Float", toString(-Float.MAX_VALUE), toString(Float.MAX_VALUE), toString(Float.SIZE));
        printType("Character", toString(Character.MIN_VALUE), toString(Character.MAX_VALUE), toString(Character.SIZE));
    }

    private static void printType(String typeName, String minValue, String maxValue, String size) {
        System.out.printf("%-9s %-23s %-22s %-4s%n", typeName, minValue, maxValue, size);
    }

    private static String toString(long value) {
        return Long.valueOf(value).toString();
    }

    private static String toString(int value) {
        return Integer.valueOf(value).toString();
    }

    private static String toString(short value) {
        return Short.valueOf(value).toString();
    }

    private static String toString(byte value) {
        return Byte.valueOf(value).toString();
    }

    private static String toString(double value) {
        return Double.valueOf(value).toString();
    }

    private static String toString(float value) {
        return Float.valueOf(value).toString();
    }

    private static String toString(char value) {
        return Integer.valueOf(value).toString();
    }
}
