package datastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;
import java.util.NoSuchElementException;

class BinaryHeapTest {
    @Test
    void testMinHeapBehavior() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(10);
        heap.add(20);
        heap.add(5); // Smallest element

        // It's a min-heap, so poll() should return the smallest element
        assertEquals(5, heap.poll());
        assertEquals(10, heap.poll());
        assertEquals(20, heap.poll());
        assertTrue(heap.isEmpty());
    }

    @Test
    void testIsEmptyAndPollOnEmpty() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        assertTrue(heap.isEmpty());
        assertThrows(NoSuchElementException.class, heap::poll);
    }

    @Test
    void testOrderingWithDuplicates() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(50);
        heap.add(10);
        heap.add(30);
        heap.add(10); // Duplicate

        assertEquals(10, heap.poll());
        assertEquals(10, heap.poll());
        assertEquals(30, heap.poll());
        assertEquals(50, heap.poll());
    }

    @Test
    void testSingleElementHeap() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(100);
        assertFalse(heap.isEmpty());
        assertEquals(100, heap.poll());
        assertTrue(heap.isEmpty());
    }
}