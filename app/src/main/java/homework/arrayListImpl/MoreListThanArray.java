package homework.arrayListImpl;

import java.util.Collection;
import java.util.Comparator;
import java.util.Objects;

public class MoreListThanArray<E> implements CustomList<E> {
    private final static int INITIAL_CAPACITY = 10;
    private Object[] base;
    private int size;
    private int capacity;

    public MoreListThanArray() {
        this.capacity = INITIAL_CAPACITY;
        base = new Object[INITIAL_CAPACITY];
    }

    public MoreListThanArray(int capacity) {
        if (capacity >= 0) {
            this.capacity = capacity;
            base = new Object[capacity];
        } else {
            throw new IllegalArgumentException("I cannot break reality sorry");
        }
    }

    @Override
    public void add(int index, Object element) {
        checkIndexForAddition(index);
        if (size == capacity) {
            //TODO: grow
        }
        System.arraycopy(base, index, base, index + 1, this.size - index);
        base[index] = element;
        this.size++;
    }

    @Override
    public void addAll(Collection<? extends E> c) {
        //TODO: implement
    }

    @Override
    public void clear() {
        this.base = new Object[INITIAL_CAPACITY];
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
        if (o == null) {
            while(true) {
                if (i >= size) {
                    return false;
                }

                if (base[i] == null) {
                    break;
                }

                ++i;
            }
        } else {
            while(true) {
                if (i >= size) {
                    return false;
                }

                if (o.equals(base[i])) {
                    break;
                }

                ++i;
            }
        }

        this.remove(i);
        return true;
    }

    @Override
    public void sort(Comparator c) {

    }

    private void checkIndexForAddition(int index) {
        if (index > this.size || index < 0) {
            throw new IllegalArgumentException("You cannot add to the void");
        }
    }
}
