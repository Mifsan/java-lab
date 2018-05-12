package ru.mifsan.javacore2017.util;


import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class RandomHelperTest {
    @Test
    void getRandomNumberReturnsFromIfFromIsEqualsToTo() {
        int from = 3;
        int to = 3;
        assertEquals(from, RandomHelper.getRandomNumber(from, to));
    }
}