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
        taskManager.addDependency(2, 1);

        List<Task> orderedTasks = taskManager.getTasksInTopologicalOrder();

        assertTrue(orderedTasks.indexOf(task1) < orderedTasks.indexOf(task2));
    }

    @Test
    void testCycleDetection() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1);

        assertThrows(IllegalStateException.class, () -> {
            taskManager.addDependency(1, 2);
        });
    }

    @Test
    void testDeleteTaskWithDependencies() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1);

        taskManager.deleteTask(1);

        assertNull(taskManager.getTask(1));
        assertNotNull(taskManager.getTask(2));

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

        taskManager.addDependency(2, 1);
        taskManager.addDependency(3, 2);

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

        taskManager.addDependency(3, 2);
        taskManager.addDependency(2, 1);

        List<Task> ordered = taskManager.getTasksInTopologicalOrder();

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
        taskManager.addTask(task1);
        taskManager.addTask(task2);

        List<Task> suggestions = taskManager.suggestTasksByTitlePrefix("Co");
        assertEquals(1, suggestions.size());
        assertEquals(task1, suggestions.get(0));

        assertTrue(taskManager.suggestTasksByTitlePrefix("xyz").isEmpty());
    }

    @Test
    void testEdfScheduling() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        taskManager.addTask(task4);

        List<Task> scheduled = taskManager.getScheduledTasksByEDF();
        assertEquals(4, scheduled.size());
        assertEquals(task4, scheduled.get(0));
        assertEquals(task1, scheduled.get(1));
        assertEquals(task2, scheduled.get(2));
        assertEquals(task3, scheduled.get(3));
    }

    @Test
    void testSortByComparator() {
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task4);

        List<Task> sorted = taskManager.getTasksSortedByQuickSort(Comparator.comparing(Task::getTitle));

        assertEquals(3, sorted.size());
        assertEquals(task4, sorted.get(0));
        assertEquals(task1, sorted.get(1));
        assertEquals(task2, sorted.get(2));
    }
}