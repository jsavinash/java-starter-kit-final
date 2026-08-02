package com.javastarterkit.patterns.layeredarchitecture.models;

import java.math.BigDecimal;
import java.util.Objects;

/**
 * Immutable value object representing a non-negative monetary amount.
 *
 * <p>This record is inherently thread-safe because it is immutable.
 * All arithmetic operations return new {@code Money} instances.
 */
public record Money(BigDecimal amount) {

    /**
     * Creates a {@code Money} from a string representation.
     *
     * @param value the decimal string (e.g., "99.99")
     * @return a new {@code Money} instance
     * @throws NumberFormatException if the string is not a valid number
     */
    public static Money of(String value) {
        return new Money(new BigDecimal(value));
    }

    public Money {
        Objects.requireNonNull(amount, "Amount must not be null");
        if (amount.signum() < 0) {
            throw new IllegalArgumentException("Amount must be non-negative");
        }
    }

    /** Returns a new {@code Money} that is the sum of this and {@code other}. */
    public Money add(Money other) {
        Objects.requireNonNull(other, "Other amount must not be null");
        return new Money(amount.add(other.amount));
    }

    /** Returns a new {@code Money} equal to this amount multiplied by {@code quantity}. */
    public Money multiply(int quantity) {
        return new Money(amount.multiply(BigDecimal.valueOf(quantity)));
    }

    /** Returns {@code true} if this amount is strictly greater than {@code other}. */
    public boolean isGreaterThan(Money other) {
        Objects.requireNonNull(other, "Other amount must not be null");
        return amount.compareTo(other.amount) > 0;
    }

    @Override
    public String toString() {
        return amount.toPlainString();
    }
}
