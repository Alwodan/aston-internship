package homework.arrayListImpl;

import java.util.Collection;
import java.util.Comparator;

public interface CustomList<E> {
    void add(int index, E element);

    void addAll(Collection<? extends E> c);

    void clear();

    E get(int index);

    boolean isEmpty();

    E remove(int index);

    boolean remove(Object o);

    void sort(Comparator<? super E> c);
}
