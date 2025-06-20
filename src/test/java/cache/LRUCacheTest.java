package cache;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class LRUCacheTest {

    @Test
    void testPutAndGet() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");

        assertEquals("A", cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    void testGetMovesToFront() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.get(1);
        cache.put(3, "C"); // This should evict 2

        assertNull(cache.get(2));
        assertEquals("A", cache.get(1));
        assertEquals("C", cache.get(3));
    }

    @Test
    void testEvictionPolicy() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(3, "C");

        assertNull(cache.get(1));
        assertEquals("B", cache.get(2));
        assertEquals("C", cache.get(3));
    }

    @Test
    void testUpdateValue() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(1, "A_Updated");

        assertEquals("A_Updated", cache.get(1));
        assertEquals("B", cache.get(2));
    }

    @Test
    void testUpdateValueMovesToFront() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        cache.put(2, "B");
        cache.put(1, "A_Updated");
        cache.put(3, "C");

        assertNull(cache.get(2));
        assertEquals("A_Updated", cache.get(1));
        assertEquals("C", cache.get(3));
    }

    @Test
    void testGetNonExistent() {
        LRUCache<Integer, String> cache = new LRUCache<>(2);
        cache.put(1, "A");
        assertNull(cache.get(99));
    }
}