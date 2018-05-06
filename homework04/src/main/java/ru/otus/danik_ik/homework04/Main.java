package ru.otus.danik_ik.homework04;

import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

import static java.lang.Thread.sleep;

public class Main
{
    public static void main( String[] args ) throws InterruptedException {
        new Main().loop();
    }

    long startTime = System.nanoTime();

    @SuppressWarnings("InfiniteLoopStatement")
    private void loop() throws InterruptedException {
        while (true) {
            int[] a = new int[1_000_000];
            printStat();
            sleep(10);
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

}
