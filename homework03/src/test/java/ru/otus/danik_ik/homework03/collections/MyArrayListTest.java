package ru.otus.danik_ik.homework03.collections;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

import static junit.framework.TestCase.*;
//import static org.junit.Assert.assertEquals;

public class MyArrayListTest {
    private List<String> list;
    @Before
    public void setup() {
        list = new MyArrayList<>();
    }
    
    @Test
    public void sizeWhenEmpty() {
        assertEquals(0, list.size());
    }
    
    @Test
    public void emptyTrue() {
        assertTrue(list.isEmpty());
    }
    
    
}
