package ru.mifsan.javacore2017.util;

public class RandomHelper {
    public static int getRandomNumber(int from, int to) {
        return (int) (Math.round(Math.random() * (to - from)) + from);
    }
}
