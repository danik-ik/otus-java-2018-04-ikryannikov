package ru.otus.danik_ik.homework08;

import com.google.gson.Gson;
import ru.otus.danik_ik.homework08.jzon.Jzon;

import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) {
        System.out.println(new Jzon().getJzonType("12345"));
        System.out.println(new Jzon().getJzonType(new Object()));
        System.out.println(new Jzon().getJzonType(new HashMap<String,String>()));
        System.out.println(new Jzon().getJzonType(new int[3]));

        Map<String, Integer> map = new HashMap<>();
        map.put("First", 1);
        map.put("Second", 2);
        map.put("Third", 3);
        System.out.println(new Jzon().toJson(map));
        System.out.println(new Jzon().toJson(12345));
        System.out.println(new Jzon().toJson("1234\"5"));
        System.out.println(new Jzon().toJson(new String[]{"123", "456", "789"}));
        System.out.println(new Jzon().toJson(new Integer[]{123, 456, 789}));
        System.out.println(new Jzon().toJson(new Map[]{map, map}));

        System.out.println();
        TestObj1 to = new TestObj1();
        to.publicString = "publicString";
        to.setPrivateString("privateString");
        to.intValue = 98765;
        to.intArray = new int[]{9,8,7,6,5};
        to.transientString = "transientString";
        System.out.println("=== Jzon ====");
        System.out.println(new Jzon().toJson(to));
        System.out.println("=== Gson ====");
        System.out.println(new Gson().toJson(to));

        System.out.println();
        System.out.println("=============================");
        System.out.println("Тут, вообще-то, баловство одно. на самом деле всё в тестах");

    }
}
