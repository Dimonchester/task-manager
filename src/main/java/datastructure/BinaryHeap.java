package datastructure;

import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;

public class BinaryHeap<T extends Comparable<T>> {
    private final List<T> heap = new ArrayList<>();

    public void add(T item) {
        heap.add(item);
        siftUp(heap.size() - 1);
    }

    public T poll() {
        if (isEmpty()) throw new NoSuchElementException();
        T result = heap.get(0);
        T last = heap.remove(heap.size() - 1);
        if (!isEmpty()) {
            heap.set(0, last);
            siftDown(0);
        }
        return result;
    }

    public boolean isEmpty() {
        return heap.isEmpty();
    }

    private void siftUp(int index) {
        if (index == 0) return;
        int parentIndex = (index - 1) / 2;
        if (heap.get(index).compareTo(heap.get(parentIndex)) > 0) {
            swap(index, parentIndex);
            siftUp(parentIndex);
        }
    }

    private void siftDown(int index) {
        int left = 2 * index + 1;
        int right = 2 * index + 2;
        int largest = index;
        if (left < heap.size() && heap.get(left).compareTo(heap.get(largest)) > 0) {
            largest = left;
        }
        if (right < heap.size() && heap.get(right).compareTo(heap.get(largest)) > 0) {
            largest = right;
        }
        if (largest != index) {
            swap(index, largest);
            siftDown(largest);
        }
    }

    private void swap(int i, int j) {
        T temp = heap.get(i);
        heap.set(i, heap.get(j));
        heap.set(j, temp);
    }
}