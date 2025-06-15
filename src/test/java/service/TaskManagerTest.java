package service;

import model.Task;
import model.TaskDependency;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TaskManagerTest {
    private TaskManager taskManager;

    @BeforeEach
    void setUp() {
        taskManager = new TaskManager();
    }

    @Test
    void testAddAndRetrieveTask() {
        Task task = new Task(1, "Implement feature");
        taskManager.addTask(task);
        
        assertEquals(task, taskManager.getTask(1));
    }

    @Test
    void testTaskDependencies() {
        Task task1 = new Task(1, "Task 1");
        Task task2 = new Task(2, "Task 2");
        
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1); 

        List<Task> orderedTasks = taskManager.getTasksInOrder();
        assertEquals(2, orderedTasks.size());
        assertTrue(orderedTasks.indexOf(task2) < orderedTasks.indexOf(task1));
    }
    @Test
    void testCycleDetection() {
        Task task1 = new Task(1, "Task 1");
        Task task2 = new Task(2, "Task 2");
        
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1);
        
        assertThrows(IllegalStateException.class, () -> {
            taskManager.addDependency(1, 2); // Создаем цикл
        });
    }

    @Test
    void testDeleteTaskWithDependencies() {
        Task task1 = new Task(1, "Task 1");
        Task task2 = new Task(2, "Task 2");
        
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addDependency(2, 1);
        taskManager.deleteTask(1);
        
        assertNull(taskManager.getTask(1));
        assertTrue(taskManager.getDependencies().isEmpty());
    }
    @Test
    void testUpdateTask() {
        Task task = new Task(1, "Original");
        taskManager.addTask(task);
        
        task.setTitle("Updated");
        taskManager.updateTask(task);
        
        assertEquals("Updated", taskManager.getTask(1).getTitle());
    }
    @Test
    void testComplexCycleDetection() {
        Task task1 = new Task(1, "Task 1");
        Task task2 = new Task(2, "Task 2");
        Task task3 = new Task(3, "Task 3");
        
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        
        taskManager.addDependency(2, 1);
        taskManager.addDependency(3, 2);
        
        assertThrows(IllegalStateException.class, () -> {
            taskManager.addDependency(1, 3); // Замыкаем цикл
        });
    }
    @Test
    void testAddNullTask() {
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addTask(null);
        });
    }

    @Test
    void testNonexistentDependency() {
        Task task = new Task(1, "Task");
        taskManager.addTask(task);
        
        assertThrows(IllegalArgumentException.class, () -> {
            taskManager.addDependency(1, 999); // Несуществующая зависимость
        });
    }
    @Test
    void testMultipleDependencies() {
        Task task1 = new Task(1, "Task 1");
        Task task2 = new Task(2, "Task 2");
        Task task3 = new Task(3, "Task 3");
        
        taskManager.addTask(task1);
        taskManager.addTask(task2);
        taskManager.addTask(task3);
        
        taskManager.addDependency(2, 1); // task2 зависит от task1
        taskManager.addDependency(3, 2); // task3 зависит от task2
        
        List<Task> ordered = taskManager.getTasksInOrder();
        
        // Проверяем, что зависимые задачи идут ПЕРЕД теми, от которых зависят
        assertTrue(ordered.indexOf(task3) < ordered.indexOf(task2));
        assertTrue(ordered.indexOf(task2) < ordered.indexOf(task1));
    }
}