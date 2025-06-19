import model.Task;
import algorithm.EDFScheduler;
import java.util.Date;

public class Main {
    public static void main(String[] args) {
        EDFScheduler scheduler = new EDFScheduler();

        // Добавляем задачи с разными дедлайнами
        Task task1 = new Task("T1", new Date().getTime() + 5000, 1);
        Task task2 = new Task("T2", new Date().getTime() + 3000, 2);
        Task task3 = new Task("T3", new Date().getTime() + 7000, 3);

        scheduler.addTask(task1);
        scheduler.addTask(task2);
        scheduler.addTask(task3);

        // Обрабатываем задачи в порядке EDF
        while (scheduler.hasTasks()) {
            Task nextTask = scheduler.getNextTask();
            System.out.println("Processing: " + nextTask.getId());
        }
    }
}