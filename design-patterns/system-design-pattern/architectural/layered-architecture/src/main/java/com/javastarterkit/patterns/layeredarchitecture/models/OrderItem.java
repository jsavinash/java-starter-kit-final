package com.javastarterkit.patterns.layeredarchitecture.models;

import java.util.Objects;

/**
 * Immutable value object representing a single line item in an order.
 *
 * <p>A line item combines a product name, its unit price, and the ordered
 * quantity. The total cost is computed as {@code price × quantity}.
 */
public record OrderItem(String product, Money price, int quantity) {

    public OrderItem {
        Objects.requireNonNull(product, "Product must not be null");
        Objects.requireNonNull(price, "Price must not be null");
        if (quantity <= 0) {
            throw new IllegalArgumentException("Quantity must be positive");
        }
    }

    /** Computes the total cost of this line item: {@code price × quantity}. */
    public Money total() {
        return price.multiply(quantity);
    }

    @Override
    public String toString() {
        return product + " x" + quantity + " @ " + price + " = " + total();
    }
}
