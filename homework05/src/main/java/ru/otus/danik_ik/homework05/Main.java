package ru.otus.danik_ik.homework05;

import ru.otus.danik_ik.homework05.testo.Engine;

public class Main
{
    public static void main( String[] args ) {
        if (args.length != 1) {
            System.out.println("В качестве параметра надо указать имя класса или пакета");
            return;
        }
        Engine.execute(args[0]);
    }
}
