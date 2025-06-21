package service;

import algorithm.CycleDetector;
import algorithm.EDFScheduler;
import algorithm.SortAndSearch;
import algorithm.TopologicalSorter;
import cache.LRUCache;
import datastructure.MyHashMap;
import datastructure.Trie;
import model.Task;
import model.TaskDependency;

import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

public class TaskManager {
    private final MyHashMap<Integer, Task> tasks = new MyHashMap<>();
    private final Set<TaskDependency> dependencies = new HashSet<>();

    private final LRUCache<Integer, Task> taskCache = new LRUCache<>(10);
    private final TopologicalSorter topologicalSorter = new TopologicalSorter();
    private final CycleDetector cycleDetector = new CycleDetector();
    private final TagFilter tagFilter = new TagFilter();
    private final Trie titleTrie = new Trie();

    public void addNewTask(int id, String title, LocalDateTime deadline, Set<String> tags) {
        if (title == null || title.isBlank()) {
            throw new IllegalArgumentException("Task title can't be empty.");
        }
        Task task = new Task(id, title, deadline);
        if (tags != null) {
            tags.forEach(task::addTag);
        }
        this.addTask(task);
    }

    public void addTask(Task task) {
        if (task == null) {
            throw new IllegalArgumentException("Task cannot be null");
        }
        tasks.put(task.getId(), task);
        tagFilter.indexTask(task);
        titleTrie.insert(task.getTitle());
    }

    public Task getTask(int id) {
        Task cachedTask = taskCache.get(id);
        if (cachedTask != null) {
            System.out.println("Loaded from cache");
            return cachedTask;
        }
        Task task = tasks.get(id);
        if (task != null) {
            taskCache.put(id, task);
        }
        return task;
    }

    public List<Task> getAllTasks() {
        return new ArrayList<>(tasks.values());
    }

    public void updateTask(Task task) {
        if (task == null || tasks.get(task.getId()) == null) {
            throw new IllegalArgumentException("Task not found or null");
        }
        Task oldTask = tasks.get(task.getId());

        tagFilter.removeTask(oldTask);
        titleTrie.delete(oldTask.getTitle());

        tasks.put(task.getId(), task);

        tagFilter.indexTask(task);
        titleTrie.insert(task.getTitle());
        taskCache.put(task.getId(), task);
    }

    public void deleteTask(int id) {
        Task task = tasks.remove(id);
        if (task != null) {
            dependencies.removeIf(dep -> dep.getTask().equals(task) || dep.getDependsOn().equals(task));
            tagFilter.removeTask(task);
            titleTrie.delete(task.getTitle());
        }
    }

    public void addDependency(int taskId, int dependsOnId) {
        Task task = tasks.get(taskId);
        Task dependsOn = tasks.get(dependsOnId);

        if (task == null || dependsOn == null) {
            throw new IllegalArgumentException("One or both tasks for dependency not found");
        }

        TaskDependency dependency = new TaskDependency(task, dependsOn);
        dependencies.add(dependency);
        if (cycleDetector.hasCycle(new HashSet<>(tasks.values()), dependencies)) {
            dependencies.remove(dependency);
            throw new IllegalStateException("Error: This dependency creates a cycle!");
        }
    }

    public List<Task> getTasksInTopologicalOrder() {
        return topologicalSorter.sortTasks(new HashSet<>(tasks.values()), dependencies);
    }

    public List<Task> getScheduledTasksByEDF() {
        EDFScheduler scheduler = new EDFScheduler();
        tasks.values().forEach(scheduler::addTask);

        List<Task> scheduled = new ArrayList<>();
        while (scheduler.hasTasks()) {
            scheduled.add(scheduler.getNextTask());
        }
        return scheduled;
    }

    public Set<Task> getTasksByTag(String tag) {
        return tagFilter.getTasksByTag(tag);
    }

    public List<Task> suggestTasksByTitlePrefix(String prefix) {
        List<String> suggestedTitles = titleTrie.suggest(prefix);
        return getAllTasks().stream()
                .filter(task -> suggestedTitles.contains(task.getTitle()))
                .collect(Collectors.toList());
    }

    public List<Task> getTasksSortedByQuickSort(Comparator<Task> comparator) {
        List<Task> taskList = new ArrayList<>(tasks.values());
        SortAndSearch.quickSort(taskList, comparator);
        return taskList;
    }

    public int findTaskWithBinarySearch(List<Task> sortedList, Task key) {
        return SortAndSearch.binarySearch(sortedList, key);
    }
}