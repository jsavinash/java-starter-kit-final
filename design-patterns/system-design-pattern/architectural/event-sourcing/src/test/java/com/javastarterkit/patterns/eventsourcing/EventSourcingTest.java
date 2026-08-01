package com.javastarterkit.patterns.eventsourcing;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import com.javastarterkit.patterns.eventsourcing.EventSourcing.AccountAggregate;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.AccountOpened;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.BalanceProjection;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.DomainEvent;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.EventStore;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.MoneyDeposited;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.MoneyWithdrawn;
import com.javastarterkit.patterns.eventsourcing.EventSourcing.Snapshot;
import java.time.Instant;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the event sourcing pattern: event replay rebuilds state,
 * commands produce new events, snapshots optimize loading, and projections
 * build read models from the event stream.
 */
class EventSourcingTest {

    @Test
    @DisplayName("replaying an event stream rebuilds aggregate state")
    void replayRebuildsState() {
        String accountId = "acc-001";
        List<DomainEvent> events = new ArrayList<>();
        events.add(new AccountOpened(accountId, "Alice", 100, Instant.now(), 1));
        events.add(new MoneyDeposited(accountId, 200, Instant.now(), 2));
        events.add(new MoneyWithdrawn(accountId, 50, Instant.now(), 3));

        AccountAggregate account = AccountAggregate.replay(events);

        assertEquals(accountId, account.id());
        assertEquals("Alice", account.owner());
        assertEquals(250, account.balance());
        assertEquals(3, account.version());
    }

    @Test
    @DisplayName("withdraw command produces a new event and updates state")
    void withdrawProducesEventAndUpdatesState() {
        String accountId = "acc-001";
        List<DomainEvent> events = new ArrayList<>();
        events.add(new AccountOpened(accountId, "Bob", 100, Instant.now(), 1));
        AccountAggregate account = AccountAggregate.replay(events);

        List<DomainEvent> newEvents = account.withdraw(40);

        assertEquals(1, newEvents.size());
        assertEquals(60, account.balance());
        assertEquals(2, account.version());
    }

    @Test
    @DisplayName("withdrawal exceeding balance is rejected")
    void withdrawalExceedingBalanceIsRejected() {
        String accountId = "acc-001";
        List<DomainEvent> events = new ArrayList<>();
        events.add(new AccountOpened(accountId, "Bob", 100, Instant.now(), 1));
        AccountAggregate account = AccountAggregate.replay(events);

        assertThrows(IllegalStateException.class, () -> account.withdraw(200));
    }

    @Test
    @DisplayName("snapshot captures state and can be restored")
    void snapshotCapturesAndRestoresState() {
        String accountId = "acc-001";
        List<DomainEvent> events = new ArrayList<>();
        events.add(new AccountOpened(accountId, "Alice", 100, Instant.now(), 1));
        events.add(new MoneyDeposited(accountId, 200, Instant.now(), 2));
        AccountAggregate account = AccountAggregate.replay(events);

        Snapshot snapshot = Snapshot.take(account);
        AccountAggregate restored = AccountAggregate.fromSnapshot(snapshot);

        assertEquals(account.id(), restored.id());
        assertEquals(account.owner(), restored.owner());
        assertEquals(account.balance(), restored.balance());
        assertEquals(account.version(), restored.version());
    }

    @Test
    @DisplayName("event store appends and loads events by aggregate id")
    void eventStoreAppendsAndLoads() {
        EventStore store = new EventStore();
        String accountId = "acc-001";

        List<DomainEvent> events = new ArrayList<>();
        events.add(new AccountOpened(accountId, "Alice", 100, Instant.now(), 1));
        events.add(new MoneyDeposited(accountId, 50, Instant.now(), 2));
        store.appendToStream(accountId, events);

        List<DomainEvent> loaded = store.loadStream(accountId);
        assertEquals(2, loaded.size());
    }

    @Test
    @DisplayName("event store loads events from a specific version")
    void eventStoreLoadsFromVersion() {
        EventStore store = new EventStore();
        String accountId = "acc-001";

        List<DomainEvent> events = new ArrayList<>();
        events.add(new AccountOpened(accountId, "Alice", 100, Instant.now(), 1));
        events.add(new MoneyDeposited(accountId, 50, Instant.now(), 2));
        events.add(new MoneyWithdrawn(accountId, 30, Instant.now(), 3));
        store.appendToStream(accountId, events);

        List<DomainEvent> afterVersion1 = store.loadStreamFromVersion(accountId, 1);
        assertEquals(2, afterVersion1.size());
    }

    @Test
    @DisplayName("projection aggregates financial metrics from events")
    void projectionAggregatesMetrics() {
        BalanceProjection projection = new BalanceProjection();
        String accountId = "acc-001";

        projection.onEvent(new AccountOpened(accountId, "Alice", 100, Instant.now(), 1));
        projection.onEvent(new MoneyDeposited(accountId, 200, Instant.now(), 2));
        projection.onEvent(new MoneyWithdrawn(accountId, 50, Instant.now(), 3));

        assertEquals(300, projection.totalDeposited());
        assertEquals(50, projection.totalWithdrawn());
        assertEquals(250, projection.currentBalance());
        assertEquals(3, projection.transactionCount());
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        EventSourcing.demonstrate();
    }
}