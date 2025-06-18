package algorithm;

import model.Task;
import model.TaskDependency;

import java.util.*;

public class CycleDetector {
    public boolean hasCycle(Set<Task> tasks, Set<TaskDependency> dependencies) {
        if (tasks == null || tasks.isEmpty()) {
            return false;
        }

        Map<Task, Set<Task>> graph = buildGraph(tasks, dependencies);
        Set<Task> visited = new HashSet<>();
        Set<Task> recursionStack = new HashSet<>();

        for (Task task : tasks) {
            if (hasCycleUtil(task, graph, visited, recursionStack)) {
                return true;
            }
        }

        return false;
    }

    private Map<Task, Set<Task>> buildGraph(Set<Task> tasks, Set<TaskDependency> dependencies) {
        Map<Task, Set<Task>> graph = new HashMap<>();
        tasks.forEach(task -> graph.put(task, new HashSet<>()));
        dependencies.forEach(dep -> graph.get(dep.getTask()).add(dep.getDependsOn()));
        return graph;
    }

    private boolean hasCycleUtil(Task task, Map<Task, Set<Task>> graph, 
                               Set<Task> visited, Set<Task> recursionStack) {
        if (recursionStack.contains(task)) {
            return true;
        }
        
        if (visited.contains(task)) {
            return false;
        }
        
        visited.add(task);
        recursionStack.add(task);
        
        for (Task neighbor : graph.get(task)) {
            if (hasCycleUtil(neighbor, graph, visited, recursionStack)) {
                return true;
            }
        }
        
        recursionStack.remove(task);
        return false;
    }
}