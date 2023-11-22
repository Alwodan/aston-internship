package homework.arrayListImpl;

import homework.arrayListImpl.sorters.MergeSorter;
import homework.arrayListImpl.sorters.QuickSorter;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

public class MyArrayListTest {
    private static MyArrayList<Integer> list;


    @BeforeEach
    public void generate() {
        list = new MyArrayList<>();
        list.add(40);
        list.add(10);
        list.add(30);
        list.add(50);
        list.add(20);
    }

    @AfterEach
    public void empty() {
        list.clear();
    }

    @Test
    void addTest() {
        assertThat(list.getSize()).isEqualTo(5);
        list.add(100);
        assertThat(list.getSize()).isEqualTo(6);
    }

    @Test
    void addAllTest() {
        List<Integer> tempList = new ArrayList<>();
        tempList.add(10);
        tempList.add(20);
        tempList.add(30);
        list.addAll(tempList);
        assertThat(list.getSize()).isEqualTo(8);
        assertThat(list.get(6)).isEqualTo(20);
    }

    @Test
    void clearTest() {
        list.clear();
        assertThat(list.getSize()).isEqualTo(0);
    }

    @Test
    void isEmptyTest() {
        list.clear();
        assertThat(list.isEmpty()).isTrue();
    }

    @Test
    void testRemove() {
        assertThat(list.remove(0)).isEqualTo(40);
        assertThat(list.get(0)).isEqualTo(10);
        assertThat(list.remove(Integer.valueOf(10))).isTrue();
        assertThat(list.get(0)).isEqualTo(30);
    }

    @Test
    void testQuickSort() {
        list.setSorter(new QuickSorter<>());
        list.sort(Comparator.comparingInt(a -> a));
        for (int i = 1; i < list.getSize(); i++) {
            assertThat(list.get(i - 1)).isLessThan(list.get(i));
        }
    }

    @Test
    void testMergeSort() {
        list.setSorter(new MergeSorter<>());
        list.sort(Comparator.comparingInt(a -> a));
        for (int i = 1; i < list.getSize(); i++) {
            assertThat(list.get(i - 1)).isLessThan(list.get(i));
        }
    }

    @Test
    void testInsertionInMiddle() {
        list.add(2, 100);
        assertThat(list.get(2)).isEqualTo(100);
        assertThat(list.getSize()).isEqualTo(6);
        assertThat(list.get(3)).isEqualTo(30);
    }

}
