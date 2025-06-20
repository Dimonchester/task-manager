package service;

import algorithm.CycleDetector;
import algorithm.EDFScheduler;
import algorithm.TopologicalSorter;
import datastructure.MyHashMap;
import datastructure.Trie;
import model.Task;
import model.TaskDependency;

import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private final MyHashMap<Integer, Task> tasks = new MyHashMap<>();
    private final Set<TaskDependency> dependencies = new HashSet<>();
    private final TopologicalSorter sorter = new TopologicalSorter();
    private final CycleDetector detector = new CycleDetector();

    // Интегрированная логика для тегов и поиска с использованием кастомных структур
    private final MyHashMap<String, Set<Task>> tagIndex = new MyHashMap<>();
    private final Trie titleTrie = new Trie();

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.put(task.getId(), task);
        indexTask(task);
    }

    public Task getTask(int id) {
        return tasks.get(id);
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void updateTask(Task task) {
        if (task == null || tasks.get(task.getId()) == null) {
            throw new IllegalArgumentException("Task not found or null");
        }
        Task oldTask = tasks.get(task.getId());
        unIndexTask(oldTask);
        tasks.put(task.getId(), task);
        indexTask(task);
    }

    public void deleteTask(int id) {
        Task task = tasks.remove(id);
        if (task != null) {
            dependencies.removeIf(dep -> dep.getTask().equals(task) || dep.getDependsOn().equals(task));
            unIndexTask(task);
        }
    }

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

    public List<Task> getTasksInTopologicalOrder() {
        return sorter.sortTasks(new HashSet<>(tasks.values()), dependencies);
    }

    public Set<Task> getTasksByTag(String tag) {
        if (tag == null) return Collections.emptySet();
        return new HashSet<>(tagIndex.getOrDefault(tag.toLowerCase(), Collections.emptySet()));
    }

    public List<Task> suggestTasksByTitlePrefix(String prefix) {
        List<String> suggestedTitles = titleTrie.suggest(prefix);
        return getAllTasks().stream()
                .filter(task -> suggestedTitles.contains(task.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Task> getScheduledTasks() {
        EDFScheduler scheduler = new EDFScheduler();
        tasks.values().forEach(scheduler::addTask);

        List<Task> scheduled = new ArrayList<>();
        while (scheduler.hasTasks()) {
            scheduled.add(scheduler.getNextTask());
        }
        return scheduled;
    }

    public List<Task> getTasksSortedBy(Comparator<Task> comparator) {
        List<Task> taskList = new ArrayList<>(tasks.values());
        taskList.sort(comparator);
        return taskList;
    }

    // --- Приватные методы для индексации ---

    private void indexTask(Task task) {
        // Индексация тегов
        task.getTags().forEach(tag -> {
            tagIndex.computeIfAbsent(tag.toLowerCase(), k -> new HashSet<>()).add(task);
        });
        // Индексация заголовка в Trie
        titleTrie.insert(task.getTitle());
    }

    private void unIndexTask(Task task) {
        // Удаление из индекса тегов
        task.getTags().forEach(tag -> {
            Set<Task> tasksWithTag = tagIndex.get(tag.toLowerCase());
            if (tasksWithTag != null) {
                tasksWithTag.remove(task);
                if (tasksWithTag.isEmpty()) {
                    tagIndex.remove(tag.toLowerCase());
                }
            }
        });
        // Удаление из Trie
        titleTrie.delete(task.getTitle());
    }
}