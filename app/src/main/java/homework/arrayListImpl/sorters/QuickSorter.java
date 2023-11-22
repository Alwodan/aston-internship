package homework.arrayListImpl.sorters;

import java.util.Comparator;

public class QuickSorter<E> implements Sorter<E> {
    Comparator<? super E> comparator;

    @Override
    public void sort(E[] arr, Comparator<? super E> comparator) {
        this.comparator = comparator;
        quickSort(arr, 0, arr.length - 1);
    }

    private void quickSort(E[] array, int lowBound, int highBound) {
        if (lowBound >= highBound) {
            return;
        }
        E pivot = array[highBound];
        int leftPointer = lowBound;
        int rightPointer = highBound;

        while (leftPointer < rightPointer) {
            while (comparator.compare(array[leftPointer], pivot) <= 0 && leftPointer < rightPointer) {
                leftPointer++;
            }
            while (comparator.compare(array[rightPointer], pivot) >= 0 && leftPointer < rightPointer) {
                rightPointer--;
            }
            swap(array, leftPointer, rightPointer);
        }
        swap(array, leftPointer, highBound);

        quickSort(array, lowBound, leftPointer - 1);
        quickSort(array, leftPointer + 1, highBound);
    }

    private void swap(E[] array, int l, int r) {
        E temp = array[l];
        array[l] = array[r];
        array[r] = temp;
    }
}
