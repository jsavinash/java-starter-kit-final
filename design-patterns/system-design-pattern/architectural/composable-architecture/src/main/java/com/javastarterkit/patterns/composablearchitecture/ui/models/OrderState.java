package com.javastarterkit.patterns.composablearchitecture.ui.models;

import com.javastarterkit.patterns.composablearchitecture.core.State;

/**
 * Root/parent state that composes the independent feature states
 * ({@link PizzaState} for pizza configuration and {@link DeliveryState} for
 * delivery details) into a single order-level state.
 *
 * <p>This is the top of the composition hierarchy: the parent store holds an
 * {@code OrderState} and routes child actions to child reducers via
 * {@link com.javastarterkit.patterns.composablearchitecture.core.Reducer#pullback}.
 *
 * @param pizza    the pizza configuration sub-state
 * @param delivery the delivery details sub-state
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record OrderState(PizzaState pizza, DeliveryState delivery) implements State {

    /** Default order state with fresh feature states. */
    public OrderState() {
        this(new PizzaState(), new DeliveryState());
    }

    /**
     * Returns a copy with a new pizza sub-state.
     *
     * @param newPizza the new pizza sub-state
     * @return a new order state
     */
    public OrderState withPizza(PizzaState newPizza) {
        return new OrderState(newPizza, delivery);
    }

    /**
     * Returns a copy with a new delivery sub-state.
     *
     * @param newDelivery the new delivery sub-state
     * @return a new order state
     */
    public OrderState withDelivery(DeliveryState newDelivery) {
        return new OrderState(pizza, newDelivery);
    }

    /**
     * @return whether the complete order is ready to be placed
     */
    public boolean isReadyToPlace() {
        return pizza.isOrderable() && delivery.isComplete();
    }
}