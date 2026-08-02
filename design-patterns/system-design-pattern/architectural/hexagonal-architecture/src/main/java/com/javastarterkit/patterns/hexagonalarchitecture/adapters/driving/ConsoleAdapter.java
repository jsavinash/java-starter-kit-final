package com.javastarterkit.patterns.hexagonalarchitecture.adapters.driving;

import com.javastarterkit.patterns.hexagonalarchitecture.application.AccountService;
import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;

/**
 * Driving adapter: a simple command-line interface.
 *
 * <p>This adapter translates user input into calls on the AccountService.
 * It demonstrates how the application can be driven from the console.
 */
public class ConsoleAdapter {
    private final AccountService service;

    /**
     * Creates a new ConsoleAdapter with the given service.
     *
     * @param service the account service
     */
    public ConsoleAdapter(AccountService service) {
        this.service = service;
    }

    /**
     * Opens a new account from the console.
     *
     * @param owner the account owner name
     * @param initialBalance the initial balance
     * @return the account ID
     */
    public String openAccount(String owner, String initialBalance) {
        Account account = service.openAccount(owner, initialBalance);
        System.out.println("  [CLI] Opened account " + account.id() + " for " + owner
            + " with balance " + account.balance());
        return account.id();
    }

    /**
     * Deposits money from the console.
     *
     * @param accountId the account ID
     * @param amount the amount to deposit
     */
    public void deposit(String accountId, String amount) {
        Account account = service.deposit(accountId, amount);
        System.out.println("  [CLI] Deposited " + amount + " -> " + account);
    }

    /**
     * Withdraws money from the console.
     *
     * @param accountId the account ID
     * @param amount the amount to withdraw
     */
    public void withdraw(String accountId, String amount) {
        Account account = service.withdraw(accountId, amount);
        System.out.println("  [CLI] Withdrew " + amount + " -> " + account);
    }

    /**
     * Prints the account balance from the console.
     *
     * @param accountId the account ID
     */
    public void printBalance(String accountId) {
        Account account = service.getAccount(accountId);
        System.out.println("  [CLI] Balance for " + account.owner() + ": " + account.balance());
    }
}