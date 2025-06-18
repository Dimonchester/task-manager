package service;

import model.Task;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

class TagFilterTest {
    private TagFilter tagFilter;
    private Task task1;
    private Task task2;

    @BeforeEach
    void setUp() {
        tagFilter = new TagFilter();
        
        task1 = new Task(1, "Task 1");
        task1.addTag("urgent");
        task1.addTag("important");
        
        task2 = new Task(2, "Task 2");
        task2.addTag("important");
    }

    @Test
    void testTagFiltering() {
        tagFilter.indexTask(task1);
        tagFilter.indexTask(task2);
        
        Set<Task> importantTasks = tagFilter.getTasksByTag("important");
        assertEquals(2, importantTasks.size());
        assertTrue(importantTasks.contains(task1));
        assertTrue(importantTasks.contains(task2));
        
        Set<Task> urgentTasks = tagFilter.getTasksByTag("urgent");
        assertEquals(1, urgentTasks.size());
        assertTrue(urgentTasks.contains(task1));
    }

    @Test
    void testRemoveTaskFromIndex() {
        tagFilter.indexTask(task1);
        tagFilter.indexTask(task2);
        tagFilter.removeTask(task1);
        
        Set<Task> importantTasks = tagFilter.getTasksByTag("important");
        assertEquals(1, importantTasks.size());
        assertTrue(importantTasks.contains(task2));
        
        assertTrue(tagFilter.getTasksByTag("urgent").isEmpty());
    }

}