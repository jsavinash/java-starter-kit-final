package com.javastarterkit.patterns.eventsourcing.application.service;

import com.javastarterkit.patterns.eventsourcing.domain.event.DomainEvent;
import com.javastarterkit.patterns.eventsourcing.domain.model.AccountAggregate;
import com.javastarterkit.patterns.eventsourcing.exception.AggregateNotFoundException;
import com.javastarterkit.patterns.eventsourcing.infrastructure.EventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.PerAggregateLock;
import com.javastarterkit.patterns.eventsourcing.infrastructure.Snapshot;
import com.javastarterkit.patterns.eventsourcing.infrastructure.SnapshotStore;

import java.util.List;
import java.util.concurrent.locks.Lock;

/**
 * Application service (command handler) for the Event Sourcing system.
 *
 * <p>Responsible for coordinating the read-mutate-write cycle for an account
 * aggregate:
 * <ol>
 *   <li>Acquire a per-aggregate {@link Lock} (pessimistic concurrency control)</li>
 *   <li>Load the aggregate by replaying events (or from a snapshot + tail events)</li>
 *   <li>Execute the command, which emits new domain events</li>
 *   <li>Persist the new events to the {@link EventStore} with the expected version</li>
 *   <li>Release the lock</li>
 * </ol>
 *
 * <h3>Thread-Safety</h3>
 * <p>All command methods acquire a dedicated {@link ReentrantLock} for the
 * target aggregate. This ensures that the entire load-mutate-save cycle is
 * serialized per aggregate, while different aggregates proceed completely in
 * parallel. Reads ({@link #load(String)}) also acquire the lock briefly to get
 * a consistent snapshot.
 */
public final class AccountService {

    private final EventStore eventStore;
    private final SnapshotStore snapshotStore;
    private final PerAggregateLock perAggregateLock;

    /**
     * Creates an AccountService.
     *
     * @param eventStore        the event store for persistence
     * @param snapshotStore     the snapshot store for optimization
     * @param perAggregateLock  the per-aggregate lock registry
     */
    public AccountService(EventStore eventStore,
                          SnapshotStore snapshotStore,
                          PerAggregateLock perAggregateLock) {
        this.eventStore = eventStore;
        this.snapshotStore = snapshotStore;
        this.perAggregateLock = perAggregateLock;
    }

    /**
     * Opens a new bank account.
     *
     * @param id             the unique account ID
     * @param owner          the account owner
     * @param initialBalance the starting balance (must be non-negative)
     */
    public void openAccount(String id, String owner, int initialBalance) {
        Lock lock = perAggregateLock.lockFor(id);
        lock.lock();
        try {
            AccountAggregate account = AccountAggregate.open(id, owner, initialBalance);
            save(account);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Deposits money into an account.
     *
     * @param aggregateId the account ID
     * @param amount      the positive amount to deposit
     */
    public void deposit(String aggregateId, int amount) {
        performCommand(aggregateId, account -> account.deposit(amount));
    }

    /**
     * Withdraws money from an account.
     *
     * @param aggregateId the account ID
     * @param amount      the positive amount to withdraw (must be ≤ balance)
     */
    public void withdraw(String aggregateId, int amount) {
        performCommand(aggregateId, account -> account.withdraw(amount));
    }

    /**
     * Closes an account. No further deposits or withdrawals are permitted.
     *
     * @param aggregateId the account ID
     */
    public void closeAccount(String aggregateId) {
        performCommand(aggregateId, AccountAggregate::close);
    }

    /**
     * Loads the current state of an aggregate by replaying its event stream.
     * Uses a snapshot if available to reduce replay time.
     *
     * @param aggregateId the account ID
     * @return the fully rehydrated aggregate
     * @throws AggregateNotFoundException if no stream exists for the ID
     */
    public AccountAggregate load(String aggregateId) {
        Lock lock = perAggregateLock.lockFor(aggregateId);
        lock.lock();
        try {
            return loadWithLock(aggregateId, lock);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Takes a snapshot of the current aggregate state for faster future loads.
     *
     * @param aggregateId the account ID
     */
    public void takeSnapshot(String aggregateId) {
        Lock lock = perAggregateLock.lockFor(aggregateId);
        lock.lock();
        try {
            AccountAggregate account = loadWithLock(aggregateId, lock);
            snapshotStore.save(aggregateId, Snapshot.take(account));
        } finally {
            lock.unlock();
        }
    }

    // ── Private helpers ─────────────────────────────────────────────────────

    /**
     * Executes a command on the aggregate under a per-aggregate lock.
     *
     * @param aggregateId the account ID
     * @param command     the command to execute
     */
    private void performCommand(String aggregateId, java.util.function.Consumer<AccountAggregate> command) {
        Lock lock = perAggregateLock.lockFor(aggregateId);
        lock.lock();
        try {
            AccountAggregate account = loadWithLock(aggregateId, lock);
            command.accept(account);
            save(account);
        } finally {
            lock.unlock();
        }
    }

    /**
     * Loads an aggregate from the event store, using a snapshot when available.
     * The caller must already hold the lock.
     *
     * @param aggregateId the account ID
     * @param lock        the lock already held by the caller
     * @return the rehydrated aggregate
     * @throws AggregateNotFoundException if no stream exists
     */
    private AccountAggregate loadWithLock(String aggregateId, Lock lock) {
        // Try snapshot first for optimization
        var snapshotOpt = snapshotStore.load(aggregateId);
        if (snapshotOpt.isPresent()) {
            var snapshot = snapshotOpt.get();
            var remaining = eventStore.loadFromVersion(aggregateId, snapshot.version());
            return AccountAggregate.fromSnapshot(snapshot, remaining);
        }

        // Full replay from the event stream
        List<DomainEvent> events = eventStore.load(aggregateId);
        if (events.isEmpty()) {
            throw new AggregateNotFoundException(
                    "No event stream found for aggregate: " + aggregateId);
        }

        var account = new AccountAggregate(aggregateId);
        events.forEach(account::replay);
        return account;
    }

    /**
     * Persists all uncommitted events from an aggregate to the event store
     * atomically using optimistic concurrency control.
     *
     * @param account the aggregate with uncommitted events
     */
    private void save(AccountAggregate account) {
        if (!account.hasUncommittedEvents()) {
            return;
        }
        List<DomainEvent> uncommitted = account.pullUncommittedEvents();
        long expectedVersion = eventStore.latestVersion(account.id());
        eventStore.append(account.id(), uncommitted, expectedVersion);
    }
}