package homework.arrayListImpl.sorters;

import java.util.Comparator;

public class MergeSorter<E> implements Sorter<E> {
    Comparator<? super E> currentComparator;

    @Override
    public void sort(E[] arr, Comparator<? super E> comparator) {
        currentComparator = comparator;
        mergeSort(arr);
    }

    private void mergeSort(E[] input) {
        int inputLength = input.length;

        if (inputLength < 2) {
            return;
        }

        int midIndex = inputLength / 2;
        Object[] leftPart = new Object[midIndex];
        Object[] rightPart = new Object[inputLength - midIndex];

        System.arraycopy(input, 0, leftPart, 0, midIndex);
        System.arraycopy(input, midIndex, rightPart, 0, inputLength - midIndex);

        mergeSort((E[]) leftPart);
        mergeSort((E[]) rightPart);
        merge(input, (E[]) leftPart, (E[]) rightPart);
    }

    private void merge(E[] parent, E[] leftPart, E[] rightPart) {
        int leftSize = leftPart.length;
        int rightSize = rightPart.length;

        int i = 0, l = 0, r = 0;
        while (l < leftSize && r < rightSize) {
            if (currentComparator.compare(leftPart[l], rightPart[r]) <= 0) {
                parent[i] = leftPart[l];
                l++;
            } else {
                parent[i] = rightPart[r];
                r++;
            }
            i++;
        }
        while (l < leftSize) {
            parent[i] = leftPart[l];
            l++;
            i++;
        }
        while (r < rightSize) {
            parent[i] = rightPart[r];
            r++;
            i++;
        }
    }
}
