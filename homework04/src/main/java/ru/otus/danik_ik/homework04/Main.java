package ru.otus.danik_ik.homework04;

import javax.management.MBeanServer;
import java.lang.management.GarbageCollectorMXBean;
import java.lang.management.ManagementFactory;
import java.util.List;

public class Main
{
    public static void main( String[] args ) {
        new Main().loop();
    }

    @SuppressWarnings("InfiniteLoopStatement")
    private void loop() {
        while (true) {
            int[] a = new int[1_000_000];
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
            List<GarbageCollectorMXBean> gcs = ManagementFactory.getGarbageCollectorMXBeans();
            System.out.println("============================");
            for (GarbageCollectorMXBean gc : gcs) {
                System.out.printf("Name: %s; count: %s; time: %s\n", gc.getName(), gc.getCollectionCount(), gc.getCollectionTime());
            }
        }
    }
}
