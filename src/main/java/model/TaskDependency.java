package model;

public class TaskDependency {
    private final Task task;
    private final Task dependsOn;

    public TaskDependency(Task task, Task dependsOn) {
        this.task = task;
        this.dependsOn = dependsOn;
    }

    public Task getTask() { return task; }
    public Task getDependsOn() { return dependsOn; }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TaskDependency that = (TaskDependency) o;
        return task.equals(that.task) && dependsOn.equals(that.dependsOn);
    }
    @Override
    public int hashCode() {
        return 31 * task.hashCode() + dependsOn.hashCode();
    }
}