package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.model.AccountAggregate;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.AggregateNotFoundException;

import java.util.List;
import java.util.Objects;
import java.util.concurrent.locks.Lock;
import java.util.function.Function;

/**
 * Repository for the {@link AccountAggregate} write model.
 *
 * <h3>Design</h3>
 * <p>This repository bridges the domain layer (aggregates) with the
 * infrastructure layer (event store, event bus). It is the sole entry point
 * for loading and persisting aggregates, enforcing two critical invariants:</p>
 *
 * <ol>
 *   <li><b>Event Sourcing</b> — aggregates are reconstructed by replaying
 *       their event stream from the {@link EventStore}. State is never
 *       persisted directly.</li>
 *   <li><b>Pessimistic Locking</b> — every read-mutate-write cycle for a
 *       single aggregate is serialized via {@link PerAggregateLock},
 *       preventing lost updates and invariant violations under concurrent
 *       command processing.</li>
 * </ol>
 *
 * <h3>Concurrency</h3>
 * <p>The {@link #executeAtomically(String, Function)} method acquires a
 * per-aggregate lock, loads the aggregate, applies the mutation, and
 * persists the events — all under the lock. The lock is always released
 * in the {@code finally} block, even if an exception is thrown mid-cycle.
 */
public final class AccountRepository {

    private final EventStore eventStore;
    private final EventBus eventBus;
    private final PerAggregateLock lock;

    /**
     * @param eventStore the append-only event store for aggregate event streams
     * @param eventBus   the in-process event bus for publishing domain events
     * @param lock       the per-aggregate lock registry for concurrency control
     */
    public AccountRepository(EventStore eventStore, EventBus eventBus, PerAggregateLock lock) {
        this.eventStore = Objects.requireNonNull(eventStore, "EventStore cannot be null");
        this.eventBus = Objects.requireNonNull(eventBus, "EventBus cannot be null");
        this.lock = Objects.requireNonNull(lock, "PerAggregateLock cannot be null");
    }

    /**
     * Loads and reconstructs an aggregate by replaying its complete event
     * stream from the {@link EventStore}.
     *
     * @param aggregateId the aggregate identifier
     * @return the fully reconstructed aggregate
     * @throws AggregateNotFoundException if no events exist for the given ID
     */
    public AccountAggregate load(String aggregateId) {
        Objects.requireNonNull(aggregateId, "aggregateId cannot be null");
        var events = eventStore.load(aggregateId);
        if (events.isEmpty()) {
            throw new AggregateNotFoundException(
                    "No account found with id: " + aggregateId);
        }
        var aggregate = new AccountAggregate(aggregateId);
        events.forEach(aggregate::replay);
        return aggregate;
    }

    /**
     * Persists an aggregate's uncommitted events and publishes them to the
     * {@link EventBus}.
     *
     * <p>The flow is:
     * 1. Pull uncommitted events from the aggregate (clearing its buffer).
     * 2. Append them atomically to the {@link EventStore}.
     * 3. Publish each event to the {@link EventBus} for projection.
     *
     * <p>Events are appended to the store <i>before</i> being published,
     * ensuring durability — if a projection fails, the events can still be
     * replayed from the store.
     *
     * @param aggregate the aggregate whose events should be persisted
     */
    public void save(AccountAggregate aggregate) {
        var events = aggregate.pullUncommittedEvents();
        if (events.isEmpty()) {
            return;
        }
        eventStore.append(aggregate.id(), events);
        events.forEach(eventBus::publish);
    }

    /**
     * Executes a read-mutate-write operation on an existing aggregate under
     * a per-aggregate lock.
     *
     * <p>This method is the heart of the CQRS command side. It guarantees
     * that concurrent commands targeting the same aggregate are fully
     * serialized:
     *
     * <pre>{@code
     * repository.executeAtomically("acc-001", aggregate -> {
     *     aggregate.deposit(100);
     *     return null;
     * });
     * }</pre>
     *
     * @param aggregateId the aggregate identifier
     * @param operation   the mutation to apply to the loaded aggregate
     * @param <T>         the return type of the operation
     * @return the result of the operation
     * @throws AggregateNotFoundException if the aggregate does not exist
     */
    public <T> T executeAtomically(String aggregateId, Function<AccountAggregate, T> operation) {
        Objects.requireNonNull(aggregateId, "aggregateId cannot be null");
        Objects.requireNonNull(operation, "operation cannot be null");

        Lock lock = this.lock.lockFor(aggregateId);
        lock.lock();
        try {
            var aggregate = load(aggregateId);
            var result = operation.apply(aggregate);
            save(aggregate);
            return result;
        } finally {
            lock.unlock();
        }
    }
}
