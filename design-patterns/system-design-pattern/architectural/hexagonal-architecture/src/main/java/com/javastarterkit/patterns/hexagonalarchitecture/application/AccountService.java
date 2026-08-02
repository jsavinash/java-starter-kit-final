package com.javastarterkit.patterns.hexagonalarchitecture.application;

import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;
import com.javastarterkit.patterns.hexagonalarchitecture.domain.Money;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.AccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.NotificationPort;

import java.util.Objects;
import java.util.UUID;

/**
 * Application service orchestrating account use cases.
 *
 * <p>This service implements the application business logic by coordinating
 * the domain layer and ports. It knows nothing about HTTP, databases, or
 * the console - it only knows about the domain and the port interfaces.
 *
 * <p>This service is stateless and thread-safe.
 */
public class AccountService {

    private final AccountRepository repository;
    private final NotificationPort notifier;

    /**
     * Creates a new AccountService with the given dependencies.
     *
     * @param repository the account repository port
     * @param notifier the notification port
     * @throws IllegalArgumentException if any dependency is null
     */
    public AccountService(AccountRepository repository, NotificationPort notifier) {
        this.repository = Objects.requireNonNull(repository, "AccountRepository cannot be null");
        this.notifier = Objects.requireNonNull(notifier, "NotificationPort cannot be null");
    }

    /**
     * Use case: open a new account with an initial balance.
     *
     * @param owner the account owner name
     * @param initialBalance the initial balance as a string
     * @return the created account
     * @throws IllegalArgumentException if parameters are invalid
     */
    public Account openAccount(String owner, String initialBalance) {
        Objects.requireNonNull(owner, "Owner cannot be null");
        Objects.requireNonNull(initialBalance, "Initial balance cannot be null");

        Money balance = Money.of(initialBalance);
        Account account = new Account(UUID.randomUUID().toString(), owner, balance);
        repository.save(account);
        notifier.notify(owner, "Welcome! Account " + account.id() + " opened with " + account.balance());
        return account;
    }

    /**
     * Use case: deposit money into an account.
     *
     * @param accountId the account ID
     * @param amount the amount to deposit as a string
     * @return the updated account
     * @throws IllegalArgumentException if account not found or amount invalid
     */
    public Account deposit(String accountId, String amount) {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        Account account = getAccount(accountId);
        Money depositAmount = Money.of(amount);
        account.deposit(depositAmount);
        repository.save(account);
        notifier.notify(account.owner(), "Deposited " + amount + ". New balance: " + account.balance());
        return account;
    }

    /**
     * Use case: withdraw money from an account.
     *
     * @param accountId the account ID
     * @param amount the amount to withdraw as a string
     * @return the updated account
     * @throws IllegalArgumentException if account not found or amount invalid
     * @throws IllegalStateException if insufficient funds
     */
    public Account withdraw(String accountId, String amount) {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        Objects.requireNonNull(amount, "Amount cannot be null");

        Account account = getAccount(accountId);
        Money withdrawAmount = Money.of(amount);
        account.withdraw(withdrawAmount);
        repository.save(account);
        notifier.notify(account.owner(), "Withdrew " + amount + ". New balance: " + account.balance());
        return account;
    }

    /**
     * Use case: query account details.
     *
     * @param accountId the account ID
     * @return the account
     * @throws IllegalArgumentException if account not found
     */
    public Account getAccount(String accountId) {
        Objects.requireNonNull(accountId, "Account ID cannot be null");
        return repository.findById(accountId)
            .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
    }
}