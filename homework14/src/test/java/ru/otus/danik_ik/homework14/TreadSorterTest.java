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
                // размер массива, количество потоков, размер предварительно отсортированных фрагментов
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
                {1000000, 6, 33},
                {1000000, 1, 100},
                {1000000, 2, 100},
                {1000000, 3, 100},
                {1000000, 4, 100},
                {1000000, 5, 100},
                {1000000, 10, 100},
                {1000000, 100, 100},
                {10000, 1, 1},
                {10000, 2, 1},
                {10000, 3, 1},
                {1000000, 2, 10000},
                {1000000, 3, 10000},
                {1000000, 4, 10000},
                {1000000, 2, 100000},
                {1000000, 3, 100000},
                {1000000, 4, 100000},
                {1000000, 2, 125000},
                {1000000, 3, 125000},
                {1000000, 4, 125000},
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
        long startTime;
        startTime = System.nanoTime();
        int[] result = ThreadSorter.sort(src, threadCount, minQuantity);
        long stopTime;
        stopTime = System.nanoTime();
        System.out.println("TreadSorter sort duration: " + ((stopTime - startTime) *.000001D) + "ms");

        startTime = System.nanoTime();
        Arrays.sort(src);
        stopTime = System.nanoTime();
        System.out.println("Arrays sort duration: " + ((stopTime - startTime) *.000001D) + "ms");
        assertArrayEquals(src, result);
    }
}
