package algorithm;

import model.Task;
import model.TaskDependency;

import java.util.*;

public class TopologicalSorter {
    public List<Task> sortTasks(Set<Task> tasks, Set<TaskDependency> dependencies) {
        if (tasks == null || tasks.isEmpty()) {
            return Collections.emptyList();
        }

        Map<Task, Set<Task>> graph = buildGraph(tasks, dependencies);
        Set<Task> visited = new HashSet<>();
        LinkedList<Task> result = new LinkedList<>(); // Изменено на LinkedList

        for (Task task : tasks) {
            if (!visited.contains(task)) {
                dfs(task, graph, visited, result);
            }
        }

        return result; // Теперь порядок прямой
    }

    private Map<Task, Set<Task>> buildGraph(Set<Task> tasks, Set<TaskDependency> dependencies) {
        Map<Task, Set<Task>> graph = new HashMap<>();
        tasks.forEach(task -> graph.put(task, new HashSet<>()));
        dependencies.forEach(dep -> graph.get(dep.getTask()).add(dep.getDependsOn()));
        return graph;
    }

    private void dfs(Task task, Map<Task, Set<Task>> graph, Set<Task> visited, LinkedList<Task> result) {
        visited.add(task);
        for (Task neighbor : graph.get(task)) {
            if (!visited.contains(neighbor)) {
                dfs(neighbor, graph, visited, result);
            }
        }
        result.add(task);
    }
}