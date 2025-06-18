package algorithm;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.ArrayList;
import java.util.List;

class SortAndSearchTest {
    @Test
    void testQuickSort() {
        List<Integer> numbers = new ArrayList<>(List.of(3, 1, 4, 1, 5, 9, 2, 6));
        SortAndSearch.quickSort(numbers);
        assertEquals(List.of(1, 1, 2, 3, 4, 5, 6, 9), numbers);
    }

    @Test
    void testBinarySearch() {
        List<Integer> sortedNumbers = List.of(1, 2, 3, 4, 5, 6, 9);
        assertEquals(3, SortAndSearch.binarySearch(sortedNumbers, 4));
        assertEquals(-1, SortAndSearch.binarySearch(sortedNumbers, 7));
    }
}