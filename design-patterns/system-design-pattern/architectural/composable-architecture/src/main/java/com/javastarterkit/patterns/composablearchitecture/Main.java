package com.javastarterkit.patterns.composablearchitecture;

import com.javastarterkit.patterns.composablearchitecture.composition.OrderComposer;
import com.javastarterkit.patterns.composablearchitecture.core.store.Store;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.DeliveryAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.OrderAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.PizzaAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.OrderState;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaSize;
import com.javastarterkit.patterns.composablearchitecture.ui.models.Topping;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

/**
 * Main entry point demonstrating the complete end-to-end execution flow of the
 * composable architecture pattern through a pizza-ordering domain.
 *
 * <p>The demo shows how two independent feature components — pizza
 * configuration and delivery details — are composed into a single order store
 * without coupling, and how that store is safely driven concurrently.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class Main {

    private static final Logger log = LoggerFactory.getLogger(Main.class);

    private Main() {
        // Prevent instantiation — this is the application bootstrap.
    }

    /**
     * Application entry point.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        demonstrate();
    }

    /**
     * Runs the full demonstration: builds the composed store, dispatches
     * sequential actions, and then exercises concurrent dispatch to prove
     * thread-safety.
     */
    public static void demonstrate() {
        log.info("======================================================");
        log.info("Composable Architecture Pattern — Pizza Order Demo");
        log.info("======================================================");

        // --- Sequential end-to-end flow -----------------------------------
        Store<OrderState, OrderAction> store = OrderComposer.createStore();
        store.subscribe(state ->
                log.info("State transition -> revision={} {}", store.revision(), state));

        log.info("Initial state: {}", store.state());

        // Route actions to the pizza feature.
        store.dispatch(new OrderAction.Pizza(new PizzaAction.selectSize(PizzaSize.LARGE)));
        store.dispatch(new OrderAction.Pizza(new PizzaAction.toggleTopping(Topping.PEPPERONI)));
        store.dispatch(new OrderAction.Pizza(new PizzaAction.toggleTopping(Topping.MUSHROOMS)));
        store.dispatch(new OrderAction.Pizza(new PizzaAction.setQuantity(2)));

        // Route actions to the delivery feature.
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setName("Alice")));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setAddress("123 Main St")));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setCity("Springfield")));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setPhone("555-0100")));

        OrderState finalState = store.state();
        log.info("Final order: {}", finalState);
        log.info("Total price: ${} (ready={})",
                finalState.pizza().totalPrice(), finalState.isReadyToPlace());

        // --- Concurrent demonstration --------------------------------------
        log.info("---- Concurrent dispatch (thread-safety) ----");
        Store<OrderState, OrderAction> concurrentStore = OrderComposer.createStore();

        ExecutorService executor = Executors.newFixedThreadPool(4);
        for (int i = 1; i <= 20; i++) {
            final int idx = i;
            executor.submit(() -> {
                // Alternate between pizza and delivery actions to prove
                // multi-producer safety of the store.
                if (idx % 2 == 0) {
                    concurrentStore.dispatch(new OrderAction.Pizza(
                            new PizzaAction.setQuantity(idx)));
                } else {
                    concurrentStore.dispatch(new OrderAction.Delivery(
                            new DeliveryAction.setName("Customer-" + idx)));
                }
            });
        }
        executor.shutdown();
        try {
            if (!executor.awaitTermination(5, TimeUnit.SECONDS)) {
                log.warn("Executor did not terminate within timeout");
            }
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
            log.error("Interrupted while awaiting executor", ex);
        }

        log.info("Concurrent final state: {}", concurrentStore.state());
        log.info("Final revision: {}", concurrentStore.revision());

        log.info("======================================================");
        log.info("Composable architecture demonstration complete.");
        log.info("======================================================");
    }
}