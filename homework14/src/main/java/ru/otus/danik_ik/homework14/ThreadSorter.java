package ru.otus.danik_ik.homework14;

import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import static java.lang.Thread.yield;

public class ThreadSorter implements AutoCloseable{
    private final int minQuantity;
    private final ExecutorService service;


    public ThreadSorter(int threadCount, int minQuantity) {
        this.minQuantity = minQuantity;
        service = Executors.newFixedThreadPool(threadCount);
    }

    public int[] sort(int[] source) {
        if (source.length == 0) return new int[0];
        List<Future<int[]>> nextLayer;

        Future<int[]> resultFuture = getSortFuture(source);

        while (!resultFuture.isDone()) yield();
        try {
            return resultFuture.get();
        } catch (InterruptedException | ExecutionException e) {
            throw new RuntimeException(e);
        }
    }

    public Future<int[]> getSortFuture(int[] source) {
        List<Future<int[]>> nextLayer;
        nextLayer = splitToSortedFragments(source, minQuantity);

        while (nextLayer.size() > 1) {
            nextLayer = mergePairs(nextLayer);
        }

        return nextLayer.get(0);
    }

    public static int[] sort(int[] source, int threadCount, int minQuantity) {
        try(ThreadSorter sorter = new ThreadSorter(threadCount, minQuantity)) {
            return sorter.sort(source);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private List<Future<int[]>> mergePairs(List<Future<int[]>> source) {
        return null; // TODO: 25.07.2018 <<<<<<<<<<<<<<<<<<<<<
        // TODO: 25.07.2018 мержить по парам. Непарные пробрасывать насквозняк
    }

    private List<Future<int[]>> splitToSortedFragments(int[] source, int minQuantity) {
        List<Future<int[]>> result = new LinkedList<>();
        int i = 0;
        while (i < source.length) {
            result.add(getSortedSubarrayFuture(source, i, Math.min(i + minQuantity, source.length)));
            i = i + minQuantity;
        }
        return result;
    }

    private Future<int[]> getSortedSubarrayFuture(int[] source, int startIndex, int stopIndex) {
        return service.submit(() -> {
            int[] result = new int[stopIndex - startIndex];
            Arrays.sort(result);
            return result;
        });
    }

    @Override
    public void close() throws Exception {
        service.shutdown();
    }
}
