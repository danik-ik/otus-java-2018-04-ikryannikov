package ru.otus.danik_ik.homework02;

import ru.otus.danik_ik.homework02.measurer.ObjectSizeCalculator;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Hello world!
 *
 */
public class Main
{
    /*
        запускать jar сборки, указывая его же в параметре javaagent (см. run.cmd)
     */
    public static void main( String[] args ) {
        System.out.println("Empty string");
        System.out.println(ObjectSizeCalculator.calcSize(""));
        System.out.println();

        String[] ss = {"rgadfgadf","dasdasdgasdfgasdasda", "uffoufff"};
        System.out.println();
        System.out.println("Array of 3 strings");
        System.out.println(ObjectSizeCalculator.calcSize(ss));

        System.out.println("ArrayList");
        List<String> list = new ArrayList<>();
        list.addAll(Arrays.asList(ss));
        System.out.println(ObjectSizeCalculator.calcSize(list));
        for (int i = 0; i < 100; i++) {
            list.add(new String("gk;sdfng;sfdg;bs;kbaf"));
            System.out.println(ObjectSizeCalculator.calcSize(list));
        }
    }
}
