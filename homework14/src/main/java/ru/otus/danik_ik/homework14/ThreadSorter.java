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
        List<Future<int[]>> result = new LinkedList<>();

        int i = 0;
        while (i + 1 < source.size()) {
            // сливаем предварительно отсортированные фрагменты попарно
            result.add(getMergedFuture(source.get(i), source.get(i + 1)));
            i = i + 2;
        }
        // пробрасываем на следующий уровень непарный фрагмент (если есть)
        if (i < source.size()) result.add(source.get(i));

        return result;
    }

    private Future<int[]> getMergedFuture(Future<int[]> future1, Future<int[]> future2) {
        return service.submit( () -> {
            while (!future1.isDone()) yield();
            while (!future2.isDone()) yield();
            int[] a = future1.get();
            int[] b = future2.get();
            return mergeOrderedArrays(a, b);
        });
    }

    private int[] mergeOrderedArrays(int[] a, int[] b) {
        int[] result = new int[a.length + b.length];
        int rPos = 0;
        int aPos = 0;
        int bPos = 0;

        // Мержим, пока не кончится один из массивов
        while (aPos < a.length && bPos < b.length) {
            int aVal = a[aPos];
            int bVal = b[bPos];
            if (aVal <= bVal) {
                result[rPos] = aVal;
                aPos++;
            } else {
                result[rPos] = bVal;
                bPos++;
            }
            rPos++;
        }
        // остаток второго массива копируем в остаток результата
        copyTailIfExists(a, aPos, result, rPos);
        copyTailIfExists(b, bPos, result, rPos);

        return result;
    }

    private void copyTailIfExists(int[] src, int srcPos, int[] dst, int dstPos) {
        if (srcPos < src.length)
            System.arraycopy(src, srcPos, dst, dstPos, src.length - srcPos);
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
            System.arraycopy(source, startIndex, result, 0, result.length);
            Arrays.sort(result);
            return result;
        });
    }

    @Override
    public void close() throws Exception {
        service.shutdown();
    }
}
