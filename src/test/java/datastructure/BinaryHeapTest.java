package datastructure;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

class BinaryHeapTest {
    @Test
    void testHeapOperations() {
        BinaryHeap<Integer> heap = new BinaryHeap<>();
        heap.add(10);
        heap.add(20);
        heap.add(5);
        assertEquals(20, heap.poll());
        assertEquals(10, heap.poll());
        assertFalse(heap.isEmpty());
    }
}