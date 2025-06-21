package model;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Task implements Comparable<Task> {
    private final int id;
    private String title;
    private String description;
    private LocalDateTime deadline;
    private final Set<String> tags = new HashSet<>();

    public Task(int id, String title, LocalDateTime deadline) {
        this.id = id;
        this.title = title;
        this.deadline = deadline;
    }

    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDateTime getDeadline() { return deadline; }
    public void setDeadline(LocalDateTime deadline) { this.deadline = deadline; }
    public Set<String> getTags() { return new HashSet<>(tags); }
    public void addTag(String tag) { tags.add(tag.toLowerCase()); }
    public void removeTag(String tag) { tags.remove(tag.toLowerCase()); }
    public boolean hasTag(String tag) { return tags.contains(tag.toLowerCase()); }

    @Override
    public int compareTo(Task other) {
        if (this.deadline == null && other.deadline == null) {
            return 0;
        }
        if (this.deadline == null) {
            return 1;
        }
        if (other.deadline == null) {
            return -1;
        }
        return this.deadline.compareTo(other.deadline);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", deadline=" + deadline +
                '}';
    }
}