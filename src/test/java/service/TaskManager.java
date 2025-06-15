package service;

import algorithm.CycleDetector;
import algorithm.TopologicalSorter;
import model.Task;
import model.TaskDependency;

import java.util.*;

public class TaskManager {
    private final Map<Integer, Task> tasks = new HashMap<>();
    private final Set<TaskDependency> dependencies = new HashSet<>();
    private final TopologicalSorter sorter = new TopologicalSorter();
    private final CycleDetector detector = new CycleDetector();

    // CRUD операции
    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.put(task.getId(), task);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void updateTask(Task task) {
        if (task == null || !tasks.containsKey(task.getId())) {
            throw new IllegalArgumentException("Task not found or null");
        }
        tasks.put(task.getId(), task);
    }

    public void deleteTask(int id) {
        Task task = tasks.remove(id);
        if (task != null) {
            dependencies.removeIf(dep -> dep.getTask().equals(task) || dep.getDependsOn().equals(task));
        }
    }

    // Управление зависимостями
    public void addDependency(int taskId, int dependsOnId) {
        Task task = getTask(taskId);
        Task dependsOn = getTask(dependsOnId);
        
        if (task == null || dependsOn == null) {
            throw new IllegalArgumentException("One or both tasks not found");
        }
        
        TaskDependency dependency = new TaskDependency(task, dependsOn);
        dependencies.add(dependency);
        
        if (detector.hasCycle(new HashSet<>(tasks.values()), dependencies)) {
            dependencies.remove(dependency);
            throw new IllegalStateException("Dependency creates a cycle");
        }
    }

    public void removeDependency(int taskId, int dependsOnId) {
        Task task = getTask(taskId);
        Task dependsOn = getTask(dependsOnId);
        
        if (task != null && dependsOn != null) {
            dependencies.remove(new TaskDependency(task, dependsOn));
        }
    }

    public List<Task> getTasksInOrder() {
        return sorter.sortTasks(new HashSet<>(tasks.values()), dependencies);
    }

    public Set<TaskDependency> getDependencies() {
        return new HashSet<>(dependencies);
    }
}