package ru.otus.danik_ik.homework03.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Queue;

import static org.junit.Assert.assertEquals;
import static ru.otus.danik_ik.homework02.util.StrUtil.randomString;

@RunWith(Parameterized.class)
public class TestMyQueueSequence {
    private int capacity;
    private int count;

    String[] source;
    String[] target;
    Queue<String> queue;


    @Parameterized.Parameters(name = "{index}) capacity: {0}; count: {1}")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {1, 1},
                {1, 10},
                {10, 100},
                {10, 999},
        });
    }

    public TestMyQueueSequence(int capacity, int count) {
        this.capacity = capacity;
        this.count = count;

        queue = new MyQueue<>(capacity);
        source = new String[count];
        target = new String[count];

        for (int i = 0; i < count; i++) {
            source[i] = randomString(100);
        }
    }

    @Test
    public void oneForOne() {
        for (int i = 0; i < count; i++) {
            queue.add(source[i]);
            target[i] = queue.remove();
        }

        checkSequencesAreEquals();
    }

    @Test
    public void byPortions() {
        int base = 0;
        int portion = capacity - 1;
        if (portion < 1) portion = 1;
        do {
            addPortion(base, portion);
            removePortion(base, portion);
            base += portion;
            if (base + portion > count) portion = count - base;
        } while (base < count);

        checkSequencesAreEquals();
    }

    private void addPortion(int base, int portion) {
        for (int i = 0; i < portion; i++)
            queue.add(source[base + i]);
    }

    private void removePortion(int base, int portion) {
        for (int i = 0; i < portion; i++)
            target[base + i] = queue.remove();
    }

    private void checkSequencesAreEquals() {
        for (int i = 0; i < count; i++) {
            assertEquals(source[i], target[i]);
        }
    }
}
