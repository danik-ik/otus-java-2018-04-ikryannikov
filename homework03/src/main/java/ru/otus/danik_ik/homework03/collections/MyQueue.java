package ru.otus.danik_ik.homework03.collections;

import java.util.Collection;
import java.util.Iterator;
import java.util.Queue;

public class MyQueue<E> implements Queue<E> {

    private static final String ERR_MSG_MIN_SIZE = "Размер очереди не может быть меньше 1";
    private int capacity;
    private int arraySize;
    /*  Выбрана реализация без учёта рамера (считается по требованию). Чтобы отличить пустое и полное
        состояния, в массиве будет лишний элемент -- "распорка" */
    private int head = 0; // указатель на индекс "где взять"
    private int tail = 0; // указатель на индекс "где положить" (всегда незаполненный! при заполнении
                          // размера указывает на "распорку")

    E[] elements;

    public MyQueue(int capacity) {
        if (capacity < 1) throw new IllegalArgumentException(ERR_MSG_MIN_SIZE);

        this.capacity = capacity;
        arraySize = capacity + 1;

        elements = (E[]) new Object[arraySize];
    }

    @Override
    public int size() {
        return (phantomTail() - head);
    }

    private int phantomTail() {
        return tail >= head ? tail : tail + arraySize;
    }

    @Override
    public boolean isEmpty() {
        return head == tail;
    }

    @Override
    public boolean contains(Object o) {
        return false;
    }

    @Override
    public Iterator<E> iterator() {
        return null;
    }

    @Override
    public Object[] toArray() {
        return new Object[0];
    }

    @Override
    public <T> T[] toArray(T[] a) {
        return null;
    }

    @Override
    public boolean add(E e) {
        return false;
    }

    @Override
    public boolean remove(Object o) {
        return false;
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        return false;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        return false;
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        return false;
    }

    @Override
    public void clear() {

    }

    @Override
    public boolean offer(E e) {
        return false;
    }

    @Override
    public E remove() {
        return null;
    }

    @Override
    public E poll() {
        return null;
    }

    @Override
    public E element() {
        return null;
    }

    @Override
    public E peek() {
        return null;
    }
}
