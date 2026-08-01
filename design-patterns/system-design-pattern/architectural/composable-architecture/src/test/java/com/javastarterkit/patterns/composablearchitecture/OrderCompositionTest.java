package com.javastarterkit.patterns.composablearchitecture;

import com.javastarterkit.patterns.composablearchitecture.composition.OrderComposer;
import com.javastarterkit.patterns.composablearchitecture.core.store.Store;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.DeliveryAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.OrderAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.PizzaAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.OrderState;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaSize;
import com.javastarterkit.patterns.composablearchitecture.ui.models.Topping;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Tests the composition of independent features into a single order store:
 * routing of actions, isolation of state slices, and subscription behavior.
 */
class OrderCompositionTest {

    @Test
    @DisplayName("pizza actions are routed to the pizza slice only")
    void pizzaActions_routeToPizzaSlice() {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();

        store.dispatch(new OrderAction.Pizza(new PizzaAction.selectSize(PizzaSize.LARGE)));
        store.dispatch(new OrderAction.Pizza(new PizzaAction.toggleTopping(Topping.PEPPERONI)));

        OrderState state = store.state();
        assertThat(state.pizza().size()).isEqualTo(PizzaSize.LARGE);
        assertThat(state.pizza().toppings()).contains(Topping.PEPPERONI);
        // Delivery slice untouched.
        assertThat(state.delivery().name()).isEmpty();
    }

    @Test
    @DisplayName("delivery actions are routed to the delivery slice only")
    void deliveryActions_routeToDeliverySlice() {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();

        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setName("Bob")));

        OrderState state = store.state();
        assertThat(state.delivery().name()).isEqualTo("Bob");
        // Pizza slice untouched (default medium cheese).
        assertThat(state.pizza().size()).isEqualTo(PizzaSize.MEDIUM);
    }

    @Test
    @DisplayName("unrelated slices are fully isolated across dispatches")
    void unrelatedSlices_areIsolated() {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();

        store.dispatch(new OrderAction.Pizza(new PizzaAction.setQuantity(3)));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setCity("Austin")));
        store.dispatch(new OrderAction.Pizza(new PizzaAction.toggleTopping(Topping.OLIVES)));

        OrderState state = store.state();
        assertThat(state.pizza().quantity()).isEqualTo(3);
        assertThat(state.pizza().toppings()).contains(Topping.OLIVES);
        assertThat(state.delivery().city()).isEqualTo("Austin");
    }

    @Test
    @DisplayName("order is ready to place only when both slices are complete")
    void isReadyToPlace_requiresBothSlices() {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();

        // Default pizza is orderable (cheese), but delivery is incomplete.
        assertThat(store.state().isReadyToPlace()).isFalse();

        // Complete the delivery.
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setName("Alice")));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setAddress("123 Main St")));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setCity("Springfield")));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setPhone("555-0100")));

        assertThat(store.state().isReadyToPlace()).isTrue();
    }

    @Test
    @DisplayName("subscribers are notified on every state transition")
    void subscribers_areNotified() {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();
        AtomicInteger notifications = new AtomicInteger();

        store.subscribe(state -> notifications.incrementAndGet());

        store.dispatch(new OrderAction.Pizza(new PizzaAction.selectSize(PizzaSize.LARGE)));
        store.dispatch(new OrderAction.Delivery(new DeliveryAction.setName("Alice")));

        assertThat(notifications.get()).isEqualTo(2);
    }

    @Test
    @DisplayName("no state change means no subscriber notification")
    void noChange_meansNoNotification() {
        Store<OrderState, OrderAction> store = OrderComposer.createStore();
        java.util.concurrent.atomic.AtomicInteger notifications = new java.util.concurrent.atomic.AtomicInteger();

        store.subscribe(state -> notifications.incrementAndGet());

        // First dispatch changes the state (revision becomes 1, notification count = 1).
        store.dispatch(new OrderAction.Pizza(new PizzaAction.selectSize(PizzaSize.LARGE)));
        assertThat(notifications.get()).isEqualTo(1);

        // Second dispatch re-selects the same size; the reducer returns an
        // equal state, so the store detects no change and skips notification.
        store.dispatch(new OrderAction.Pizza(new PizzaAction.selectSize(PizzaSize.LARGE)));

        assertThat(store.revision()).isEqualTo(1);
        assertThat(notifications.get()).isEqualTo(1);
    }
}