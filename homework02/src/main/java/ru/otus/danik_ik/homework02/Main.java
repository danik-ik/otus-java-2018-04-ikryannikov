package ru.otus.danik_ik.homework02;

import ru.otus.danik_ik.homework02.measurer.ObjectSizeCalculator;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import static ru.otus.danik_ik.homework02.util.StrUtil.randomString;

public class Main
{
    /*
        запускать jar сборки, указывая его же в параметре javaagent (см. run.cmd)
     */
    public static void main( String[] args ) {

        testObject("", "Empty string");
        testObject(randomString(100), "Random 100-char string");
        testObject(randomString(1000), "Random 1000-char string");
        testObject(new String[]{}, "Array of 0 strings");
        testObject(new String[]{"", "", ""}, "Array of 3 empty strings");
        testObject(new String[]{".", ".", "."}, "Array of 3 equal one-char string consts");
        testObject(new String[]{new String("."), new String("."), new String(".")},
                "Array of 3 new one-char strings");
        testObject(new String[]{"1", "2", "3"}, "Array of 3 different one-char string consts");

        testObject(new int[0], "int[0]");
        testObject(new int[100], "int[100]");
        testObject(new Integer[0], "Integer[0]");
        testObject(new Integer[100], "Integer[100] non-initialized");
        testObject(new long[0], "long[0]");
        testObject(new long[100], "long[100]");
        testObject(new Long[0], "Long[0]");
        testObject(new Long[100], "Long[100]");


        multiTest(new ArrayList(), "ArrayList of 100 empty strings", list->list.add(""), 100);
        multiTest(new ArrayList(), "ArrayList of 100 random 100-char strings",
                list->list.add(randomString(100)), 100);
    }

    private static void testObject(Object object, String comment) {
        printHeader(comment);
        printSize(object);
    }

    private static void multiTest(List object, String comment, Consumer<List> modifier, int count) {
        testObject(object, comment);
        for (int i = 0; i < count; i++) {
            modifier.accept(object);
            printSize(object);
        }
    }

    private static void printSize(Object object) {
        System.out.println(ObjectSizeCalculator.calcSize(object));
    }

    private static void printHeader(String comment) {
        System.out.println();
        System.out.println("====================================================");
        System.out.println(comment);
        System.out.println("====================================================");
    }
}
