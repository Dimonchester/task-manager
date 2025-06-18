package datastructure;

import java.util.LinkedList;

public class MyHashMap<K, V> {
    private static final int DEFAULT_CAPACITY = 16;
    private final LinkedList<Entry<K, V>>[] table;
    private int size = 0;

    @SuppressWarnings("unchecked")
    public MyHashMap() {
        this.table = (LinkedList<Entry<K, V>>[]) new LinkedList[DEFAULT_CAPACITY];
    }

    public void put(K key, V value) {
        int index = hash(key);
        if (table[index] == null) {
            table[index] = new LinkedList<>();
        }
        for (Entry<K, V> entry : table[index]) {
            if (entry.getKey().equals(key)) {
                entry.setValue(value);
                return;
            }
        }
        table[index].add(new Entry<>(key, value));
        size++;
    }

    public V get(K key) {
        int index = hash(key);
        if (table[index] != null) {
            for (Entry<K, V> entry : table[index]) {
                if (entry.getKey().equals(key)) {
                    return entry.getValue();
                }
            }
        }
        return null;
    }

    public int size() {
        return size;
    }

    private int hash(K key) {
        return Math.abs(key.hashCode()) % DEFAULT_CAPACITY;
    }

    static class Entry<K, V> {
        private final K key;
        private V value;

        public Entry(K key, V value) {
            this.key = key;
            this.value = value;
        }

        public K getKey() { return key; }
        public V getValue() { return value; }
        public void setValue(V value) { this.value = value; }
    }
}