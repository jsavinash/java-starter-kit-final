package com.javastarterkit.patterns.composablearchitecture.ui.actions;

import com.javastarterkit.patterns.composablearchitecture.core.Action;

/**
 * Parent action hierarchy for the composed order feature.
 *
 * <p>Each variant wraps a child feature action ({@link PizzaAction} or
 * {@link DeliveryAction}) so the parent reducer can route it to the correct
 * child reducer via
 * {@link com.javastarterkit.patterns.composablearchitecture.core.Reducer#pullback}.
 *
 * <p>The interface is sealed so the compiler guarantees exhaustive handling in
 * switch expressions.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public sealed interface OrderAction extends Action {

    /**
     * Wraps an action targeting the pizza-configuration feature.
     *
     * @param action the child pizza action
     */
    record Pizza(PizzaAction action) implements OrderAction {}

    /**
     * Wraps an action targeting the delivery-details feature.
     *
     * @param action the child delivery action
     */
    record Delivery(DeliveryAction action) implements OrderAction {}
}