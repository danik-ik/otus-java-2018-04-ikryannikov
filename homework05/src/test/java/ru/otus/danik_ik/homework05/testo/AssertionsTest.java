package ru.otus.danik_ik.homework05.testo;

import org.junit.Test;

public class AssertionsTest {
    @Test
    public void assertTrue () {
        Assertions.assertTrue(true);
    }

    @Test(expected = AssertionError.class)
    public void assertFalse () {
        Assertions.assertTrue(false);
    }

    @Test
    public void assertStringsAreEquals () {
        Assertions.assertEquals("abcdef", "abc" + "def");
    }

    @Test(expected = AssertionError.class)
    public void assertStringsAreNotEquals () {
        Assertions.assertEquals("abcdef", "abc");
    }

    @Test
    public void assertIntegersAreEquals () {
        Assertions.assertEquals(10, 7 + 3);
    }

    @Test(expected = AssertionError.class)
    public void assertIntegersAreNotEquals () {
        Assertions.assertEquals(10, 4);
    }

}
