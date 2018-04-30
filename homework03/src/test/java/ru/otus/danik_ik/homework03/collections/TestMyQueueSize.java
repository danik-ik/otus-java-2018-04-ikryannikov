package ru.otus.danik_ik.homework03.collections;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.Arrays;
import java.util.Queue;

import static org.junit.Assert.assertEquals;

@RunWith(Parameterized.class)
public class TestMyQueueSize {
    private int capacity;
    private String operations;
    private int targetSize;

    @Parameterized.Parameters(name = "{index}: (capacity: {0}; operations: «{1}»; size: {2})")
    public static Iterable<Object[]> dataForTest() {
        return Arrays.asList(new Object[][]{
                {1, "", 0},
                {1, "+", 1},
                {1, "+-", 0},
                {1, "+-+", 1},
                {1, "+-+-", 0},
                {1, "+-+-+", 1},
                {10, "+++", 3},
                {10, "++++++++++", 10},
                {10, "++++++--+++++", 9},
                {10, "++++++--++++++", 10},
        });
    }
    public TestMyQueueSize(int capacity, String operations, int targetSize) {
        this.capacity = capacity;
        this.operations = operations;
        this.targetSize = targetSize;
    }

    @Test
    public void testSize() {
        Queue<Object> q = new MyQueue<>(capacity);
        doOperations(q, operations);
        assertEquals(targetSize, q.size());
    }

    private void doOperations(Queue<Object> q, String operations) {
        for (char command : operations.toCharArray())
            switch (command) {
                case '+': {
                    q.add(null);
                    break;
                }
                case '-': {
                    q.remove();
                }
            }
    }
}
