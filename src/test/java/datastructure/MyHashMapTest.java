package datastructure;
import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.Test;

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
}