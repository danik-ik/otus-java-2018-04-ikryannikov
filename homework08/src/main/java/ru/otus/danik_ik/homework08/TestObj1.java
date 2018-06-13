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
        return publicString;
    }
    
    public int[] intArray;
    public int intValue;
    
    transient public String transientString; 
    
    public final Object nullReverence = null; 
}
