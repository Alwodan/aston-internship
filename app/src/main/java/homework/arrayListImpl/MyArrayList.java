package homework.arrayListImpl;

import homework.arrayListImpl.sorters.MergeSorter;
import homework.arrayListImpl.sorters.QuickSorter;
import homework.arrayListImpl.sorters.Sorter;

import java.time.LocalTime;
import java.util.*;

public class MyArrayList<E> implements CustomList<E> {
    private final static int INITIAL_CAPACITY = 10;
    private Object[] base;
    private int size;
    private Sorter<E> sorter;

    public MyArrayList() {
        base = new Object[INITIAL_CAPACITY];
    }

    public MyArrayList(int capacity) {
        if (capacity >= 0) {
            base = new Object[capacity];
        } else {
            throw new IllegalArgumentException("I cannot break reality sorry");
        }
    }

    public void add(Object element) {
        add(size, element);
    }

    @Override
    public void add(int index, Object element) {
        checkIndexForAddition(index);
        if (this.size == this.base.length) {
            this.base = grow(base.length);
        }
        System.arraycopy(base, index, base, index + 1, this.size - index);
        base[index] = element;
        this.size++;
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        Object[] arrayToAdd = c.toArray();
        int totalSize = arrayToAdd.length + this.size;
        if (totalSize > base.length) {
            this.base = grow(totalSize);
        }
        System.arraycopy(arrayToAdd, 0, base, size, arrayToAdd.length);
        this.size += arrayToAdd.length;
    }

    @Override
    public void clear() {
        this.base = new Object[INITIAL_CAPACITY];
        this.size = 0;
    }

    @Override
    public E get(int index) {
        Objects.checkIndex(index, this.size);
        return (E) base[index];
    }

    @Override
    public boolean isEmpty() {
        return this.size == 0;
    }

    @Override
    public E remove(int index) {
        Objects.checkIndex(index, this.size);
        E oldValue = (E) base[index];
        int newSize = this.size - 1;
        if (newSize > index) {
            System.arraycopy(base, index + 1, base, index, newSize - index);
        }
        this.size = newSize;
        base[this.size] = null;
        return oldValue;
    }

    @Override
    public boolean remove(Object o) {
        int i = 0;
        while(true) {
            if (i >= size) {
                return false;
            }

            if (o.equals(base[i])) {
                break;
            }

            ++i;
        }

        this.remove(i);
        return true;
    }

    @Override
    public void sort(Comparator<? super E> c) {
        Object[] toSort = new Object[this.size];
        System.arraycopy(this.base, 0, toSort, 0, this.size);
        sorter.sort((E[]) toSort, c);
        System.arraycopy(toSort, 0, this.base, 0, this.size);
    }

    private Object[] grow(int minCapacity) {
        return Arrays.copyOf(this.base, Math.max(minCapacity, this.base.length * 2));
    }

    private void checkIndexForAddition(int index) {
        if (index > this.size || index < 0) {
            throw new IllegalArgumentException("You cannot add to the void");
        }
    }

    @Override
    public String toString() {
        return "MyArrayList{" +
                "base=" + Arrays.toString(base) +
                '}';
    }

    public int getSize() {
        return size;
    }

    public void setSorter(Sorter<E> sorter) {
        this.sorter = sorter;
    }
}
