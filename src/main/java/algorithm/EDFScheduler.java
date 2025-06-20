package algorithm;

import datastructure.BinaryHeap;
import model.Task;

public class EDFScheduler {
    private final BinaryHeap<Task> queue;

    public EDFScheduler() {
        this.queue = new BinaryHeap<>();
    }

    public void addTask(Task task) {
        queue.add(task);
    }

    public Task getNextTask() {
        return queue.poll();
    }

    public boolean hasTasks() {
        return !queue.isEmpty();
    }
}