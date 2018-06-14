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
        src.shortValue = 9865;
        src.longValue = 98765;
        src.floatValue = 987.65F;
        src.doubleValue = 98.765;
        src.booleanValue = true;
        src.charValue = 'Ё';
        src.byteValue = -1;
        src.intArray = new int[]{9,8,7,6,5};
        src.shortArray = new short[]{11,9,7,7,6,5};
        src.longArray = new long[]{3,14,15,92,6};
        src.floatArray = new float[]{.3F,.14F,.15F,.92F,.6F};
        src.doubleArray = new double[]{.3,.14,.15,.92,.6};
        src.booleanArray = new boolean[]{false, false, false, true, true, true, false, false, false};
        src.charArray = new char[]{'ф','ы','в','А','п','р','О','л','д','ж','э',};
        src.byteArray = new byte[]{1,2,3,4,5};

        src.transientString = "transientString";
        json = new Jzon().toJson(src);
        System.out.println(json);
    }
    
    @Test
    public void noErrorsInFormat() {
        new Gson().fromJson(json, TestObj1.class);
    }

    @Test
    public void publicString() {
        // Геттер имеет приоритет при чтении значения
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.getPublicString(), dst.publicString);
    }

    @Test
    public void privateStringGetter() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.getPrivateString(), dst.getPrivateString());
    }

    @Test
    public void intValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.intValue, dst.intValue);
    }

    @Test
    public void shortValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.shortValue, dst.shortValue);
    }

    @Test
    public void longValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.longValue, dst.longValue);
    }

    @Test
    public void floatValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.floatValue, dst.floatValue, .0000001);
    }

    @Test
    public void doubleValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.doubleValue, dst.doubleValue, .0000001);
    }

    @Test
    public void booleanValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.booleanValue, dst.booleanValue);
    }

    @Test
    public void charValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.charValue, dst.charValue);
    }

    @Test
    public void byteValue() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEquals(src.byteValue, dst.byteValue);
    }

    @Test
    public void intArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.intArray);
        assertEquals(src.intArray.length, dst.intArray.length);
        for (int i = 0; i < src.intArray.length; i++) 
            assertEquals(src.intArray[i], dst.intArray[i]);
    }

    @Test
    public void shortArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.shortArray);
        assertEquals(src.shortArray.length, dst.shortArray.length);
        for (int i = 0; i < src.shortArray.length; i++)
            assertEquals(src.shortArray[i], dst.shortArray[i]);
    }

    @Test
    public void longArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.longArray);
        assertEquals(src.longArray.length, dst.longArray.length);
        for (int i = 0; i < src.longArray.length; i++)
            assertEquals(src.longArray[i], dst.longArray[i]);
    }

    @Test
    public void floatArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.floatArray);
        assertEquals(src.floatArray.length, dst.floatArray.length);
        for (int i = 0; i < src.floatArray.length; i++)
            assertEquals(src.floatArray[i], dst.floatArray[i], .0001F);
    }

    @Test
    public void doubleArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.doubleArray);
        assertEquals(src.doubleArray.length, dst.doubleArray.length);
        for (int i = 0; i < src.doubleArray.length; i++)
            assertEquals(src.doubleArray[i], dst.doubleArray[i], .0001F);
    }

    @Test
    public void booleanArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.booleanArray);
        assertEquals(src.booleanArray.length, dst.booleanArray.length);
        for (int i = 0; i < src.booleanArray.length; i++)
            assertEquals(src.booleanArray[i], dst.booleanArray[i]);
    }

    @Test
    public void charArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.charArray);
        assertEquals(src.charArray.length, dst.charArray.length);
        for (int i = 0; i < src.charArray.length; i++)
            assertEquals(src.charArray[i], dst.charArray[i]);
    }

    @Test
    public void byteArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertNotNull(dst.byteArray);
        assertEquals(src.byteArray.length, dst.byteArray.length);
        for (int i = 0; i < src.byteArray.length; i++)
            assertEquals(src.byteArray[i], dst.byteArray[i]);
    }

    @Test
    public void transientValue() {
        assertFalse(json.contains("transientString"));
    }

    @Test
    public void nullReference() {
        assertFalse(json.contains("nullReverence"));
    }

    @Test(expected = JzonException.class)
    public void cycling() {
        new Jzon().toJson(new Cycled());
    }

    private class Cycled {
        public Object infinity = this;
    }
}
