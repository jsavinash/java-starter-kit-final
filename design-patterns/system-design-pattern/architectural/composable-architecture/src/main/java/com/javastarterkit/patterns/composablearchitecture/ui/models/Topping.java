package com.javastarterkit.patterns.composablearchitecture.ui.models;

import java.util.Arrays;

/**
 * Represents a pizza topping and its unit price.
 *
 * <p>Enums are inherently immutable and thread-safe, making them ideal value
 * types for the composable architecture state model.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public enum Topping {

    /** Cheese (default, zero-cost). */
    CHEESE("Cheese", 0.00),
    /** Pepperoni. */
    PEPPERONI("Pepperoni", 1.50),
    /** Mushrooms. */
    MUSHROOMS("Mushrooms", 1.00),
    /** Onions. */
    ONIONS("Onions", 0.75),
    /** Olives. */
    OLIVES("Olives", 0.90),
    /** Bell peppers. */
    BELL_PEPPERS("Bell Peppers", 0.80),
    /** Extra cheese. */
    EXTRA_CHEESE("Extra Cheese", 1.25),
    /** Pineapple. */
    PINEAPPLE("Pineapple", 1.10);

    private final String displayName;
    private final double unitPrice;

    Topping(String displayName, double unitPrice) {
        this.displayName = displayName;
        this.unitPrice = unitPrice;
    }

    /**
     * @return human-friendly display name
     */
    public String displayName() {
        return displayName;
    }

    /**
     * @return unit price in currency units
     */
    public double unitPrice() {
        return unitPrice;
    }

    /**
     * Looks up a topping by its display name (case-insensitive).
     *
     * @param name the display name to match
     * @return the matching topping or {@code null} if not found
     */
    public static Topping fromDisplayName(String name) {
        if (name == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(topping -> topping.displayName.equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }
}