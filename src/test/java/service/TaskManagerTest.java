package service;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private TaskManager taskManager;
    private Task task1, task2, task3, task4;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
        // Initialize common tasks with deadlines
        task1 = new Task(1, "Code feature", LocalDate.now().plusDays(5));
        task2 = new Task(2, "Write tests", LocalDate.now().plusDays(6));
        task3 = new Task(3, "Deploy to staging", LocalDate.now().plusDays(7));
        task4 = new Task(4, "Another Task", LocalDate.now().plusDays(1));
    }

    @Test
    void testAddAndRetrieveTask() {
        taskManager.addTask(task1);
        assertEquals(task1, taskManager.getTask(1));
        assertNull(taskManager.getTask(99));
    }

    @Test
    void testTaskDependencies() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        // task2 depends on task1
        taskManager.addDependency(2, 1);

        // Fixed: Method name is getTasksInTopologicalOrder
        List<Task> orderedTasks = taskManager.getTasksInTopologicalOrder();

        // Correct order: dependencies must be met first. So task1 must come before task2.
        // Fixed: Assertion logic was reversed.
        assertTrue(orderedTasks.indexOf(task1) < orderedTasks.indexOf(task2));
    }

    @Test
    void testCycleDetection() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1); // 2 -> 1

        // This should throw an exception because it creates a cycle (1 -> 2)
        assertThrows(IllegalStateException.class, () -> {
            taskManager.addDependency(1, 2);
        });
    }

    @Test
    void testDeleteTaskWithDependencies() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1);

        taskManager.deleteTask(1); // Delete task1

        assertNull(taskManager.getTask(1)); // Task1 should be gone
        assertNotNull(taskManager.getTask(2)); // Task2 should still exist

        // The dependency should be removed automatically. Topological sort should still work.
        List<Task> orderedTasks = taskManager.getTasksInTopologicalOrder();
        assertEquals(1, orderedTasks.size());
        assertEquals(task2, orderedTasks.get(0));
    }

    @Test
    void testUpdateTask() {
        taskManager.addTask(task1);
        task1.setTitle("Code awesome feature");
        taskManager.updateTask(task1);

        assertEquals("Code awesome feature", taskManager.getTask(1).getTitle());
    }

    @Test
    void testComplexCycleDetection() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.addDependency(2, 1); // 2 -> 1
        taskManager.addDependency(3, 2); // 3 -> 2

        // Creates a cycle 1 -> 3, completing 1 -> 3 -> 2 -> 1
        assertThrows(IllegalStateException.class, () -> {
            taskManager.addDependency(1, 3);
        });
    }

    @Test
    void testAddNullTask() {
        assertThrows(IllegalArgumentException.class, () -> taskManager.addTask(null));
    }

    @Test
    void testNonexistentDependency() {
        taskManager.addTask(task1);
        assertThrows(IllegalArgumentException.class, () -> taskManager.addDependency(1, 999));
    }

    @Test
    void testMultipleDependencies() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        taskManager.addDependency(3, 2); // task3 depends on task2
        taskManager.addDependency(2, 1); // task2 depends on task1

        // Expected order: 1, 2, 3
        List<Task> ordered = taskManager.getTasksInTopologicalOrder();

        // Fixed: Check correct order
        assertTrue(ordered.indexOf(task1) < ordered.indexOf(task2));
        assertTrue(ordered.indexOf(task2) < ordered.indexOf(task3));
    }

    @Test
    void testFilterByTag() {
        task1.addTag("work");
        task2.addTag("work");
        task3.addTag("deploy");
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);

        assertEquals(2, taskManager.getTasksByTag("work").size());
        assertEquals(1, taskManager.getTasksByTag("deploy").size());
        assertTrue(taskManager.getTasksByTag("personal").isEmpty());
    }

    @Test
    void testSuggestByTitlePrefix() {
        taskManager.addTask(task1); // "Code feature"
        taskManager.addTask(task2); // "Write tests"

        List<Task> suggestions = taskManager.suggestTasksByTitlePrefix("Co");
        assertEquals(1, suggestions.size());
        assertEquals(task1, suggestions.get(0));

        assertTrue(taskManager.suggestTasksByTitlePrefix("xyz").isEmpty());
    }

    @Test
    void testEdfScheduling() {
        taskManager.addTask(task1); // 5 days
        taskManager.addTask(task2); // 6 days
        taskManager.addTask(task3); // 7 days
        taskManager.addTask(task4); // 1 day

        List<Task> scheduled = taskManager.getScheduledTasks();
        assertEquals(4, scheduled.size());
        assertEquals(task4, scheduled.get(0)); // Earliest deadline
        assertEquals(task1, scheduled.get(1));
        assertEquals(task2, scheduled.get(2));
        assertEquals(task3, scheduled.get(3)); // Latest deadline
    }

    @Test
    void testSortByComparator() {
        taskManager.addTask(task1); // "Code feature"
        taskManager.addTask(task2); // "Write tests"
        taskManager.addTask(task4); // "Another Task"

        List<Task> sorted = taskManager.getTasksSortedBy(Comparator.comparing(Task::getTitle));

        assertEquals(3, sorted.size());
        assertEquals(task4, sorted.get(0)); // "Another Task"
        assertEquals(task1, sorted.get(1)); // "Code feature"
        assertEquals(task2, sorted.get(2)); // "Write tests"
    }
}