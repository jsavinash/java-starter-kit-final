package com.javastarterkit.patterns.hexagonalarchitecture.domain;

import java.math.BigDecimal;

/**
 * Immutable value object representing a single transaction.
 *
 * <p>This record is immutable by design, making it inherently thread-safe.
 *
 * @param type the transaction type (e.g., "DEPOSIT", "WITHDRAW")
 * @param amount the transaction amount
 * @param balanceAfter the account balance after this transaction
 */
public record Transaction(String type, Money amount, Money balanceAfter) {

    /**
     * Returns the transaction type.
     *
     * @return the type (e.g., "DEPOSIT", "WITHDRAW")
     */
    public String type() {
        return type;
    }

    /**
     * Returns the transaction amount.
     *
     * @return the amount
     */
    public Money amount() {
        return amount;
    }

    /**
     * Returns the account balance after this transaction.
     *
     * @return the balance after transaction
     */
    public Money balanceAfter() {
        return balanceAfter;
    }

    @Override
    public String toString() {
        return "Transaction{" +
            "type='" + type + '\'' +
            ", amount=" + amount +
            ", balanceAfter=" + balanceAfter +
            '}';
    }
}