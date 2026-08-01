package com.javastarterkit.patterns.composablearchitecture.ui.actions;

import com.javastarterkit.patterns.composablearchitecture.core.Action;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaSize;
import com.javastarterkit.patterns.composablearchitecture.ui.models.Topping;

/**
 * Sealed hierarchy of all events that can change the pizza-configuration
 * feature state. Each variant is an immutable record carrying the data needed
 * by the reducer.
 *
 * <p>The interface is sealed so the compiler guarantees exhaustive handling in
 * switch expressions (Java 21+ pattern matching).
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public sealed interface PizzaAction extends Action {

    /**
     * Selection of a new pizza size.
     *
     * @param size the newly selected size
     */
    record selectSize(PizzaSize size) implements PizzaAction {}

    /**
     * Toggle a topping in/out of the selection.
     *
     * @param topping the topping to toggle
     */
    record toggleTopping(Topping topping) implements PizzaAction {}

    /**
     * Set the pizza quantity.
     *
     * @param quantity the new quantity (must be >= 1)
     */
    record setQuantity(int quantity) implements PizzaAction {}
}