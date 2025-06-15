package model;

import java.time.LocalDate;
import java.util.HashSet;
import java.util.Set;

public class Task {
    private final int id;
    private String title;
    private String description;
    private LocalDate deadline;
    private final Set<String> tags = new HashSet<>();

    public Task(int id, String title) {
        this.id = id;
        this.title = title;
    }

    // Добавьте эти методы:
    public int getId() { return id; }
    public String getTitle() { return title; }
    public void setTitle(String title) { this.title = title; }
    public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
    public LocalDate getDeadline() { return deadline; }
    public void setDeadline(LocalDate deadline) { this.deadline = deadline; }
    public Set<String> getTags() { return new HashSet<>(tags); }
    public void addTag(String tag) { tags.add(tag.toLowerCase()); }
    public void removeTag(String tag) { tags.remove(tag.toLowerCase()); }
    public boolean hasTag(String tag) { return tags.contains(tag.toLowerCase()); }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Task task = (Task) o;
        return id == task.id;
    }

    @Override
    public int hashCode() {
        return id;
    }
}