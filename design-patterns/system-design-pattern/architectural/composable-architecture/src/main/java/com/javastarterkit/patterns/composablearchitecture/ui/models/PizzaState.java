package com.javastarterkit.patterns.composablearchitecture.ui.models;

import com.javastarterkit.patterns.composablearchitecture.core.State;

import java.util.EnumSet;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;

/**
 * Immutable state for the pizza-configuration feature.
 *
 * <p>Holds the {@link PizzaSize}, an {@link EnumSet} of selected {@link Topping}s,
 * and a quantity. The state is an immutable record; every "withX" method returns
 * a new instance (copy-on-write) so it can be safely shared across threads.
 *
 * @param size     the selected pizza size (never {@code null})
 * @param toppings the set of selected toppings (never {@code null})
 * @param quantity the number of pizzas (always >= 1)
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record PizzaState(PizzaSize size, Set<Topping> toppings, int quantity) implements State {

    /** Default cheese-only medium single pizza. */
    public PizzaState() {
        this(PizzaSize.MEDIUM, EnumSet.of(Topping.CHEESE), 1);
    }

    /** Compact constructor enforces invariants. */
    public PizzaState {
        toppings = (toppings == null) ? EnumSet.noneOf(Topping.class) : EnumSet.copyOf(toppings);
        if (quantity < 1) {
            throw new IllegalArgumentException("quantity must be >= 1 but was " + quantity);
        }
    }

    /**
     * @return defensive copy of toppings to preserve immutability
     */
    @Override
    public Set<Topping> toppings() {
        return Set.copyOf(toppings);
    }

    /**
     * Returns a copy with a new size.
     *
     * @param newSize the new size
     * @return a new state
     */
    public PizzaState withSize(PizzaSize newSize) {
        return new PizzaState(newSize, toppings, quantity);
    }

    /**
     * Returns a copy that toggles a topping in/out of the selection.
     *
     * @param topping the topping to toggle
     * @return a new state
     */
    public PizzaState withToppingToggled(Topping topping) {
        EnumSet<Topping> updated = EnumSet.copyOf(toppings);
        if (updated.contains(topping)) {
            updated.remove(topping);
        } else {
            updated.add(topping);
        }
        return new PizzaState(size, updated, quantity);
    }

    /**
     * Returns a copy with a new quantity.
     *
     * @param newQuantity the new quantity (must be >= 1)
     * @return a new state
     */
    public PizzaState withQuantity(int newQuantity) {
        return new PizzaState(size, toppings, newQuantity);
    }

    /**
     * Computes the total price for this pizza configuration.
     *
     * <p>Uses {@code double} for demonstration clarity. In production, use
     * {@link java.math.BigDecimal} for exact monetary arithmetic.
     *
     * @return the total price
     */
    public double totalPrice() {
        double toppingCost = toppings.stream()
                .mapToDouble(Topping::unitPrice)
                .sum();
        return (size.basePrice() + toppingCost) * quantity;
    }

    /**
     * @return whether this configuration is orderable (size present + non-empty toppings)
     */
    public boolean isOrderable() {
        return size != null && !toppings.isEmpty();
    }

    /**
     * @return a compact human-readable summary of the price breakdown
     */
    public Map<String, Object> breakdown() {
        Map<String, Object> result = new LinkedHashMap<>();
        result.put("size", size.displayName());
        result.put("toppings", Set.copyOf(toppings));
        result.put("quantity", quantity);
        result.put("total", totalPrice());
        return result;
    }
}