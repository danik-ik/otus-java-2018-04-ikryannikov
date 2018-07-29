package ru.otus.danik_ik.homework14;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

import static java.lang.Thread.yield;

public class Main
{
    public static void main( String[] args ) {
        ExecutorService service = Executors.newFixedThreadPool(2);
        try {
            for(int i = 0; i < 10; i++)
            {
                int finalI = i;
                service.submit(() -> {
                    System.out.println(finalI);
                    yield();
                });
            }
        } finally {
            service.shutdown();
        }
    }
}
