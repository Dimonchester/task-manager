package service;

import guru.nidi.graphviz.attribute.Color;
import guru.nidi.graphviz.attribute.Label;
import guru.nidi.graphviz.attribute.Shape;
import guru.nidi.graphviz.attribute.Style;
import guru.nidi.graphviz.engine.Format;
import guru.nidi.graphviz.engine.Graphviz;
import guru.nidi.graphviz.model.Graph;
import guru.nidi.graphviz.model.Node;
import model.Task;
import model.TaskDependency;

import java.io.File;
import java.io.IOException;
import java.util.Set;

import static guru.nidi.graphviz.model.Factory.*;

public class GraphVisualizer {
    public static void visualizeDependencies(Set<Task> tasks, Set<TaskDependency> dependencies, String filename) {
        Graph graph = graph("task_dependencies").directed()
                .graphAttr().with("rankdir", "TB")
                .nodeAttr().with(Shape.RECTANGLE);

        for (Task task : tasks) {
            Node taskNode = node(taskNodeId(task))
                    .with(Label.of(task.getTitle() + " (ID: " + task.getId() + ")"),
                            Style.FILLED, Color.LIGHTBLUE);
            graph = graph.with(taskNode);
        }

        for (TaskDependency dependency : dependencies) {
            Node from = node(taskNodeId(dependency.getTask()));
            Node to = node(taskNodeId(dependency.getDependsOn()));
            graph = graph.with(from.link(to));
        }

        try {
            Graphviz.fromGraph(graph)
                    .width(1000)
                    .render(Format.PNG)
                    .toFile(new File(filename + ".png"));
        } catch (IOException e) {
            System.err.println("Error when creating the graph: " + e.getMessage());
        }
    }

    private static String taskNodeId(Task task) {
        return "task_" + task.getId();
    }
}