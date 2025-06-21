package service;

import model.Task;

import java.util.*;

public class TagFilter {
    private final Map<String, Set<Task>> tagToTasksMap = new HashMap<>();

    public void indexTask(Task task) {
        if (task == null) return;
        
        task.getTags().forEach(tag -> {
            String normalizedTag = tag.toLowerCase();
            tagToTasksMap.computeIfAbsent(normalizedTag, k -> new HashSet<>()).add(task);
        });
    }

    public void removeTask(Task task) {
        if (task == null) return;
        
        task.getTags().forEach(tag -> {
            String normalizedTag = tag.toLowerCase();
            Set<Task> tasksWithTag = tagToTasksMap.get(normalizedTag);
            if (tasksWithTag != null) {
                tasksWithTag.remove(task);
                if (tasksWithTag.isEmpty()) {
                    tagToTasksMap.remove(normalizedTag);
                }
            }
        });
    }

    public Set<Task> getTasksByTag(String tag) {
        if (tag == null) return Collections.emptySet();
        return new HashSet<>(tagToTasksMap.getOrDefault(tag.toLowerCase(), Collections.emptySet()));
    }

    public Set<String> getAllTags() {
        return new HashSet<>(tagToTasksMap.keySet());
    }
}