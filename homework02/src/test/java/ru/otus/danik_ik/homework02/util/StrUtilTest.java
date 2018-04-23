package ru.otus.danik_ik.homework02.util;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.stream.Stream;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static ru.otus.danik_ik.homework02.util.StrUtil.randomString;

@RunWith(Parameterized.class)
public class StrUtilTest {
    @Parameterized.Parameters(name = "{index}: {0}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {0},
                {1},
                {2},
                {3},
                {5},
                {100},
                {1000},
        });
    }

    private final static double MAX_REPEAT_PROPORTION = .01;

    private final int length;

    public StrUtilTest(int length) {
        this.length = length;
    }

    @Test
    public void TestLength() {
        assertEquals(length, randomString(length).length());
    }

    @Test
    public void TestUniqueness() {
        int count = length;
        Stream<String> s = Stream.generate(()->randomString(length)).limit(count);
        int maxRepeats = (int)(count * MAX_REPEAT_PROPORTION);
        long uniqueValuesCount = s.distinct().count();

        assertTrue(uniqueValuesCount >= count - maxRepeats);
    }
}
