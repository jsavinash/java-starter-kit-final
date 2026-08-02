package com.javastarterkit.patterns.hexagonalarchitecture.domain;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * Domain entity representing a bank account.
 *
 * <p>This class encapsulates the core business logic and rules for a bank account.
 * It is not thread-safe by design - each thread should work on its own instance.
 *
 * @param id the unique account identifier
 * @param owner the account owner name
 * @param initialBalance the initial balance
 */
public class Account {
    private final String id;
    private final String owner;
    private Money balance;
    private final List<Transaction> transactions;

    /**
     * Creates a new Account with the specified details.
     *
     * @param id the unique account identifier
     * @param owner the account owner name
     * @param initialBalance the initial balance
     * @throws IllegalArgumentException if any parameter is invalid
     */
    public Account(String id, String owner, Money initialBalance) {
        this.id = Objects.requireNonNull(id, "Account ID cannot be null");
        this.owner = Objects.requireNonNull(owner, "Owner cannot be null");
        this.balance = Objects.requireNonNull(initialBalance, "Initial balance cannot be null");
        this.transactions = new ArrayList<>();
    }

    /**
     * Returns the account ID.
     *
     * @return the account ID
     */
    public String id() {
        return id;
    }

    /**
     * Returns the account owner name.
     *
     * @return the owner name
     */
    public String owner() {
        return owner;
    }

    /**
     * Returns the current balance.
     *
     * @return the balance
     */
    public Money balance() {
        return balance;
    }

    /**
     * Returns an unmodifiable list of all transactions.
     *
     * @return the list of transactions
     */
    public List<Transaction> transactions() {
        return Collections.unmodifiableList(transactions);
    }

    /**
     * Deposits money into the account.
     *
     * @param amount the amount to deposit
     * @throws IllegalArgumentException if amount is null or not positive
     */
    public void deposit(Money amount) {
        Objects.requireNonNull(amount, "Deposit amount cannot be null");
        if (amount.amount().signum() <= 0) {
            throw new IllegalArgumentException("Deposit amount must be positive");
        }
        balance = balance.add(amount);
        transactions.add(new Transaction("DEPOSIT", amount, balance));
    }

    /**
     * Withdraws money from the account.
     *
     * @param amount the amount to withdraw
     * @throws IllegalArgumentException if amount is null or not positive
     * @throws IllegalStateException if insufficient funds
     */
    public void withdraw(Money amount) {
        Objects.requireNonNull(amount, "Withdrawal amount cannot be null");
        if (amount.amount().signum() <= 0) {
            throw new IllegalArgumentException("Withdrawal amount must be positive");
        }
        if (amount.isGreaterThan(balance)) {
            throw new IllegalStateException(
                "Insufficient funds: balance=" + balance + ", requested=" + amount
            );
        }
        balance = balance.subtract(amount);
        transactions.add(new Transaction("WITHDRAW", amount, balance));
    }

    @Override
    public String toString() {
        return "Account{id=" + id + ", owner=" + owner + ", balance=" + balance + "}";
    }
}