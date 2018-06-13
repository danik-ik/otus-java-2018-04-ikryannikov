package ru.otus.danik_ik.homework08;

import com.google.gson.Gson;
import org.json.simple.JSONArray;
import org.json.simple.JSONAware;
import org.json.simple.JSONObject;
import ru.otus.danik_ik.homework08.jzon.Jzon;

import java.util.HashMap;
import java.util.Map;

public class Main
{
    public static void main( String[] args ) {
        Object it = "1234\"5";

//        Jzon jzon = new Jzon();
//        System.out.println(jzon.serialize(it));
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
        System.out.println(new Jzon().serialize(map));
        System.out.println(new Jzon().serialize(12345));
        System.out.println(new Jzon().serialize("1234\"5"));
        System.out.println(new Jzon().serialize(new String[]{"123", "456", "789"}));
        System.out.println(new Jzon().serialize(new Integer[]{123, 456, 789}));
        System.out.println(new Jzon().serialize(new Map[]{map, map}));
    }
}
