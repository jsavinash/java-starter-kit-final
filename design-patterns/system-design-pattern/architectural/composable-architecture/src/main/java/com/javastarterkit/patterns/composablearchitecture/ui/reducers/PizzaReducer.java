package com.javastarterkit.patterns.composablearchitecture.ui.reducers;

import com.javastarterkit.patterns.composablearchitecture.core.Reducer;
import com.javastarterkit.patterns.composablearchitecture.exception.InvalidPizzaException;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.PizzaAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaState;

/**
 * Pure reducer for the pizza-configuration feature.
 *
 * <p>Implemented as a singleton {@link Enum#INSTANCE} — an idiomatic,
 * thread-safe singleton pattern in Java. Because it holds no mutable state,
 * this reducer is safe to invoke concurrently from multiple threads and can be
 * shared across stores.
 *
 * <p>All transitions are pure reductions: given the current immutable
 * {@link PizzaState} and an action, it returns a new immutable state.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public enum PizzaReducer implements Reducer<PizzaState, PizzaAction> {

    /** The singleton instance of the pizza reducer. */
    INSTANCE;

    @Override
    public PizzaState reduce(PizzaState state, PizzaAction action) {
        return switch (action) {
            case PizzaAction.selectSize selectSize ->
                    state.withSize(selectSize.size());
            case PizzaAction.toggleTopping toggleTopping ->
                    state.withToppingToggled(toggleTopping.topping());
            case PizzaAction.setQuantity setQuantity -> {
                if (setQuantity.quantity() < 1) {
                    throw new InvalidPizzaException(
                            "Quantity must be >= 1 but was " + setQuantity.quantity());
                }
                yield state.withQuantity(setQuantity.quantity());
            }
        };
    }
}