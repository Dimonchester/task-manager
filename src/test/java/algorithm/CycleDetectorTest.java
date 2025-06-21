package algorithm;

import model.Task;
import model.TaskDependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDateTime;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class CycleDetectorTest {

    private CycleDetector detector;
    private Task task1, task2, task3;

    @BeforeEach
    void setUp() {
        detector = new CycleDetector();
        LocalDateTime deadline = LocalDateTime.now();
        task1 = new Task(1, "Task 1", deadline);
        task2 = new Task(2, "Task 2", deadline);
        task3 = new Task(3, "Task 3", deadline);
    }

    @Test
    void testHasCycleWithEmptyAndNullSets() {
        assertFalse(detector.hasCycle(null, new HashSet<>()));
        assertFalse(detector.hasCycle(new HashSet<>(), new HashSet<>()));
        Set<Task> tasks = Set.of(task1);
        assertFalse(detector.hasCycle(tasks, null));
        assertFalse(detector.hasCycle(tasks, new HashSet<>()));
    }

    @Test
    void testGraphWithoutCycle() {
        Set<Task> tasks = Set.of(task1, task2, task3);
        Set<TaskDependency> dependencies = new HashSet<>();
        dependencies.add(new TaskDependency(task2, task1));
        dependencies.add(new TaskDependency(task3, task1));
        assertFalse(detector.hasCycle(tasks, dependencies));
    }

    @Test
    void testGraphWithSimpleCycle() {
        Set<Task> tasks = Set.of(task1, task2);
        Set<TaskDependency> dependencies = new HashSet<>();
        dependencies.add(new TaskDependency(task2, task1));
        dependencies.add(new TaskDependency(task1, task2));
        assertTrue(detector.hasCycle(tasks, dependencies));
    }

    @Test
    void testGraphWithComplexCycle() {
        Set<Task> tasks = Set.of(task1, task2, task3);
        Set<TaskDependency> dependencies = new HashSet<>();
        dependencies.add(new TaskDependency(task2, task1));
        dependencies.add(new TaskDependency(task3, task2));
        dependencies.add(new TaskDependency(task1, task3));
        assertTrue(detector.hasCycle(tasks, dependencies));
    }

    @Test
    void testGraphWithSelfCycle() {
        Set<Task> tasks = Set.of(task1);
        Set<TaskDependency> dependencies = new HashSet<>();
        dependencies.add(new TaskDependency(task1, task1));
        assertTrue(detector.hasCycle(tasks, dependencies));
    }
}