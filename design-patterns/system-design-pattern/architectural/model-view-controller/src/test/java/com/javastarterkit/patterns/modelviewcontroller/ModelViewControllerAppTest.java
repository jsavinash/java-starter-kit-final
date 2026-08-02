package com.javastarterkit.patterns.modelviewcontroller;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

import com.javastarterkit.patterns.modelviewcontroller.controller.TaskController;
import com.javastarterkit.patterns.modelviewcontroller.exception.TaskNotFoundException;
import com.javastarterkit.patterns.modelviewcontroller.model.Task;
import com.javastarterkit.patterns.modelviewcontroller.model.TaskList;
import com.javastarterkit.patterns.modelviewcontroller.view.ConsoleTaskView;
import com.javastarterkit.patterns.modelviewcontroller.view.HtmlTaskView;
import com.javastarterkit.patterns.modelviewcontroller.view.TaskView;
import java.util.List;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Comprehensive test suite for the Model-View-Controller (MVC) pattern.
 *
 * <p>Covers: model validation, observer notification, controller behavior,
 * multiple views, error handling, and concurrency.
 */
@DisplayName("Model-View-Controller Tests")
class ModelViewControllerAppTest {

    // =========================================================================
    // MODEL VALIDATION
    // =========================================================================

    @Test
    @DisplayName("Task rejects null description")
    void taskRejectsNullDescription() {
        assertThatThrownBy(() -> new Task(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Task description must not be null");
    }

    @Test
    @DisplayName("Task rejects blank description")
    void taskRejectsBlankDescription() {
        assertThatThrownBy(() -> new Task("   "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task description must not be blank");
    }

    @Test
    @DisplayName("Task generates unique IDs")
    void taskGeneratesUniqueIds() {
        Task task1 = new Task("First");
        Task task2 = new Task("Second");
        assertThat(task1.id()).isNotEqualTo(task2.id());
    }

    @Test
    @DisplayName("Task can be completed")
    void taskCanBeCompleted() {
        Task task = new Task("Buy groceries");
        assertThat(task.isCompleted()).isFalse();
        task.complete();
        assertThat(task.isCompleted()).isTrue();
    }

    // =========================================================================
    // MODEL BEHAVIOR
    // =========================================================================

    @Test
    @DisplayName("TaskList adds tasks and returns immutable snapshot")
    void taskListAddsTasks() {
        TaskList model = new TaskList();
        Task task = model.addTask("Buy groceries");

        assertThat(model.size()).isEqualTo(1);
        assertThat(model.completedCount()).isZero();
        assertThat(model.tasks()).containsExactly(task);
    }

    @Test
    @DisplayName("TaskList rejects blank task descriptions")
    void taskListRejectsBlankDescription() {
        TaskList model = new TaskList();
        assertThatThrownBy(() -> model.addTask("  "))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Task description must not be blank");
    }

    @Test
    @DisplayName("TaskList completes task by index")
    void taskListCompletesByIndex() {
        TaskList model = new TaskList();
        model.addTask("Task 1");
        model.addTask("Task 2");

        Task completed = model.completeTask(0);

        assertThat(completed.isCompleted()).isTrue();
        assertThat(model.completedCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("TaskList throws TaskNotFoundException for invalid index")
    void taskListThrowsForInvalidIndex() {
        TaskList model = new TaskList();
        assertThatThrownBy(() -> model.completeTask(5))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task not found at index: 5");
    }

    @Test
    @DisplayName("TaskList completes task by ID")
    void taskListCompletesById() {
        TaskList model = new TaskList();
        Task task = model.addTask("Task 1");

        Task completed = model.completeTaskById(task.id());

        assertThat(completed.isCompleted()).isTrue();
        assertThat(model.completedCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("TaskList throws TaskNotFoundException for unknown ID")
    void taskListThrowsForUnknownId() {
        TaskList model = new TaskList();
        assertThatThrownBy(() -> model.completeTaskById("unknown-id"))
                .isInstanceOf(TaskNotFoundException.class)
                .hasMessage("Task not found: unknown-id");
    }

    @Test
    @DisplayName("TaskList returns defensive copy of tasks")
    void taskListReturnsDefensiveCopy() {
        TaskList model = new TaskList();
        model.addTask("Task 1");

        List<Task> tasks = model.tasks();
        assertThatThrownBy(() -> tasks.add(new Task("Task 2")))
                .isInstanceOf(UnsupportedOperationException.class);
    }

    // =========================================================================
    // OBSERVER NOTIFICATION
    // =========================================================================

    @Test
    @DisplayName("TaskList notifies observers on add")
    void taskListNotifiesObserversOnAdd() {
        TaskList model = new TaskList();
        AtomicInteger notifications = new AtomicInteger();
        TaskView view = new TaskView() {
            @Override
            public void render(TaskList m) {
            }

            @Override
            public void onModelChanged(TaskList m) {
                notifications.incrementAndGet();
            }
        };
        model.addObserver(view);

        model.addTask("Task 1");
        model.addTask("Task 2");

        assertThat(notifications.get()).isEqualTo(2);
    }

    @Test
    @DisplayName("TaskList notifies observers on complete")
    void taskListNotifiesObserversOnComplete() {
        TaskList model = new TaskList();
        AtomicInteger notifications = new AtomicInteger();
        TaskView view = new TaskView() {
            @Override
            public void render(TaskList m) {
            }

            @Override
            public void onModelChanged(TaskList m) {
                notifications.incrementAndGet();
            }
        };
        model.addObserver(view);
        model.addTask("Task 1");

        model.completeTask(0);

        assertThat(notifications.get()).isEqualTo(2); // add + complete
    }

    @Test
    @DisplayName("TaskList stops notifying removed observers")
    void taskListStopsNotifyingRemovedObservers() {
        TaskList model = new TaskList();
        AtomicInteger notifications = new AtomicInteger();
        TaskView view = new TaskView() {
            @Override
            public void render(TaskList m) {
            }

            @Override
            public void onModelChanged(TaskList m) {
                notifications.incrementAndGet();
            }
        };
        model.addObserver(view);
        model.addTask("Task 1");

        model.removeObserver(view);
        model.addTask("Task 2");

        assertThat(notifications.get()).isEqualTo(1);
    }

    // =========================================================================
    // CONTROLLER BEHAVIOR
    // =========================================================================

    @Test
    @DisplayName("Controller adds tasks through the model")
    void controllerAddsTasks() {
        TaskList model = new TaskList();
        TaskController controller = new TaskController(model);

        Task task = controller.addTask("Buy groceries");

        assertThat(controller.taskCount()).isEqualTo(1);
        assertThat(controller.listTasks()).containsExactly(task);
    }

    @Test
    @DisplayName("Controller completes tasks by index")
    void controllerCompletesByIndex() {
        TaskList model = new TaskList();
        TaskController controller = new TaskController(model);
        controller.addTask("Task 1");
        controller.addTask("Task 2");

        Task completed = controller.completeTask(0);

        assertThat(completed.isCompleted()).isTrue();
        assertThat(controller.completedCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Controller completes tasks by ID")
    void controllerCompletesById() {
        TaskList model = new TaskList();
        TaskController controller = new TaskController(model);
        Task task = controller.addTask("Task 1");

        Task completed = controller.completeTaskById(task.id());

        assertThat(completed.isCompleted()).isTrue();
        assertThat(controller.completedCount()).isEqualTo(1);
    }

    @Test
    @DisplayName("Controller rejects null model")
    void controllerRejectsNullModel() {
        assertThatThrownBy(() -> new TaskController(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Model must not be null");
    }

    // =========================================================================
    // MULTIPLE VIEWS
    // =========================================================================

    @Test
    @DisplayName("Multiple views render the same model")
    void multipleViewsRenderSameModel() {
        TaskList model = new TaskList();
        ConsoleTaskView consoleView = new ConsoleTaskView();
        HtmlTaskView htmlView = new HtmlTaskView();
        model.addObserver(consoleView);
        model.addObserver(htmlView);

        model.addTask("Buy groceries");
        model.addTask("Write report");
        model.completeTask(0);

        // Both views should render without throwing
        consoleView.render(model);
        htmlView.render(model);

        assertThat(model.size()).isEqualTo(2);
        assertThat(model.completedCount()).isEqualTo(1);
    }

    // =========================================================================
    // CONCURRENCY
    // =========================================================================

    @Test
    @DisplayName("TaskList handles 100 concurrent task additions safely")
    void taskListHandlesConcurrentAdditions() throws InterruptedException {
        int threadCount = 100;
        TaskList model = new TaskList();
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            final int index = i;
            executor.submit(() -> {
                try {
                    model.addTask("Task " + index);
                } finally {
                    latch.countDown();
                }
            });
        }

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();
        assertThat(model.size()).isEqualTo(threadCount);
    }

    @Test
    @DisplayName("TaskList handles concurrent observer notifications safely")
    void taskListHandlesConcurrentNotifications() throws InterruptedException {
        int threadCount = 50;
        TaskList model = new TaskList();
        AtomicInteger notifications = new AtomicInteger();
        TaskView view = new TaskView() {
            @Override
            public void render(TaskList m) {
            }

            @Override
            public void onModelChanged(TaskList m) {
                notifications.incrementAndGet();
            }
        };
        model.addObserver(view);

        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    model.addTask("Task");
                } finally {
                    latch.countDown();
                }
            });
        }

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();
        assertThat(notifications.get()).isEqualTo(threadCount);
    }

    // =========================================================================
    // END-TO-END
    // =========================================================================

    @Test
    @DisplayName("Demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        ModelViewControllerApp.demonstrate();
    }
}