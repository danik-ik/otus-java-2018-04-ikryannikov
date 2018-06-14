package ru.otus.danik_ik.homework08.jzon;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.danik_ik.homework08.TestObj1;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotNull;

public class JzonTest {
    private TestObj1 src;
    private String json;
    @Before
    public void before() {
        src = new TestObj1();
        src.publicString = "publicString";
        src.setPrivateString("privateString");
        src.intValue = 98765;
        src.intArray = new int[]{9,8,7,6,5};
        src.transientString = "transientString";
        json = new Jzon().toJson(src);
        System.out.println(json);
    }
    
    @Test
    public void NoErrorsInFormat() {
        new Gson().fromJson(json, TestObj1.class);
    }

    @Test
    public void PublicString() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.publicString, dst.publicString);
    }

    @Test
    public void PrivateStringGetter() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.getPrivateString(), dst.getPrivateString());
    }

    @Test
    public void Int() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.intValue, dst.intValue);
    }

    @Test
    public void IntArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.intArray);
        assertEquals(src.intArray.length, dst.intArray.length);
        for (int i = 0; i < src.intArray.length; i++) 
            assertEquals(src.intArray[i], dst.intArray[i]);
    }

    @Test
    public void Transient() {
        assertFalse(json.contains("transient"));
    }

    @Test(expected = JzonException.class)
    public void cycling() {
        new Jzon().toJson(new Cycled());
    }

    private class Cycled {
        public Object infinity = this;
    }
}
