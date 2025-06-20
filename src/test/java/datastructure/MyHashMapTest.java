package datastructure;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

import java.util.Collection;
import java.util.List;
import java.util.Set;

class MyHashMapTest {
    @Test
    void testPutAndGet() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "Task1");
        map.put(2, "Task2");
        assertEquals("Task1", map.get(1));
        assertEquals(2, map.size());
        assertNull(map.get(3));
    }

    @Test
    void testUpdateExistingKey() {
        MyHashMap<String, Integer> map = new MyHashMap<>();
        map.put("key1", 100);
        assertEquals(100, map.get("key1"));
        map.put("key1", 200);
        assertEquals(1, map.size());
        assertEquals(200, map.get("key1"));
    }

    @Test
    void testRemove() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "A");
        map.put(2, "B");
        assertEquals(2, map.size());

        String removedValue = map.remove(1);
        assertEquals("A", removedValue);
        assertEquals(1, map.size());
        assertNull(map.get(1));
        assertNotNull(map.get(2));

        assertNull(map.remove(99)); // Remove non-existent key
        assertEquals(1, map.size());
    }

    @Test
    void testResize() {
        // Using default capacity 16 and load factor 0.75f. Resize will trigger at 13th element.
        MyHashMap<Integer, Integer> map = new MyHashMap<>();
        for (int i = 0; i < 20; i++) {
            map.put(i, i * 2);
        }
        assertEquals(20, map.size());
        assertEquals(10, map.get(5));
        assertEquals(38, map.get(19));
    }

    @Test
    void testKeySetAndValues() {
        MyHashMap<String, String> map = new MyHashMap<>();
        map.put("k1", "v1");
        map.put("k2", "v2");
        map.put("k3", "v3");

        Set<String> keys = map.keySet();
        Collection<String> values = map.values();

        assertEquals(3, keys.size());
        assertEquals(3, values.size());

        assertTrue(keys.containsAll(List.of("k1", "k2", "k3")));
        assertTrue(values.containsAll(List.of("v1", "v2", "v3")));
    }

    @Test
    void testGetOrDefault() {
        MyHashMap<Integer, String> map = new MyHashMap<>();
        map.put(1, "One");
        assertEquals("One", map.getOrDefault(1, "Default"));
        assertEquals("Default", map.getOrDefault(2, "Default"));
    }

    @Test
    void testComputeIfAbsent() {
        MyHashMap<String, Integer> map = new MyHashMap<>();

        Integer val1 = map.computeIfAbsent("key1", k -> k.length());
        assertEquals(4, val1);
        assertEquals(4, map.get("key1"));
        assertEquals(1, map.size());

        Integer val2 = map.computeIfAbsent("key1", k -> 100);
        assertEquals(4, val2); // Should return existing value, not compute new one
        assertEquals(4, map.get("key1"));
        assertEquals(1, map.size());
    }
}