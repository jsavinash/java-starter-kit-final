package com.javastarterkit.patterns.modelviewcontroller;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.modelviewcontroller.ModelViewController.ConsoleTaskView;
import com.javastarterkit.patterns.modelviewcontroller.ModelViewController.HtmlTaskView;
import com.javastarterkit.patterns.modelviewcontroller.ModelViewController.Task;
import com.javastarterkit.patterns.modelviewcontroller.ModelViewController.TaskController;
import com.javastarterkit.patterns.modelviewcontroller.ModelViewController.TaskList;
import com.javastarterkit.patterns.modelviewcontroller.ModelViewController.TaskView;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the Model-View-Controller pattern: the model manages
 * data and business rules independently, views render the model, and the
 * controller coordinates user input and model updates.
 */
class ModelViewControllerTest {

    @Test
    @DisplayName("model manages tasks and business rules independently")
    void modelManagesTasks() {
        TaskList model = new TaskList();
        model.addTask("Buy groceries");
        model.addTask("Write report");

        assertEquals(2, model.size());
        assertEquals(0, model.completedCount());

        model.completeTask(0);
        assertEquals(1, model.completedCount());
        assertTrue(model.tasks().get(0).isCompleted());
        assertTrue(!model.tasks().get(1).isCompleted());
    }

    @Test
    @DisplayName("model rejects blank task descriptions")
    void modelRejectsBlankDescriptions() {
        TaskList model = new TaskList();
        assertThrows(IllegalArgumentException.class, () -> model.addTask(""));
        assertThrows(IllegalArgumentException.class, () -> model.addTask("   "));
    }

    @Test
    @DisplayName("model rejects invalid task indices")
    void modelRejectsInvalidIndices() {
        TaskList model = new TaskList();
        model.addTask("Task A");

        assertThrows(IndexOutOfBoundsException.class, () -> model.completeTask(-1));
        assertThrows(IndexOutOfBoundsException.class, () -> model.completeTask(5));
    }

    @Test
    @DisplayName("model notifies observers when it changes")
    void modelNotifiesObservers() {
        TaskList model = new TaskList();
        List<String> notifications = new ArrayList<>();

        TaskView observer = new TaskView() {
            @Override
            public void render(TaskList model) {
            }

            @Override
            public void onModelChanged(TaskList model) {
                notifications.add("changed, size=" + model.size());
            }
        };

        model.addObserver(observer);
        model.addTask("Task A");
        model.addTask("Task B");
        model.completeTask(0);

        assertEquals(3, notifications.size());
        assertEquals("changed, size=1", notifications.get(0));
        assertEquals("changed, size=2", notifications.get(1));
        assertEquals("changed, size=2", notifications.get(2));
    }

    @Test
    @DisplayName("controller coordinates user actions and model updates")
    void controllerCoordinatesUserActions() {
        TaskList model = new TaskList();
        TaskController controller = new TaskController(model);

        controller.addTask("Buy groceries");
        controller.addTask("Write report");
        controller.completeTask(1);

        List<Task> tasks = controller.listTasks();
        assertEquals(2, tasks.size());
        assertTrue(!tasks.get(0).isCompleted());
        assertTrue(tasks.get(1).isCompleted());
    }

    @Test
    @DisplayName("multiple views render the same model")
    void multipleViewsRenderSameModel() {
        TaskList model = new TaskList();
        model.addTask("Task A");
        model.addTask("Task B");
        model.completeTask(0);

        ConsoleTaskView consoleView = new ConsoleTaskView();
        HtmlTaskView htmlView = new HtmlTaskView();

        // Both views should render without throwing and reflect model state
        consoleView.render(model);
        htmlView.render(model);

        assertEquals(2, model.size());
        assertEquals(1, model.completedCount());
    }

    @Test
    @DisplayName("tasks toggle completion state correctly")
    void tasksToggleCompletion() {
        Task task = new Task("Task A");
        assertTrue(!task.isCompleted());

        task.complete();
        assertTrue(task.isCompleted());

        assertTrue(task.toString().startsWith("[x]"));
        assertTrue(new Task("Pending").toString().startsWith("[ ]"));
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        ModelViewController.demonstrate();
    }
}