package ru.otus.danik_ik.homework03.collections;

import java.util.*;

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
        throw new UnsupportedOperationException();
    }

    @Override
    public Iterator<E> iterator() {
        return new MyQueueIterator();
    }

    @Override
    public Object[] toArray() {
        return toNewArray(Object[].class);
    }

    @Override
    public <T> T[] toArray(T[] a) {
        int size = size();
        if (size > a.length)
            return toNewArray((Class<T[]>) a.getClass());

        final T[] result = a;
        ToExistsArray(result);
        if (size < result.length) result[size] = null;

        return result;
    }

    private <T> T[] toNewArray(Class<T[]> destClass) {
        final Object[] in = elements;
        final T[] out;

        int endIndex = tail >= head ? tail : arraySize;

        // В случае перехода через границу источника out дополняется null-ссылками нужного класса
        out = Arrays.copyOfRange(in, head, phantomTail(), destClass);
        if (tail < head) {
            int base = arraySize - head;
            System.arraycopy(in, 0, out, endIndex - head, tail);
        }
        return out;
    }

    private <T> void ToExistsArray(T[] destination) {
        int endIndex = tail >= head ? tail : arraySize;
        System.arraycopy(elements, head, destination, 0, endIndex - head);

        if (tail < head) {
            int base = arraySize - head;
            System.arraycopy(elements, 0, destination, endIndex - head, tail);
        }
    }

    @Override
    public boolean add(E e) {
        if (! offer(e)) throw new IllegalStateException();
        return true;
    }

    private void incTail() {
        tail++;
        if (tail == arraySize) tail = 0;
    }

    private boolean isFull() {
        return size() == capacity;
    }

    @Override
    public boolean remove(Object o) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean containsAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean addAll(Collection<? extends E> c) {
        for (E e: c) add(e);
        return true;
    }

    @Override
    public boolean removeAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public boolean retainAll(Collection<?> c) {
        throw new UnsupportedOperationException();
    }

    @Override
    public void clear() {
        head = 0;
        tail = 0;
    }

    @Override
    public boolean offer(E e) {
        if (isFull()) return false;

        elements[tail] = e;
        incTail();
        return true;
    }

    @Override
    public E remove() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[getAndIncHead()];
    }

    @Override
    public E poll() {
        if (isEmpty()) return null;
        return elements[getAndIncHead()];
    }

    private int getAndIncHead() {
        int h = head++;
        if (head == arraySize) head = 0;
        return h;
    }

    @Override
    public E element() {
        if (isEmpty()) throw new NoSuchElementException();
        return elements[head];
    }

    @Override
    public E peek() {
        if (isEmpty()) return null;
        return elements[head];
    }

    private class MyQueueIterator implements Iterator<E> {

        private int carret;

        public MyQueueIterator() {
            carret = head;
        }

        @Override
        public boolean hasNext() {
            return carret != tail;
        }

        @Override
        public E next() {
            int oldCarret = carret;
            if (++carret >= arraySize) carret = 0;
            return elements[oldCarret];
        }
    }
}
