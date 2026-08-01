package com.javastarterkit.patterns.composablearchitecture;

import com.javastarterkit.patterns.composablearchitecture.composition.OrderComposer;
import com.javastarterkit.patterns.composablearchitecture.core.store.Store;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.OrderAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.PizzaAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.OrderState;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaSize;
import com.javastarterkit.patterns.composablearchitecture.ui.models.Topping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Verifies the thread-safety of the composed store under concurrent dispatch.
 *
 * <p>The store guards the read-modify-write cycle with a {@code ReentrantLock}
 * and uses immutable states, so concurrent producers must never corrupt state
 * or lose updates. These tests assert both of those properties.
 */
class StoreConcurrencyTest {

    private static final int THREAD_COUNT = 8;
    private static final int OPS_PER_THREAD = 100;

    @Test
    @DisplayName("concurrent dispatches increment revision by exactly total dispatches")
    void concurrentDispatches_incrementRevisionDeterministically() throws InterruptedException {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();

        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREAD_COUNT);

        for (int t = 0; t < THREAD_COUNT; t++) {
            final int threadId = t;
            executor.submit(() -> {
                awaitUninterruptibly(start);
                try {
                    for (int i = 0; i < OPS_PER_THREAD; i++) {
                        int quantity = threadId * OPS_PER_THREAD + i + 1;
                        store.dispatch(new OrderAction.Pizza(
                                new PizzaAction.setQuantity(quantity)));
                    }
                } finally {
                    done.countDown();
                }
            });
        }

        start.countDown();
        assertThat(done.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();

        int total = THREAD_COUNT * OPS_PER_THREAD;
        assertThat(store.revision()).isEqualTo(total);
        // The final quantity must be one of the dispatched values.
        int finalQuantity = store.state().pizza().quantity();
        assertThat(finalQuantity).isBetween(1, total);
    }

    @Test
    @DisplayName("concurrent reads observe a stable, consistent state snapshot")
    void concurrentReads_observeConsistentState() throws InterruptedException {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();

        // Seed the state fully for a stable read target.
        store.dispatch(new OrderAction.Pizza(new PizzaAction.selectSize(PizzaSize.LARGE)));
        store.dispatch(new OrderAction.Pizza(new PizzaAction.toggleTopping(Topping.PEPPERONI)));

        // Readers sample the state many times concurrently without mutating.
        ExecutorService executor = Executors.newFixedThreadPool(THREAD_COUNT);
        CountDownLatch start = new CountDownLatch(1);
        CountDownLatch done = new CountDownLatch(THREAD_COUNT);
        CopyOnWriteArrayList<Boolean> consistentSnapshots = new CopyOnWriteArrayList<>();

        for (int t = 0; t < THREAD_COUNT; t++) {
            executor.submit(() -> {
                awaitUninterruptibly(start);
                try {
                    for (int i = 0; i < OPS_PER_THREAD; i++) {
                        OrderState s = store.state();
                        consistentSnapshots.add(s.pizza().size() == PizzaSize.LARGE);
                    }
                } finally {
                    done.countDown();
                }
            });
        }

        start.countDown();
        assertThat(done.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();

        assertThat(consistentSnapshots).hasSize(THREAD_COUNT * OPS_PER_THREAD);
        assertThat(consistentSnapshots).allMatch(Boolean::booleanValue);
    }

    private static void awaitUninterruptibly(CountDownLatch latch) {
        boolean interrupted = false;
        while (true) {
            try {
                latch.await();
                return;
            } catch (InterruptedException e) {
                interrupted = true;
            }
        }
    }
}