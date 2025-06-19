package model;

public class Task implements Comparable<Task> {
    private final String id;
    private final long deadline; // Unix timestamp в миллисекундах
    private final int priority;

    public Task(String id, long deadline, int priority) {
        this.id = id;
        this.deadline = deadline;
        this.priority = priority;
    }

    @Override
    public int compareTo(Task other) {
        int deadlineCompare = Long.compare(this.deadline, other.deadline);
        return deadlineCompare != 0 ? deadlineCompare :
                Integer.compare(other.priority, this.priority);
    }

    // Геттеры
    public String getId() { return id; }
    public long getDeadline() { return deadline; }
    public int getPriority() { return priority; }
}