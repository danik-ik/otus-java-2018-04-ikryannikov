package ru.otus.danik_ik.homework08;

public class TestObj1 {
    public String publicString;
    private String privateString;

    public String getPrivateString() {
        return privateString;
    }

    public void setPrivateString(String privateString) {
        this.privateString = privateString;
    }

    public String getPublicString() {
        return publicString.toUpperCase();
    }
    
    public int[] intArray;
    public short[] shortArray;
    public long[] longArray;
    public float[] floatArray;
    public double[] doubleArray;
    public boolean[] booleanArray;
    public char[] charArray;
    public byte[] byteArray;

    public int intValue;
    public short shortValue;
    public long longValue;
    public float floatValue;
    public double doubleValue;
    public boolean booleanValue;
    public char charValue;
    public byte byteValue;

    transient public String transientString;
    
    public final Object nullReverence = null; 
}
