package ru.otus.danik_ik.homework05;

import ru.otus.danik_ik.homework05.testo.Engine;
import ru.otus.danik_ik.homework05.testo.TargetNotFoundException;

public class Main
{
    public static void main( String[] args ) {
        if (args.length != 1) {
            System.out.println("В качестве параметра надо указать имя класса или пакета");
            return;
        }
        try {
            Engine.execute(args[0]);
        } catch (TargetNotFoundException targetNotFoundException) {
            System.out.println(targetNotFoundException.getMessage());
        }
    }
}
