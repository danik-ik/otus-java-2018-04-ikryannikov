package ru.otus.danik_ik.homework14;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.concurrent.ThreadLocalRandom;

import static org.junit.Assert.assertArrayEquals;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

/** Simple parametrized test. */
@RunWith(Parameterized.class)
public class TreadSorterTest
{
    /** Data for test. */
    @Parameterized.Parameters(name = "{index}: (size: {0}; threadCount: {1}; minQuamtity: {2})")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
             {0, 1, 1},
             {1, 1, 1},
             {3, 1, 1},
             {10, 1, 10},
             {20, 1, 10},
             {0, 3, 1},
             {1, 3, 1},
             {10, 9, 10},
             {20, 9, 10},
             {10000, 6, 33},
        });
    }

    private final int arraySize;
    private final int threadCount;
    private final int minQuantity;
    private final int[] src;

    public TreadSorterTest(int arraySize, int threadCount, int minQuantity) {
        this.arraySize = arraySize;
        this.threadCount = threadCount;
        this.minQuantity = minQuantity;
        src = new int[arraySize];
    }

    @Before
    public void setup() {
        for (int i = 0; i < arraySize; i++) {
            src[i] = getRandomValue();
        }
    }

    private int getRandomValue() {
        return ThreadLocalRandom.current().nextInt(Integer.MIN_VALUE, Integer.MAX_VALUE);
    }


    @Test
    public void testIt() throws Exception {
        int[] result = ThreadSorter.sort(src, threadCount, minQuantity);
        Arrays.sort(src);
        assertArrayEquals(src, result);
    }
}
