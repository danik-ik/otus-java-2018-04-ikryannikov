package ru.otus.danik_ik.homework05.testo;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class AssertionsTest {
    @Test
    public void assertTrue () {
        Assertions.Assert(true);
    }

    @Test(expected = AssertionError.class)
    public void assertFalse () {
        Assertions.Assert(false);
    }

    @Test
    public void assertStringsAreEquals () {
        Assertions.Assert("abcdef", "abc" + "def");
    }

    @Test(expected = AssertionError.class)
    public void assertStringsAreNotEquals () {
        Assertions.Assert("abcdef", "abc");
    }

    @Test
    public void assertIntegersAreEquals () {
        Assertions.Assert(10, 7 + 3);
    }

    @Test(expected = AssertionError.class)
    public void assertIntegersAreNotEquals () {
        Assertions.Assert(10, 4);
    }

}
