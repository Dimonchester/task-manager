package org.example;

import model.Task;
import service.TaskManager;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.*;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
    private static final TaskManager taskManager = new TaskManager();

    public static void main(String[] args) {
        addInitialData();

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1": addNewTask(); break;
                case "2": getTaskById(); break;
                case "3": updateExistingTask(); break;
                case "4": deleteTaskById(); break;
                case "5": addDependency(); break;
                case "6": showAllTasks(); break;
                case "7": showTasksInTopologicalOrder(); break;
                case "8": showTasksScheduledByEDF(); break;
                case "9": suggestTasksByTitle(); break;
                case "10": findTasksByTag(); break;
                case "11": showTasksSortedByQuickSort_Id(); break;
                case "12": showTasksSortedByQuickSort_Title(); break;
                case "13": running = false; break;
                default: System.out.println("Invalid option. Please try again.");
            }
        }
        System.out.println("Exiting Task Manager");
    }

    private static void printMenu() {
        System.out.println("\n--- Main Menu ---");
        System.out.println("1. Add new task");
        System.out.println("2. Find task by ID (cache demo)");
        System.out.println("3. Update task");
        System.out.println("4. Delete task");
        System.out.println("5. Add dependency between tasks");
        System.out.println("--- Viewing and Algorithms ---");
        System.out.println("6. Show all tasks");
        System.out.println("7. Show tasks in execution order (Topological Sort)");
        System.out.println("8. Show tasks scheduled by EDF (sorted by deadline)");
        System.out.println("9. Find tasks by title prefix (Trie demo)");
        System.out.println("10. Find tasks by tag");
        System.out.println("11. Demo QuickSort: sort by ID");
        System.out.println("12. Demo QuickSort: sort by Title");
        System.out.println("13. Exit");
        System.out.print("Select an option: ");
    }

    private static void addNewTask() {
        try {
            System.out.print("Enter task ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter task title: ");
            String title = scanner.nextLine();
            System.out.print("Enter deadline yyyy-MM-dd HH:mm or leave empty: ");
            String deadlineStr = scanner.nextLine();
            LocalDateTime deadline = deadlineStr.isEmpty() ? null : LocalDateTime.parse(deadlineStr, dateFormatter);

            System.out.print("Enter comma-separated tags (e.g., 'urgent,important'): ");
            String[] tagsArr = scanner.nextLine().split(",");
            Set<String> tags = new HashSet<>();
            for (String tag : tagsArr) {
                if (!tag.isBlank()) tags.add(tag.trim());
            }

            taskManager.addNewTask(id, title, deadline, tags);
            System.out.println("Task added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        } catch (DateTimeParseException e) {
            System.out.println("Error: Invalid date format. Use yyyy-MM-dd HH:mm.");
        } catch (Exception e) {
            System.out.println("An error occurred: " + e.getMessage());
        }
    }

    private static void showTasksSortedByQuickSort_Id() {
        System.out.println("\n--- Tasks sorted by QuickSort (by ID) ---");
        Comparator<Task> idComparator = Comparator.comparing(Task::getId);
        List<Task> tasks = taskManager.getTasksSortedByQuickSort(idComparator);
        tasks.forEach(System.out::println);
    }

    private static void showTasksSortedByQuickSort_Title() {
        System.out.println("\n--- Tasks sorted by QuickSort (by Title) ---");
        Comparator<Task> titleComparator = Comparator.comparing(Task::getTitle);
        List<Task> tasks = taskManager.getTasksSortedByQuickSort(titleComparator);
        tasks.forEach(System.out::println);
    }

    private static void getTaskById() {
        try {
            System.out.print("Enter task ID to search: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println("Searching for task... (repeated calls for same ID will demonstrate cache)");
            Task task = taskManager.getTask(id);
            if (task != null) {
                System.out.println("Found task: " + task);
            } else {
                System.out.println("Task with ID " + id + " not found.");
            }
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        }
    }

    private static void updateExistingTask() {
        try {
            System.out.print("Enter task ID to update: ");
            int id = Integer.parseInt(scanner.nextLine());
            Task task = taskManager.getTask(id);
            if(task == null) {
                System.out.println("Task with this ID not found.");
                return;
            }

            System.out.print("Enter new title (or leave empty): ");
            String title = scanner.nextLine();
            if(!title.isBlank()) task.setTitle(title);

            taskManager.updateTask(task);
            System.out.println("Task updated: " + task);
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        }
    }

    private static void deleteTaskById() {
        try {
            System.out.print("Enter task ID to delete: ");
            int id = Integer.parseInt(scanner.nextLine());
            taskManager.deleteTask(id);
            System.out.println("Task with ID " + id + " deleted (if existed).");
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        }
    }

    private static void addDependency() {
        try {
            System.out.print("Enter dependent task ID: ");
            int taskId = Integer.parseInt(scanner.nextLine());
            System.out.print("Enter ID of task it depends on: ");
            int dependsOnId = Integer.parseInt(scanner.nextLine());
            taskManager.addDependency(taskId, dependsOnId);
            System.out.println("Dependency added successfully!");
        } catch (NumberFormatException e) {
            System.out.println("Error: ID must be a number.");
        } catch (Exception e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private static void showAllTasks() {
        List<Task> tasks = taskManager.getAllTasks();
        System.out.println("\n--- All Tasks ---");
        if (tasks.isEmpty()) {
            System.out.println("Task list is empty.");
        } else {
            tasks.forEach(task -> {
                System.out.println("id: " + task.getId());
                System.out.println("title: " + task.getTitle());
                System.out.println("deadline: " + task.getDeadline());
                System.out.println();
            });
        }
    }

    private static void showTasksInTopologicalOrder() {
        System.out.println("\n--- Tasks in execution order (Topological Sort) ---");
        try {
            List<Task> tasks = taskManager.getTasksInTopologicalOrder();
            tasks.forEach(task -> {
                System.out.println("id: " + task.getId());
                System.out.println("title: " + task.getTitle());
                System.out.println("deadline: " + task.getDeadline());
                System.out.println();
            });
        } catch (Exception e) {
            System.out.println("Sorting error: " + e.getMessage());
        }
    }

    private static void showTasksScheduledByEDF() {
        System.out.println("\n--- Tasks scheduled by EDF (sorted by deadline) ---");
        List<Task> tasks = taskManager.getScheduledTasksByEDF();
        tasks.forEach(task -> {
            System.out.println("id: " + task.getId());
            System.out.println("title: " + task.getTitle());
            System.out.println("deadline: " + task.getDeadline());
            System.out.println();
        });
    }

    private static void suggestTasksByTitle() {
        System.out.print("Enter title prefix to search: ");
        String prefix = scanner.nextLine();
        System.out.println("\n--- Found tasks (Trie demo) ---");
        List<Task> suggestions = taskManager.suggestTasksByTitlePrefix(prefix);
        if (suggestions.isEmpty()) {
            System.out.println("No tasks found with this prefix.");
        } else {
            suggestions.forEach(task -> {
                System.out.println("id: " + task.getId());
                System.out.println("title: " + task.getTitle());
                System.out.println("deadline: " + task.getDeadline());
                System.out.println();
            });
        }
    }

    private static void findTasksByTag() {
        System.out.print("Enter tag to search: ");
        String tag = scanner.nextLine();
        System.out.println("\n--- Tasks with tag '" + tag + "' ---");
        Set<Task> tasks = taskManager.getTasksByTag(tag);
        if (tasks.isEmpty()) {
            System.out.println("No tasks found with this tag.");
        } else {
            tasks.forEach(task -> {
                System.out.println("id: " + task.getId());
                System.out.println("title: " + task.getTitle());
                System.out.println("deadline: " + task.getDeadline());
                System.out.println();
            });
        }
    }

    private static void addInitialData() {
        Task t1 = new Task(1, "Design architecture", LocalDateTime.parse("2025-07-01T12:00"));
        t1.addTag("project");
        t1.addTag("architecture");
        taskManager.addTask(t1);

        Task t2 = new Task(2, "Implement core system", LocalDateTime.parse("2025-07-15T13:00"));
        t2.addTag("project");
        t2.addTag("development");
        taskManager.addTask(t2);

        Task t3 = new Task(3, "Write tests", LocalDateTime.parse("2025-07-20T14:00"));
        t3.addTag("project");
        t3.addTag("qa");
        taskManager.addTask(t3);

        Task t4 = new Task(4, "Prepare documentation", LocalDateTime.parse("2025-07-18T15:00"));
        t4.addTag("documentation");
        t4.addTag("ga");
        taskManager.addTask(t4);

        Task t5 = new Task(10, "Requirements analysis", LocalDateTime.parse("2025-06-25T12:00"));
        t5.addTag("project");
        t5.addTag("analysis");
        taskManager.addTask(t5);

        try {
            taskManager.addDependency(1, 10);
            taskManager.addDependency(2, 1);
            taskManager.addDependency(3, 2);
        } catch (Exception e) {
            System.out.println("Error adding initial dependencies: " + e.getMessage());
        }
    }
}