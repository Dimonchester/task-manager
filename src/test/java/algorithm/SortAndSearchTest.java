package algorithm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

class SortAndSearchTest {
    @Test
    void testQuickSort() {
        List<Integer> numbers = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6));
        SortAndSearch.quickSort(numbers);
        assertEquals(List.of(1, 1, 2, 3, 4, 5, 6, 9), numbers);
    }

    @Test
    void testQuickSortWithEmptyList() {
        List<Integer> numbers = new ArrayList<>();
        SortAndSearch.quickSort(numbers);
        assertTrue(numbers.isEmpty());
    }

    @Test
    void testQuickSortWithSingleElement() {
        List<Integer> numbers = new ArrayList<>(List.of(42));
        SortAndSearch.quickSort(numbers);
        assertEquals(List.of(42), numbers);
    }

    @Test
    void testQuickSortWithAlreadySortedList() {
        List<Integer> numbers = new ArrayList<>(List.of(1, 2, 3, 4, 5));
        SortAndSearch.quickSort(numbers);
        assertEquals(List.of(1, 2, 3, 4, 5), numbers);
    }

    @Test
    void testBinarySearch() {
        List<Integer> sortedNumbers = List.of(1, 2, 3, 4, 5, 6, 9);
        assertEquals(3, SortAndSearch.binarySearch(sortedNumbers, 4));
        assertEquals(-1, SortAndSearch.binarySearch(sortedNumbers, 7));
        assertEquals(0, SortAndSearch.binarySearch(sortedNumbers, 1));
        assertEquals(6, SortAndSearch.binarySearch(sortedNumbers, 9));
    }

    @Test
    void testBinarySearchOnEmptyList() {
        List<Integer> emptyList = Collections.emptyList();
        assertEquals(-1, SortAndSearch.binarySearch(emptyList, 5));
    }
}