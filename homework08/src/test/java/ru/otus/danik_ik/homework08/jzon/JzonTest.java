package ru.otus.danik_ik.homework08.jzon;

import com.google.gson.Gson;
import org.junit.Before;
import org.junit.Test;
import ru.otus.danik_ik.homework08.TestObj1;

import java.lang.reflect.Array;

import static org.junit.Assert.*;

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
        src.objectsArray = new Object[]{new O1(), new O2()};
        src.characterArray = new Character[]{'a','b','c','x','y','Z'};

        src.transientString = "transientString";
        json = new Jzon().toJson(src);
        System.out.println("=== Jzon ====");
        System.out.println(json);
        System.out.println("=== Gson ====");
        System.out.println(new Gson().toJson(src));
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
        assertEqualsArrays(src.intArray, dst.intArray);
    }

    @Test
    public void shortArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.shortArray, dst.shortArray);
    }

    @Test
    public void longArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.longArray, dst.longArray);
    }

    @Test
    public void floatArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.floatArray, dst.floatArray);
    }

    @Test
    public void doubleArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.doubleArray, dst.doubleArray);
    }

    @Test
    public void booleanArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.booleanArray, dst.booleanArray);
    }

    @Test
    public void charArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.byteArray, dst.byteArray);
    }

    @Test
    public void byteArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.charArray, dst.charArray);
    }

    @Test
    public void objectsArray() {
        assertTrue(json.contains("\"objectsArray\":["));
        assertTrue(json.contains("\"name\":\"O1\""));
        assertTrue(json.contains("\"name\":\"O2\""));
        assertTrue(json.contains("\"longName\":\"Ooooooooooo2\""));
    }

    @Test
    public void charsAreQuoted() {
        assertTrue(json.contains("\"charValue\":\"Ё\""));
    }

    @Test
    public void charsAreQuotedInArray() {
        assertTrue(json.contains("\"characterArray\":[\"a\","));
    }

    @Test
    public void characterArray() {
        TestObj1 dst = new Gson().fromJson(json, TestObj1.class);
        assertEqualsArrays(src.characterArray, dst.characterArray);
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

    private void assertEqualsArrays(Object expected, Object actual) {
        if ( !(  isArray(expected) &&  isArray(actual) ) )
            throw new IllegalArgumentException("Аргументы метода assertEqualsArrays " +
                    "должны быть массивами");

        assertEquals(Array.getLength(expected), Array.getLength(actual));
        for(int i = 0, length = Array.getLength(expected); i < length; ++i)
            assertEquals(Array.get(expected, i), Array.get(actual, i));
    }

    private boolean isArray(Object it) {
        return it.getClass().getComponentType() != null;
    }

    private class O1 {
        public String name = "O1";
    }

    private class O2 {
        public String name = "O2";
        public String longName = "Ooooooooooo2";
    }

}
