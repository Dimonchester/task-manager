package algorithm;

import service.Sorter;
import java.util.Arrays;
import java.util.Comparator;

public class TimSort<T extends Comparable<T>> implements Sorter<T> {
    @Override
    public void sort(T[] array) {
        Arrays.sort(array);
    }

    public void sort(T[] array, Comparator<? super T> comparator) {
        Arrays.sort(array, comparator);
    }
}