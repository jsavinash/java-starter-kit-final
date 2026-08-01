package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.model;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.AccountClosed;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.AccountOpened;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.MoneyDeposited;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.MoneyWithdrawn;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.DomainException;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;

/**
 * The write-side aggregate root for a bank account.
 *
 * <p>Implements <b>event sourcing</b>: state changes are never applied directly—
 * instead, domain events are emitted and then applied to mutate state.  The
 * aggregate can be fully reconstructed by replaying its event stream from the
 * {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.EventStore
 * EventStore}.
 *
 * <h3>Enforced Business Invariants</h3>
 * <ul>
 *   <li>Initial balance cannot be negative</li>
 *   <li>Deposit amounts must be strictly positive</li>
 *   <li>Withdrawal amounts must be strictly positive and must not exceed the current balance</li>
 *   <li>No operations are permitted on a closed account (except close, which is idempotent-safe)</li>
 * </ul>
 *
 * <h3>Thread-Safety</h3>
 * <p>An aggregate instance is <b>not</b> inherently thread-safe.  Concurrent command
 * handlers operating on the same aggregate must serialize access through
 * {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.PerAggregateLock}.
 * Each aggregate instance is confined to a single thread for the duration of its
 * read-modify-write lifecycle.
 */
public class AccountAggregate {

    /** Unique identifier for this aggregate. */
    private final String id;

    /** Name of the account owner. Set when the account is opened. */
    private String owner;

    /** Current balance in the smallest currency unit (e.g. cents). */
    private int balance;

    /** Whether the account has been closed. */
    private boolean closed;

    /**
     * Events raised during the current session that have not yet been persisted.
     * A defensive copy is taken when pulling to prevent external mutation.
     */
    private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

    /**
     * Creates a new aggregate shell with the given ID.  Use {@link #open(String, String, int)}
     * to properly initialise an account.
     *
     * @param id the unique account identifier
     */
    public AccountAggregate(String id) {
        this.id = Objects.requireNonNull(id, "Account id cannot be null");
    }

    // ── Accessors ───────────────────────────────────────────────────────────

    /** @return the aggregate ID */
    public String id() {
        return id;
    }

    /** @return the account owner's name */
    public String owner() {
        return owner;
    }

    /** @return the current balance */
    public int balance() {
        return balance;
    }

    /** @return {@code true} if the account is closed */
    public boolean isClosed() {
        return closed;
    }

    /** @return {@code true} if there are uncommitted events pending persistence */
    public boolean hasUncommittedEvents() {
        return !uncommittedEvents.isEmpty();
    }

    // ── Factory method ──────────────────────────────────────────────────────

    /**
     * Factory method that opens a new account.
     *
     * @param id             the unique account identifier
     * @param owner          the account owner's name
     * @param initialBalance the starting balance (must be non-negative)
     * @return a fully initialised aggregate with an uncommitted {@link AccountOpened} event
     * @throws DomainException if {@code initialBalance} is negative or {@code owner} is null
     */
    public static AccountAggregate open(String id, String owner, int initialBalance) {
        if (initialBalance < 0) {
            throw new DomainException("Initial balance cannot be negative, got: " + initialBalance);
        }
        Objects.requireNonNull(owner, "Owner cannot be null");
        var account = new AccountAggregate(id);
        account.apply(new AccountOpened(id, owner, initialBalance));
        return account;
    }

    // ── Command methods ────────────────────────────────────────────────────

    /**
     * Deposits a positive amount into the account.
     *
     * @param amount the amount to deposit (must be positive)
     * @throws DomainException if the account is closed or the amount is not positive
     */
    public void deposit(int amount) {
        if (closed) {
            throw new DomainException("Cannot deposit into a closed account: " + id);
        }
        if (amount <= 0) {
            throw new DomainException("Deposit amount must be positive, got: " + amount);
        }
        apply(new MoneyDeposited(id, amount));
    }

    /**
     * Withdraws a positive amount from the account if sufficient funds exist.
     *
     * @param amount the amount to withdraw (must be positive and ≤ balance)
     * @throws DomainException if the account is closed, the amount is not positive,
     *                         or the balance is insufficient
     */
    public void withdraw(int amount) {
        if (closed) {
            throw new DomainException("Cannot withdraw from a closed account: " + id);
        }
        if (amount <= 0) {
            throw new DomainException("Withdrawal amount must be positive, got: " + amount);
        }
        if (balance < amount) {
            throw new DomainException(
                    "Insufficient funds: balance=" + balance + ", requested=" + amount);
        }
        apply(new MoneyWithdrawn(id, amount));
    }

    /**
     * Closes the account.  After closing, no further deposits or withdrawals are allowed.
     *
     * @throws DomainException if the account is already closed
     */
    public void close() {
        if (closed) {
            throw new DomainException("Account is already closed: " + id);
        }
        apply(new AccountClosed(id));
    }

    // ── Event application ──────────────────────────────────────────────────

    /**
     * Applies an event: records it as uncommitted and mutates internal state.
     * This is the write path used during command handling.
     */
    private void apply(DomainEvent event) {
        uncommittedEvents.add(event);
        handle(event);
    }

    /**
     * Replays a historical event: mutates internal state without recording it
     * as uncommitted.  This is the read path used when reconstructing an
     * aggregate from its event stream.
     */
    public void replay(DomainEvent event) {
        handle(event);
    }

    /**
     * Dispatches an event to the appropriate state mutation method.
     *
     * <p>Relies on Java's sealed-type pattern matching for exhaustiveness.
     * The {@code default} branch handles the theoretical case of a new
     * permitted subtype and is unreachable as long as the sealed hierarchy
     * is closed.
     */
    private void handle(DomainEvent event) {
        switch (event) {
            case AccountOpened e -> {
                this.owner = e.owner();
                this.balance = e.initialBalance();
            }
            case MoneyDeposited e -> this.balance += e.amount();
            case MoneyWithdrawn e -> this.balance -= e.amount();
            case AccountClosed e -> this.closed = true;
            default -> {
                // Unreachable for a closed sealed hierarchy
            }
        }
    }

    /**
     * Returns an immutable snapshot of all uncommitted events and clears the internal buffer.
     * The repository uses this to persist the event stream and publish events.
     *
     * @return an unmodifiable list of events raised since the last persistence
     */
    public List<DomainEvent> pullUncommittedEvents() {
        var copy = new ArrayList<>(uncommittedEvents);
        uncommittedEvents.clear();
        return Collections.unmodifiableList(copy);
    }
}
