package algorithm;

import model.Task;
import java.util.PriorityQueue;

public class EDFScheduler {
    private final PriorityQueue<Task> queue;

    public EDFScheduler() {
        this.queue = new PriorityQueue<>();
    }

    public void addTask(Task task) {
        queue.offer(task);
    }

    public Task getNextTask() {
        return queue.poll();
    }

    public boolean hasTasks() {
        return !queue.isEmpty();
    }
}