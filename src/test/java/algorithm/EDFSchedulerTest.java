package algorithm;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

class EDFSchedulerTest {

    private EDFScheduler scheduler;
    private Task task1, task2, task3, task4;

    @BeforeEach
    void setUp() {
        scheduler = new EDFScheduler();
        task1 = new Task(1, "Earlier Task", LocalDate.now().plusDays(1));
        task2 = new Task(2, "Later Task", LocalDate.now().plusDays(5));
        task3 = new Task(3, "Middle Task", LocalDate.now().plusDays(3));
        task4 = new Task(4, "No Deadline Task", null);
    }

    @Test
    void testSchedulingOrder() {
        // Add tasks in random order
        scheduler.addTask(task2);
        scheduler.addTask(task1);
        scheduler.addTask(task4);
        scheduler.addTask(task3);

        assertTrue(scheduler.hasTasks());

        assertEquals(task1, scheduler.getNextTask());
        assertEquals(task3, scheduler.getNextTask());
        assertEquals(task2, scheduler.getNextTask());
        assertEquals(task4, scheduler.getNextTask());

        assertFalse(scheduler.hasTasks());
    }

    @Test
    void testHasTasks() {
        assertFalse(scheduler.hasTasks());
        scheduler.addTask(task1);
        assertTrue(scheduler.hasTasks());
        scheduler.getNextTask();
        assertFalse(scheduler.hasTasks());
    }
}