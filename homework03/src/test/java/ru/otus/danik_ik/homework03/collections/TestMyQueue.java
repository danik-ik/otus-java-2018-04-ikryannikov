package ru.otus.danik_ik.homework03.collections;

import org.junit.Test;
import static org.junit.Assert.*;

import java.util.Arrays;
import java.util.Iterator;
import java.util.NoSuchElementException;
import java.util.Queue;

public class TestMyQueue {
    @Test (expected = IllegalArgumentException.class)
    public void throwsOnCapacity0() {
        new MyQueue<String>(0);
    }

    @Test (expected = IllegalArgumentException.class)
    public void throwsOnCapacityNegative() {
        new MyQueue<String>(-10);
    }

    @Test
    public void peekReturnsNullOnEmpty() {
        Queue<String> q = new MyQueue<>(1);
        assertEquals(null, q.peek());
    }

    @Test (expected = NoSuchElementException.class)
    public void elementTrowsOnEmpty() {
        Queue<String> q = new MyQueue<>(1);
        q.element();
    }

    @Test
    public void pollReturnsNullOnEmpty() {
        Queue<String> q = new MyQueue<>(1);
        assertEquals(null, q.poll());
    }

    @Test (expected = NoSuchElementException.class)
    public void removeTrowsOnEmpty() {
        Queue<String> q = new MyQueue<>(1);
        q.remove();
    }

    @Test
    public void addElementRemove() {
        Queue<String> q = new MyQueue<>(1);
        assertTrue(q.add("123"));
        assertEquals("123", q.element());
        assertEquals("123", q.remove());
    }

    @Test
    public void offerPeekPoll() {
        Queue<String> q = new MyQueue<>(1);
        assertTrue(q.offer("456"));
        assertEquals("456", q.peek());
        assertEquals("456", q.poll());
    }

    @Test (expected = IllegalStateException.class)
    public void addAllThrowsOnOverflow() {
        Queue<String> q = new MyQueue<>(2);
        q.addAll(Arrays.asList(new String[]{"1","2","3"}));
    }

    @Test
    public void addAll() {
        Queue<String> q = new MyQueue<>(3);
        q.addAll(Arrays.asList(new String[]{"1","2","3"}));

        assertEquals("1", q.remove());
        assertEquals("2", q.remove());
        assertEquals("3", q.remove());
    }

    @Test
    public void toArrayInbounds() {
        Queue<String> q = new MyQueue<>(3);
        String[] source = new String[]{"1","2","3"};

        q.addAll(Arrays.asList(source));

        Object[] target = q.toArray();

        assertEquals(3, target.length);

        assertEquals("1", (String)target[0]);
        assertEquals("2", (String)target[1]);
        assertEquals("3", (String)target[2]);
    }

    @Test
    public void toArrayOverbound() {
        Queue<String> q = new MyQueue<>(3);
        String[] source = new String[]{"1","2","3"};
        q.add(null);
        q.remove();
        q.add(null);
        q.remove();

        q.addAll(Arrays.asList(source));

        Object[] target = q.toArray();

        assertEquals(3, target.length);

        assertEquals("1", (String)target[0]);
        assertEquals("2", (String)target[1]);
        assertEquals("3", (String)target[2]);
    }

    @Test
    public void toArrayEmpty() {
        Queue<String> q = new MyQueue<>(3);
        assertEquals(0, q.toArray().length);
    }

    @Test
    public void toArrayTyped() {
        // будет создан новый массив
        Queue<String> q = new MyQueue<>(3);
        String[] source = new String[]{"1","2","3"};

        q.addAll(Arrays.asList(source));

        String[] target = q.toArray(new String[0]);

        assertEquals(3, target.length);

        assertEquals("1", target[0]);
        assertEquals("2", target[1]);
        assertEquals("3", target[2]);
    }

    @Test
    public void toArrayTypedOverBounds() {
        // будет создан новый массив
        Queue<String> q = new MyQueue<>(3);
        String[] source = new String[]{"1","2","3"};

        q.add(null);
        q.remove();
        q.add(null);
        q.remove();

        q.addAll(Arrays.asList(source));

        String[] target = q.toArray(new String[0]);

        assertEquals(3, target.length);

        assertEquals("1", target[0]);
        assertEquals("2", target[1]);
        assertEquals("3", target[2]);
    }

    @Test
    public void toArrayTypedSrc() {
        // будет использован исходный массив
        Queue<String> q = new MyQueue<>(3);
        String[] source = new String[]{"1","2","3"};

        q.addAll(Arrays.asList(source));

        String[] prototype = new String[4];

        String[] target = q.toArray(prototype);

        assertTrue(prototype == target);

        assertEquals("1", target[0]);
        assertEquals("2", target[1]);
        assertEquals("3", target[2]);
        assertEquals(null, target[3]);
    }

    @Test
    public void iteratorTest() {
        Queue<String> q = new MyQueue<>(4);
        Iterator<String> i = q.iterator();

        assertFalse(i.hasNext());
        testIteration(q, i,"1");
        testIteration(q, i,"2");
        testIteration(q, i,"3");
        testIteration(q, i,"4");
        testIteration(q, i,"5");
        testIteration(q, i,"6");
        testIteration(q, i,"7");
        testIteration(q, i,"8");
        testIteration(q, i,"9");

    }

    private void testIteration(Queue<String> q, Iterator<String> i, String s) {
        assertFalse(i.hasNext());
        q.add(s);
        q.add(s+s);
        assertTrue(i.hasNext());
        assertEquals(s, i.next());
        assertEquals(s + s, i.next());
        assertFalse(i.hasNext());
        q.remove();
        q.remove();
        assertFalse(i.hasNext());
    }
}

