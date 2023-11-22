package homework.arrayListImpl.sorters;

import java.util.Comparator;

public interface Sorter<E> {
    void sort(E[] arr, Comparator<? super E> comparator);
}
