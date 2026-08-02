package com.javastarterkit.patterns.hexagonalarchitecture.domain;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Immutable value object representing a non-negative amount of money.
 *
 * <p>This record is immutable by design, making it inherently thread-safe.
 *
 * @param amount the monetary amount (must be non-negative)
 */
public record Money(BigDecimal amount) {

    /**
     * Creates a new Money instance from a string value.
     *
     * @param value the string representation of the amount
     * @return a new Money instance
     * @throws IllegalArgumentException if value is null, empty, or negative
     */
    public static Money of(String value) {
        Objects.requireNonNull(value, "Value cannot be null");
        BigDecimal decimal = new BigDecimal(value);
        if (decimal.signum() < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
        return new Money(decimal);
    }

    /**
     * Adds another Money value to this one.
     *
     * @param other the other Money value
     * @return a new Money instance with the sum
     * @throws IllegalArgumentException if other is null
     */
    public Money add(Money other) {
        Objects.requireNonNull(other, "Money value cannot be null");
        return new Money(amount.add(other.amount));
    }

    /**
     * Subtracts another Money value from this one.
     *
     * @param other the other Money value
     * @return a new Money instance with the difference
     * @throws IllegalArgumentException if other is null
     */
    public Money subtract(Money other) {
        Objects.requireNonNull(other, "Money value cannot be null");
        return new Money(amount.subtract(other.amount));
    }

    /**
     * Checks if this amount is greater than another amount.
     *
     * @param other the other Money value
     * @return true if this amount is greater
     * @throws IllegalArgumentException if other is null
     */
    public boolean isGreaterThan(Money other) {
        Objects.requireNonNull(other, "Money value cannot be null");
        return amount.compareTo(other.amount) > 0;
    }

    @Override
    public String toString() {
        return amount.toPlainString();
    }
}