package algorithm;

import java.util.Collections;
import java.util.Comparator;
import java.util.List;

public class SortAndSearch {

    public static <T extends Comparable<T>> void quickSort(List<T> list) {
        quickSort(list, 0, list.size() - 1);
    }

    private static <T extends Comparable<T>> void quickSort(List<T> list, int begin, int end) {
        if (begin < end) {
            int partitionIndex = partition(list, begin, end);
            quickSort(list, begin, partitionIndex - 1);
            quickSort(list, partitionIndex + 1, end);
        }
    }

    private static <T extends Comparable<T>> int partition(List<T> list, int begin, int end) {
        T pivot = list.get(end);
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (list.get(j).compareTo(pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, end);
        return i + 1;
    }

    public static <T> void quickSort(List<T> list, Comparator<T> comparator) {
        quickSort(list, 0, list.size() - 1, comparator);
    }

    private static <T> void quickSort(List<T> list, int begin, int end, Comparator<T> comparator) {
        if (begin < end) {
            int partitionIndex = partition(list, begin, end, comparator);
            quickSort(list, begin, partitionIndex - 1, comparator);
            quickSort(list, partitionIndex + 1, end, comparator);
        }
    }

    private static <T> int partition(List<T> list, int begin, int end, Comparator<T> comparator) {
        T pivot = list.get(end);
        int i = (begin - 1);
        for (int j = begin; j < end; j++) {
            if (comparator.compare(list.get(j), pivot) <= 0) {
                i++;
                Collections.swap(list, i, j);
            }
        }
        Collections.swap(list, i + 1, end);
        return i + 1;
    }

    public static <T extends Comparable<T>> int binarySearch(List<T> sortedList, T key) {
        int low = 0;
        int high = sortedList.size() - 1;
        while (low <= high) {
            int mid = low + (high - low) / 2;
            int cmp = sortedList.get(mid).compareTo(key);
            if (cmp < 0) {
                low = mid + 1;
            } else if (cmp > 0) {
                high = mid - 1;
            } else {
                return mid;
            }
        }
        return -1;
    }
}