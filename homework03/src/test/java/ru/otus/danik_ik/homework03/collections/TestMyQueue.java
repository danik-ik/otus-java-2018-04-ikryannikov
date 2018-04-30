package ru.otus.danik_ik.homework03.collections;

import org.junit.Test;
import static org.junit.Assert.*;

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

}

