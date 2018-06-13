package ru.otus.danik_ik.homework08;

import com.google.gson.Gson;
import ru.otus.danik_ik.homework08.jzon.Jzon;

import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) {
        Object it = "1234\"5";

//        Jzon jzon = new Jzon();
//        System.out.println(jzon.toJson(it));
//
//        Gson gson = new Gson();
//        System.out.println(gson.toJson(it));
//
//        JSONAware a = new JSONArray();
//        for (int i: new int[]{1,2,4,8}) {
//            ((JSONArray) a).add(i);
//        }
//        JSONObject o = new JSONObject();
//        o.put("aaa", a);
//        o.put("bbb", a);
//        System.out.println(o.toJSONString());

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

    }
}
