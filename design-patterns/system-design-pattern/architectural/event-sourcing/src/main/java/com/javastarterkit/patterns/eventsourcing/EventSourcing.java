package com.javastarterkit.patterns.eventsourcing;

import java.time.Instant;
import java.util.ArrayList;
import java.util.List;

/**
 * Event Sourcing Pattern Example
 *
 * <p>Instead of storing just the current state of an entity, Event Sourcing
 * stores a sequence of immutable {@link DomainEvent}s that describe every state
 * change. The current state is derived by replaying the event stream from the
 * beginning. This gives a full audit trail, the ability to rebuild state at any
 * point in time, and natural support for temporal queries.
 *
 * <p>This self-contained example models a simple bank account aggregate:
 * <ul>
 *   <li><b>Domain Events</b> — {@link AccountOpened}, {@link MoneyDeposited},
 *       {@link MoneyWithdrawn} (immutable records with timestamps)</li>
 *   <li><b>Event Store</b> — {@link EventStore} appends events to a stream and
 *       retrieves them for replay</li>
 *   <li><b>Aggregate</b> — {@link AccountAggregate} applies events to build
 *       current state and enforces business invariants</li>
 *   <li><b>Snapshot</b> — {@link Snapshot} stores a pre-computed state to avoid
 *       replaying the entire stream on every load</li>
 *   <li><b>Projection</b> — {@link BalanceProjection} builds a query-optimized
 *       read model from the event stream</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class EventSourcing {

    /**
     * Demonstrates event sourcing: open an account, deposit/withdraw money,
     * replay the event stream to rebuild state, take a snapshot, and query a
     * projection.
     */
    public static void demonstrate() {
        System.out.println("\n=== Event Sourcing Pattern ===");
        System.out.println("Store state changes as a sequence of immutable events\n");

        EventStore eventStore = new EventStore();
        BalanceProjection projection = new BalanceProjection();

        // --- Open a new account and perform some transactions -------------------
        String accountId = "acc-001";
        List<DomainEvent> changes = new ArrayList<>();

        changes.add(new AccountOpened(accountId, "Alice", 100, Instant.now(), 1));
        changes.add(new MoneyDeposited(accountId, 200, Instant.now(), 2));
        changes.add(new MoneyWithdrawn(accountId, 50, Instant.now(), 3));
        changes.add(new MoneyDeposited(accountId, 300, Instant.now(), 4));

        // Append all events to the store
        eventStore.appendToStream(accountId, changes);
        System.out.println("Events appended to stream '" + accountId + "': " + changes.size());

        // --- Rebuild state by replaying the event stream ------------------------
        System.out.println("\n--- Replaying event stream to rebuild state ---");
        AccountAggregate account = AccountAggregate.replay(eventStore.loadStream(accountId));
        System.out.println("Rebuilt state: " + account);

        // --- Apply a new command (generates new events) -------------------------
        System.out.println("\n--- Applying a new command ---");
        List<DomainEvent> newEvents = account.withdraw(80);
        eventStore.appendToStream(accountId, newEvents);
        System.out.println("After withdrawal of 80: " + account);

        // --- Snapshot optimization ----------------------------------------------
        System.out.println("\n--- Snapshot optimization ---");
        Snapshot snapshot = Snapshot.take(account);
        System.out.println("Snapshot taken at version " + snapshot.version() + ": " + snapshot.state());

        // Simulate loading from snapshot + subsequent events
        AccountAggregate fromSnapshot = AccountAggregate.fromSnapshot(snapshot);
        List<DomainEvent> eventsAfterSnapshot = eventStore.loadStreamFromVersion(accountId, snapshot.version());
        eventsAfterSnapshot.forEach(fromSnapshot::applyEvent);
        System.out.println("State from snapshot + " + eventsAfterSnapshot.size()
                + " replayed event(s): " + fromSnapshot);

        // --- Projection: build a read model from the event stream ---------------
        System.out.println("\n--- Projection (read model from events) ---");
        eventStore.loadStream(accountId).forEach(projection::onEvent);
        System.out.println("Total deposited:  " + projection.totalDeposited());
        System.out.println("Total withdrawn:  " + projection.totalWithdrawn());
        System.out.println("Current balance:  " + projection.currentBalance());
        System.out.println("Transaction count: " + projection.transactionCount());

        // --- Full audit trail ---------------------------------------------------
        System.out.println("\n--- Full audit trail ---");
        eventStore.loadStream(accountId).forEach(e ->
                System.out.println("  " + e.timestamp() + " | " + e.getClass().getSimpleName() + " | " + e));

        System.out.println("\nBenefits:");
        System.out.println("- Complete audit trail of all state changes");
        System.out.println("- State can be rebuilt at any point in time by replaying events");
        System.out.println("- Snapshots optimize loading for long event streams");
        System.out.println("- Projections build query-optimized read models from events");
        System.out.println("- Events are immutable and append-only (no lost history)");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // Domain Events — immutable records describing state changes
    // =========================================================================

    /** Base type for all domain events. */
    sealed interface DomainEvent permits AccountOpened, MoneyDeposited, MoneyWithdrawn {
        String aggregateId();

        Instant timestamp();

        long version();
    }

    /** Event: a new account was opened with an initial balance. */
    record AccountOpened(
            String aggregateId,
            String owner,
            int initialBalance,
            Instant timestamp,
            long version) implements DomainEvent {
    }

    /** Event: money was deposited into an account. */
    record MoneyDeposited(
            String aggregateId,
            int amount,
            Instant timestamp,
            long version) implements DomainEvent {
    }

    /** Event: money was withdrawn from an account. */
    record MoneyWithdrawn(
            String aggregateId,
            int amount,
            Instant timestamp,
            long version) implements DomainEvent {
    }

    // =========================================================================
    // Event Store — append-only storage of event streams
    // =========================================================================

    /**
     * An in-memory append-only event store. Each aggregate has its own event
     * stream. Events are never modified or deleted — only appended.
     */
    static final class EventStore {
        private final List<DomainEvent> allEvents = new ArrayList<>();

        void appendToStream(String aggregateId, List<DomainEvent> events) {
            allEvents.addAll(events);
        }

        List<DomainEvent> loadStream(String aggregateId) {
            return allEvents.stream()
                    .filter(e -> e.aggregateId().equals(aggregateId))
                    .toList();
        }

        List<DomainEvent> loadStreamFromVersion(String aggregateId, long fromVersion) {
            return allEvents.stream()
                    .filter(e -> e.aggregateId().equals(aggregateId))
                    .filter(e -> e.version() > fromVersion)
                    .toList();
        }
    }

    // =========================================================================
    // Aggregate — applies events to build state, enforces business rules
    // =========================================================================

    /**
     * The bank account aggregate. State is derived by applying events. Commands
     * validate business invariants and produce new events (but do not directly
     * mutate state — the event is applied to do that).
     */
    static final class AccountAggregate {
        private String id;
        private String owner;
        private int balance;
        private long version;

        private AccountAggregate() {
        }

        /** Rebuilds an aggregate from scratch by replaying its entire event stream. */
        static AccountAggregate replay(List<DomainEvent> history) {
            AccountAggregate account = new AccountAggregate();
            history.forEach(account::applyEvent);
            return account;
        }

        /** Rebuilds an aggregate from a snapshot (skips replaying events before it). */
        static AccountAggregate fromSnapshot(Snapshot snapshot) {
            AccountAggregate account = new AccountAggregate();
            account.id = snapshot.state().id();
            account.owner = snapshot.state().owner();
            account.balance = snapshot.state().balance();
            account.version = snapshot.version();
            return account;
        }

        String id() {
            return id;
        }

        String owner() {
            return owner;
        }

        int balance() {
            return balance;
        }

        long version() {
            return version;
        }

        /** Command: withdraw money. Validates and returns new events (does not apply them). */
        List<DomainEvent> withdraw(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
            if (balance < amount) {
                throw new IllegalStateException("Insufficient funds: balance=" + balance + ", requested=" + amount);
            }
            List<DomainEvent> events = new ArrayList<>();
            events.add(new MoneyWithdrawn(id, amount, Instant.now(), version + 1));
            events.forEach(this::applyEvent);
            return events;
        }

        /** Command: deposit money. Validates and returns new events. */
        List<DomainEvent> deposit(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            List<DomainEvent> events = new ArrayList<>();
            events.add(new MoneyDeposited(id, amount, Instant.now(), version + 1));
            events.forEach(this::applyEvent);
            return events;
        }

        /** Applies a single event to update state (the only place state changes). */
        void applyEvent(DomainEvent event) {
            switch (event) {
                case AccountOpened e -> {
                    this.id = e.aggregateId();
                    this.owner = e.owner();
                    this.balance = e.initialBalance();
                    this.version = e.version();
                }
                case MoneyDeposited e -> {
                    this.balance += e.amount();
                    this.version = e.version();
                }
                case MoneyWithdrawn e -> {
                    this.balance -= e.amount();
                    this.version = e.version();
                }
            }
        }

        @Override
        public String toString() {
            return "Account{id=" + id + ", owner=" + owner + ", balance=" + balance + ", version=" + version + "}";
        }
    }

    // =========================================================================
    // Snapshot — pre-computed state to optimize loading long streams
    // =========================================================================

    /** Immutable snapshot of aggregate state at a specific version. */
    record Snapshot(long version, AccountState state) {
        static Snapshot take(AccountAggregate account) {
            return new Snapshot(account.version(),
                    new AccountState(account.id(), account.owner(), account.balance()));
        }
    }

    /** Serializable state captured in a snapshot. */
    record AccountState(String id, String owner, int balance) {
    }

    // =========================================================================
    // Projection — builds a query-optimized read model from events
    // =========================================================================

    /**
     * A projection that tracks aggregate financial metrics by consuming events.
     * This is a simple read model that could be stored in a separate database
     * table or cache in a real system.
     */
    static final class BalanceProjection {
        private int totalDeposited;
        private int totalWithdrawn;
        private int currentBalance;
        private int transactionCount;

        void onEvent(DomainEvent event) {
            switch (event) {
                case AccountOpened e -> {
                    totalDeposited += e.initialBalance();
                    currentBalance += e.initialBalance();
                    transactionCount++;
                }
                case MoneyDeposited e -> {
                    totalDeposited += e.amount();
                    currentBalance += e.amount();
                    transactionCount++;
                }
                case MoneyWithdrawn e -> {
                    totalWithdrawn += e.amount();
                    currentBalance -= e.amount();
                    transactionCount++;
                }
            }
        }

        int totalDeposited() {
            return totalDeposited;
        }

        int totalWithdrawn() {
            return totalWithdrawn;
        }

        int currentBalance() {
            return currentBalance;
        }

        int transactionCount() {
            return transactionCount;
        }
    }
}