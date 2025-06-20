package algorithm;

import model.Task;
import model.TaskDependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

class TopologicalSorterTest {

    private TopologicalSorter sorter;
    private Task task1, task2, task3, task4, task5;

    @BeforeEach
    void setUp() {
        sorter = new TopologicalSorter();
        LocalDate deadline = LocalDate.now();
        task1 = new Task(1, "Task 1", deadline);
        task2 = new Task(2, "Task 2", deadline);
        task3 = new Task(3, "Task 3", deadline);
        task4 = new Task(4, "Task 4", deadline);
        task5 = new Task(5, "Task 5 (Independent)", deadline);
    }

    @Test
    void testLinearDependencySort() {
        // 4 -> 3 -> 2 -> 1
        Set<Task> tasks = Set.of(task1, task2, task3, task4);
        Set<TaskDependency> dependencies = new HashSet<>();
        dependencies.add(new TaskDependency(task2, task1));
        dependencies.add(new TaskDependency(task3, task2));
        dependencies.add(new TaskDependency(task4, task3));

        List<Task> sortedTasks = sorter.sortTasks(tasks, dependencies);

        // Correct order should be 1, 2, 3, 4
        assertEquals(4, sortedTasks.size());
        assertTrue(sortedTasks.indexOf(task1) < sortedTasks.indexOf(task2));
        assertTrue(sortedTasks.indexOf(task2) < sortedTasks.indexOf(task3));
        assertTrue(sortedTasks.indexOf(task3) < sortedTasks.indexOf(task4));
    }

    @Test
    void testComplexGraphSort() {
        // 4 -> 2 -> 1
        // 4 -> 3 -> 1
        // 5 (independent)
        Set<Task> tasks = Set.of(task1, task2, task3, task4, task5);
        Set<TaskDependency> dependencies = new HashSet<>();
        dependencies.add(new TaskDependency(task2, task1));
        dependencies.add(new TaskDependency(task3, task1));
        dependencies.add(new TaskDependency(task4, task2));
        dependencies.add(new TaskDependency(task4, task3));

        List<Task> sortedTasks = sorter.sortTasks(tasks, dependencies);

        assertEquals(5, sortedTasks.size());
        assertTrue(sortedTasks.indexOf(task1) < sortedTasks.indexOf(task2));
        assertTrue(sortedTasks.indexOf(task1) < sortedTasks.indexOf(task3));
        assertTrue(sortedTasks.indexOf(task2) < sortedTasks.indexOf(task4));
        assertTrue(sortedTasks.indexOf(task3) < sortedTasks.indexOf(task4));
    }

    @Test
    void testNoDependencies() {
        Set<Task> tasks = Set.of(task1, task2, task3);
        List<Task> sortedTasks = sorter.sortTasks(tasks, new HashSet<>());
        assertEquals(3, sortedTasks.size());
        assertTrue(sortedTasks.containsAll(tasks));
    }
}