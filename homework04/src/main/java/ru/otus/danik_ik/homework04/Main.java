package ru.otus.danik_ik.homework04;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.LinkedList;
import java.util.List;
import java.util.Random;

import static java.lang.Thread.sleep;

public class Main
{
    public static final int DELAY_MILLIS = 500;
    public static final int BIG_OBJECT_SIZE = 20_000;

    public static void main( String[] args ) throws InterruptedException {
        new Main().execute();
    }

    private void execute() throws InterruptedException {
        int leakCountAtStep = 10;
        loop(leakCountAtStep);
    }

    long startTime = System.nanoTime();
    List<long[]> list = new LinkedList<>();

    @SuppressWarnings("InfiniteLoopStatement")
    private void loop(int leakCountAtStep) throws InterruptedException {
        while (true) {
            for (int i = 0; i < leakCountAtStep; i++) leak();
            printStat();
            sleep(DELAY_MILLIS);
        }
    }

    private void printStat() {
        Runtime rt = Runtime.getRuntime();
        List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
        System.out.println("============================");
        System.out.printf("Time: %d s\n", (int)((System.nanoTime() - startTime) * .000_000_001));
        for (GarbageCollectorMXBean gc : gcs) {
            System.out.printf("Name: %s; count: %s; time: %s\n", gc.getName(), gc.getCollectionCount(),
                    gc.getCollectionTime());
        }
        System.out.printf("Free memory: %d/%d MiB\n ", rt.freeMemory()/1024/1024, rt.totalMemory()/1024/1024);
    }

    private void leak() {
        list.add(new long[BIG_OBJECT_SIZE]);
        list.add(new long[BIG_OBJECT_SIZE]);
        list.add(new long[BIG_OBJECT_SIZE]);
        list.remove(0);
        list.remove(0);
    }

}
