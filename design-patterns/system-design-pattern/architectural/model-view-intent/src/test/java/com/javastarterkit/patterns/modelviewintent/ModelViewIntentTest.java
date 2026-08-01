package com.javastarterkit.patterns.modelviewintent;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.AddTask;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.CompleteTask;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.CounterState;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.Decrement;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.Increment;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.MviStore;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.Reset;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.TaskItem;
import com.javastarterkit.patterns.modelviewintent.ModelViewIntent.TaskState;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the Model-View-Intent pattern: intents are immutable,
 * reducers are pure functions, the store is the single source of truth, and
 * the view renders from immutable state without mutating it.
 */
class ModelViewIntentTest {

    @Test
    @DisplayName("counter reducer produces new immutable state")
    void counterReducerProducesNewState() {
        CounterState initial = new CounterState(0);

        CounterState incremented = ModelViewIntent.reduceCounter(initial, new Increment());
        assertEquals(1, incremented.count());

        CounterState decremented = ModelViewIntent.reduceCounter(initial, new Decrement());
        assertEquals(-1, decremented.count());

        CounterState reset = ModelViewIntent.reduceCounter(new CounterState(42), new Reset());
        assertEquals(0, reset.count());

        // Original state is never mutated
        assertEquals(0, initial.count());
    }

    @Test
    @DisplayName("task reducer adds and completes tasks immutably")
    void taskReducerAddsAndCompletes() {
        TaskState initial = new TaskState(List.of());

        TaskState withTasks = ModelViewIntent.reduceTask(initial, new AddTask("Buy groceries"));
        withTasks = ModelViewIntent.reduceTask(withTasks, new AddTask("Write report"));
        withTasks = ModelViewIntent.reduceTask(withTasks, new CompleteTask(0));

        assertEquals(2, withTasks.tasks().size());
        assertTrue(withTasks.tasks().get(0).completed());
        assertTrue(!withTasks.tasks().get(1).completed());

        // Original state is never mutated
        assertTrue(initial.tasks().isEmpty());
    }

    @Test
    @DisplayName("task reducer rejects invalid indices")
    void taskReducerRejectsInvalidIndices() {
        TaskState initialState = ModelViewIntent.reduceTask(
                new TaskState(List.of()), new AddTask("Task A"));

        assertThrows(IllegalArgumentException.class,
                () -> ModelViewIntent.reduceTask(initialState, new CompleteTask(5)));
        assertThrows(IllegalArgumentException.class,
                () -> ModelViewIntent.reduceTask(initialState, new CompleteTask(-1)));
    }

    @Test
    @DisplayName("store dispatches intents and updates state")
    void storeDispatchesIntents() {
        MviStore<CounterState, com.javastarterkit.patterns.modelviewintent.ModelViewIntent.CounterIntent> store =
                new MviStore<>(new CounterState(0), ModelViewIntent::reduceCounter);

        store.dispatch(new Increment());
        store.dispatch(new Increment());
        store.dispatch(new Decrement());

        assertEquals(1, store.state().count());
    }

    @Test
    @DisplayName("store notifies observers on every state change")
    void storeNotifiesObservers() {
        MviStore<CounterState, com.javastarterkit.patterns.modelviewintent.ModelViewIntent.CounterIntent> store =
                new MviStore<>(new CounterState(0), ModelViewIntent::reduceCounter);

        List<Integer> observedCounts = new ArrayList<>();
        store.addObserver(state -> observedCounts.add(state.count()));

        store.dispatch(new Increment());
        store.dispatch(new Increment());
        store.dispatch(new Reset());

        assertEquals(List.of(1, 2, 0), observedCounts);
    }

    @Test
    @DisplayName("intents are immutable by design")
    void intentsAreImmutable() {
        // Records are immutable; just verify they work as expected.
        Increment inc1 = new Increment();
        Increment inc2 = new Increment();
        assertEquals(inc1, inc2);

        AddTask task = new AddTask("Task");
        assertEquals("Task", task.description());
    }

    @Test
    @DisplayName("state records provide copyWith for immutable updates")
    void stateProvidesCopyWith() {
        CounterState state = new CounterState(1);
        CounterState updated = state.copyWith(5);

        assertEquals(1, state.count());  // original unchanged
        assertEquals(5, updated.count());
    }

    @Test
    @DisplayName("task items can be completed immutably")
    void taskItemsComplete() {
        TaskItem task = new TaskItem("Buy", false);
        TaskItem completed = task.complete();

        assertEquals("Buy", task.description());
        assertTrue(!task.completed());  // original unchanged
        assertTrue(completed.completed());
        assertEquals("Buy", completed.description());
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        ModelViewIntent.demonstrate();
    }
}