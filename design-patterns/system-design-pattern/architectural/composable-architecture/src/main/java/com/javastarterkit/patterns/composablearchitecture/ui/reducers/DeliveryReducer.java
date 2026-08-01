package com.javastarterkit.patterns.composablearchitecture.ui.reducers;

import com.javastarterkit.patterns.composablearchitecture.core.Reducer;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.DeliveryAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.DeliveryState;

/**
 * Pure reducer for the delivery-details feature.
 *
 * <p>Implemented as a singleton {@link Enum#INSTANCE} — an idiomatic,
 * thread-safe singleton pattern in Java. Because it holds no mutable state,
 * this reducer is safe to invoke concurrently from multiple threads and can be
 * shared across stores.
 *
 * <p>All transitions are pure reductions: given the current immutable
 * {@link DeliveryState} and an action, it returns a new immutable state.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public enum DeliveryReducer implements Reducer<DeliveryState, DeliveryAction> {

    /** The singleton instance of the delivery reducer. */
    INSTANCE;

    @Override
    public DeliveryState reduce(DeliveryState state, DeliveryAction action) {
        return switch (action) {
            case DeliveryAction.setName setName -> state.withName(setName.name());
            case DeliveryAction.setAddress setAddress -> state.withAddress(setAddress.address());
            case DeliveryAction.setCity setCity -> state.withCity(setCity.city());
            case DeliveryAction.setPhone setPhone -> state.withPhone(setPhone.phone());
        };
    }
}