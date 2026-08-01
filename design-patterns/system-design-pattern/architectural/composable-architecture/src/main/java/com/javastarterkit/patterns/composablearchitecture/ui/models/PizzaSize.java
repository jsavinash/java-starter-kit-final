package com.javastarterkit.patterns.composablearchitecture.ui.models;

import java.util.Arrays;

/**
 * Represents the available pizza sizes with their base price multipliers.
 *
 * <p>As an enum, this type is a natural fit for the composable architecture's
 * state model: it is immutable, thread-safe (enums are singletons by nature),
 * and can be stored directly in a feature state record without any defensive
 * copying.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public enum PizzaSize {

    /** Small 8" pizza. */
    SMALL("Small", 8, 8.00),
    /** Medium 12" pizza (default). */
    MEDIUM("Medium", 12, 10.50),
    /** Large 16" pizza. */
    LARGE("Large", 16, 13.00),
    /** Extra large 18" pizza. */
    EXTRA_LARGE("Extra Large", 18, 15.50);

    private final String displayName;
    private final int inches;
    private final double basePrice;

    PizzaSize(String displayName, int inches, double basePrice) {
        this.displayName = displayName;
        this.inches = inches;
        this.basePrice = basePrice;
    }

    /**
     * @return human-friendly display name
     */
    public String displayName() {
        return displayName;
    }

    /**
     * @return the diameter in inches
     */
    public int inches() {
        return inches;
    }

    /**
     * @return the base price in currency units
     */
    public double basePrice() {
        return basePrice;
    }

    /**
     * Looks up a size by its display name (case-insensitive).
     *
     * @param name the display name to match
     * @return the matching size or {@code null} if not found
     */
    public static PizzaSize fromDisplayName(String name) {
        if (name == null) {
            return null;
        }
        return Arrays.stream(values())
                .filter(size -> size.displayName.equalsIgnoreCase(name.trim()))
                .findFirst()
                .orElse(null);
    }

    @Override
    public String toString() {
        return displayName + " (" + inches + "\")";
    }
}