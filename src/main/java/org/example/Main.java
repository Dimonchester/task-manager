package org.example;

import model.Task;
import algorithm.EDFScheduler;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

public class Main {
    private static final EDFScheduler scheduler = new EDFScheduler();
    private static final Scanner scanner = new Scanner(System.in);
    private static final DateTimeFormatter dateFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");

    public static void main(String[] args) {
        System.out.println("Task Manager with EDF Scheduling");
        System.out.println("--------------------------------");

        boolean running = true;
        while (running) {
            printMenu();
            String choice = scanner.nextLine().trim();

            switch (choice) {
                case "1":
                    addTask();
                    break;
                case "2":
                    processTasks();
                    break;
                case "3":
                    viewAllTasks();
                    break;
                case "4":
                    running = false;
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        System.out.println("Exiting Task Manager. Goodbye!");
    }

    private static void printMenu() {
        System.out.println("\nMenu:");
        System.out.println("1. Add new task");
        System.out.println("2. Process tasks (EDF order)");
        System.out.println("3. View all tasks");
        System.out.println("4. Exit");
        System.out.print("Select an option: ");
    }

    private static void addTask() {
        System.out.println("\nAdd New Task");
        System.out.println("------------");

        try {
            // Ввод ID с валидацией
            int id;
            while (true) {
                try {
                    System.out.print("Enter task ID: ");
                    id = Integer.parseInt(scanner.nextLine());
                    break;
                } catch (NumberFormatException e) {
                    System.out.println("Error: Invalid ID format. Please enter a number.");
                }
            }

            System.out.print("Enter task title: ");
            String title = scanner.nextLine();

            // Ввод даты с валидацией
            LocalDate deadline;
            while (true) {
                try {
                    System.out.print("Enter deadline (YYYY-MM-DD): ");
                    String dateInput = scanner.nextLine();
                    deadline = LocalDate.parse(dateInput, dateFormatter);
                    break;
                } catch (DateTimeParseException e) {
                    System.out.println("Error: Invalid date format. Please use YYYY-MM-DD.");
                }
            }

            Task task = new Task(id, title, deadline);

            System.out.print("Enter description (optional): ");
            task.setDescription(scanner.nextLine());

            System.out.print("Add tags (comma separated, optional): ");
            String[] tags = scanner.nextLine().split(",");
            for (String tag : tags) {
                if (!tag.trim().isEmpty()) {
                    task.addTag(tag.trim());
                }
            }

            scheduler.addTask(task);
            System.out.println("Task added successfully!");

        } catch (Exception e) {
            System.out.println("Unexpected error: " + e.getMessage());
        }
    }

    private static void processTasks() {
        System.out.println("\nProcessing Tasks (EDF Order)");
        System.out.println("---------------------------");

        if (!scheduler.hasTasks()) {
            System.out.println("No tasks to process.");
            return;
        }

        while (scheduler.hasTasks()) {
            Task nextTask = scheduler.getNextTask();
            System.out.println("\nProcessing Task:");
            System.out.println("ID: " + nextTask.getId());
            System.out.println("Title: " + nextTask.getTitle());
            System.out.println("Deadline: " + nextTask.getDeadline());
            System.out.println("Description: " +
                    (nextTask.getDescription() != null ? nextTask.getDescription() : "None"));
            System.out.println("Tags: " +
                    (nextTask.getTags().isEmpty() ? "None" : String.join(", ", nextTask.getTags())));


            System.out.print("\nPress Enter to continue to next task...");
            scanner.nextLine();
        }

        System.out.println("\nAll tasks processed!");
    }

    private static void viewAllTasks() {
        System.out.println("\nAll Tasks (Ordered by Deadline)");
        System.out.println("-------------------------------");

        if (!scheduler.hasTasks()) {
            System.out.println("No tasks available.");
            return;
        }

        // Создаем временную копию для отображения без удаления
        EDFScheduler tempScheduler = new EDFScheduler();
        for (Task task : scheduler.getAllTasks()) {
            tempScheduler.addTask(task);
        }

        int counter = 1;
        while (tempScheduler.hasTasks()) {
            Task task = tempScheduler.getNextTask();
            System.out.println("\nTask #" + counter++);
            System.out.println("ID: " + task.getId());
            System.out.println("Title: " + task.getTitle());
            System.out.println("Deadline: " + task.getDeadline());
            System.out.println("Description: " +
                    (task.getDescription() != null ? task.getDescription() : "None"));
            System.out.println("Tags: " +
                    (task.getTags().isEmpty() ? "None" : String.join(", ", task.getTags())));
        }
    }
}
