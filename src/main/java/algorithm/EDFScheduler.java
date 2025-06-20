package algorithm;

import datastructure.BinaryHeap;
import model.Task;

import java.util.ArrayList;
import java.util.List;

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

    public List<Task> getAllTasks() {
        List<Task> tasks = new ArrayList<>();
        while (hasTasks()) {
            Task task = getNextTask();
            tasks.add(task);
        }
        // Восстанавливаем очередь
        tasks.forEach(this::addTask);
        return tasks;
    }
}