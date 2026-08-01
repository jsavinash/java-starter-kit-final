package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Thread-safe, in-memory read model for the account query side.
 *
 * <h3>Design</h3>
 * <p>The read model stores a denormalized, flat representation of each account
 * — optimized for O(1) lookups by account ID, full listing, and counting.
 * It is updated exclusively by {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection.AccountProjection
 * AccountProjection} in response to domain events, and read exclusively by
 * query handlers.</p>
 *
 * <h3>Concurrency Strategy</h3>
 * <ul>
 *   <li>All mutations go through {@link ConcurrentMap#computeIfPresent} or
 *       {@link ConcurrentMap#put}, both of which are atomic per key.</li>
 *   <li>Reads ({@link #findById}, {@link #findAll}) use defensive copies of
 *       the internal data, so callers never observe a partially-updated state.</li>
 *   <li>No external locking is required — the {@code ConcurrentHashMap}
 *       provides thread-safety for both concurrent reads and writes.</li>
 * </ul>
 */
public final class AccountReadModel {

    /**
     * Internal mutable entry used as the map value. Each update creates a
     * new entry instance (immutable), so the map value is effectively
     * replaced atomically via {@code computeIfPresent}.
     */
    private record Entry(String accountId, String owner, int balance, boolean closed) {
    }

    /** Maps account IDs to their current read-model state. */
    private final ConcurrentMap<String, Entry> accounts = new ConcurrentHashMap<>();

    /**
     * Creates a new account entry in the read model.
     * Called when an {@code AccountOpened} event is projected.
     *
     * @param accountId the account ID
     * @param owner     the account owner
     * @param balance   the initial balance
     */
    public void addAccount(String accountId, String owner, int balance) {
        accounts.put(accountId, new Entry(accountId, owner, balance, false));
    }

    /**
     * Updates the balance of an existing account by depositing or withdrawing.
     * Called when a {@code MoneyDeposited} or {@code MoneyWithdrawn} event is
     * projected.
     *
     * @param accountId the account ID
     * @param amount    the amount to adjust (always positive)
     * @param deposit   {@code true} to add, {@code false} to subtract
     */
    public void updateBalance(String accountId, int amount, boolean deposit) {
        accounts.computeIfPresent(accountId, (id, entry) -> {
            int newBalance = deposit ? entry.balance() + amount : entry.balance() - amount;
            return new Entry(entry.accountId(), entry.owner, newBalance, entry.closed());
        });
    }

    /**
     * Marks an account as closed in the read model.
     * Called when an {@code AccountClosed} event is projected.
     *
     * @param accountId the account ID
     */
    public void closeAccount(String accountId) {
        accounts.computeIfPresent(accountId, (id, entry) ->
                new Entry(entry.accountId(), entry.owner(), entry.balance(), true));
    }

    /**
     * Finds an account by ID, returning an {@link Optional}.
     *
     * @param accountId the account ID
     * @return an {@code Optional} containing the {@link AccountView} if found,
     *         or an empty {@code Optional} if the account does not exist
     */
    public Optional<AccountView> findById(String accountId) {
        var entry = accounts.get(accountId);
        return entry != null
                ? Optional.of(toView(entry))
                : Optional.empty();
    }

    /**
     * Returns a snapshot list of all accounts.
     *
     * @return an unmodifiable list of {@link AccountView} instances
     */
    public List<AccountView> findAll() {
        return accounts.values().stream()
                .map(this::toView)
                .toList();
    }

    /**
     * Returns the total number of accounts.
     *
     * @return the account count
     */
    public int count() {
        return accounts.size();
    }

    /** Converts an internal {@link Entry} to an immutable {@link AccountView}. */
    private AccountView toView(Entry entry) {
        return new AccountView(
                entry.accountId(), entry.owner(), entry.balance(), entry.closed());
    }
}
