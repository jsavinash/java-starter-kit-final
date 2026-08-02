package com.javastarterkit.patterns.eventsourcing;

import com.javastarterkit.patterns.eventsourcing.application.projection.BalanceProjection;
import com.javastarterkit.patterns.eventsourcing.application.service.AccountService;
import com.javastarterkit.patterns.eventsourcing.exception.AggregateNotFoundException;
import com.javastarterkit.patterns.eventsourcing.exception.DomainException;
import com.javastarterkit.patterns.eventsourcing.exception.OptimisticLockException;
import com.javastarterkit.patterns.eventsourcing.infrastructure.AccountState;
import com.javastarterkit.patterns.eventsourcing.infrastructure.EventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.InMemoryEventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.PerAggregateLock;
import com.javastarterkit.patterns.eventsourcing.infrastructure.SnapshotStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.time.Instant;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Functional tests for the Event Sourcing pattern covering the end-to-end
 * write path, rehydration, snapshots, projections, and invariants.
 */
class EventSourcingTest {

    private EventStore eventStore;
    private SnapshotStore snapshotStore;
    private PerAggregateLock perAggregateLock;
    private AccountService accountService;

    @BeforeEach
    void setUp() {
        eventStore = new InMemoryEventStore();
        snapshotStore = new SnapshotStore();
        perAggregateLock = new PerAggregateLock();
        accountService = new AccountService(eventStore, snapshotStore, perAggregateLock);
    }

    @Test
    @DisplayName("Open account with initial balance persists AccountOpened event")
    void openAccount_persistsAccountOpenedEvent() {
        accountService.openAccount("acc-001", "Alice", 100);

        assertThat(eventStore.latestVersion("acc-001")).isEqualTo(1);
        assertThat(eventStore.load("acc-001")).hasSize(1);

        var account = accountService.load("acc-001");
        assertThat(account.id()).isEqualTo("acc-001");
        assertThat(account.owner()).isEqualTo("Alice");
        assertThat(account.balance()).isEqualTo(100);
        assertThat(account.version()).isEqualTo(1);
    }

    @Test
    @DisplayName("Deposit and withdraw persist events and update aggregate state")
    void depositAndWithdraw_updateStateAndAppendEvents() {
        accountService.openAccount("acc-001", "Alice", 100);
        accountService.deposit("acc-001", 200);
        accountService.withdraw("acc-001", 50);
        accountService.deposit("acc-001", 300);
        accountService.withdraw("acc-001", 80);

        var account = accountService.load("acc-001");
        assertThat(account.balance()).isEqualTo(470);
        assertThat(account.version()).isEqualTo(5);
        assertThat(eventStore.latestVersion("acc-001")).isEqualTo(5);
        assertThat(eventStore.load("acc-001")).hasSize(5);
    }

    @Test
    @DisplayName("Replaying event stream rebuilds aggregate state deterministically")
    void replayEventStream_rebuildsState() {
        accountService.openAccount("acc-001", "Alice", 100);
        accountService.deposit("acc-001", 200);
        accountService.withdraw("acc-001", 50);
        accountService.deposit("acc-001", 300);

        // Create a new service instance that shares the same event store
        var service2 = new AccountService(eventStore, new SnapshotStore(), new PerAggregateLock());
        var rebuilt = service2.load("acc-001");

        assertThat(rebuilt.balance()).isEqualTo(550);
        assertThat(rebuilt.owner()).isEqualTo("Alice");
        assertThat(rebuilt.version()).isEqualTo(4);
    }

    @Test
    @DisplayName("Snapshot preserves state and subsequent replay from snapshot is consistent")
    void snapshotAndReplay_fromSnapshotIsConsistent() {
        accountService.openAccount("acc-001", "Alice", 100);
        accountService.deposit("acc-001", 200);
        accountService.withdraw("acc-001", 50);

        // Take snapshot at version 3
        accountService.takeSnapshot("acc-001");

        var snapshot = snapshotStore.load("acc-001").orElseThrow();
        assertThat(snapshot.version()).isEqualTo(3);
        assertThat(snapshot.state()).isEqualTo(new AccountState("acc-001", "Alice", 250, false));

        // Continue with more events, then reload from snapshot +
        accountService.deposit("acc-001", 300);
        var reloaded = accountService.load("acc-001");
        assertThat(reloaded.balance()).isEqualTo(550);
        assertThat(reloaded.version()).isEqualTo(4);
    }

    @Test
    @DisplayName("Projection builds a read model from the event stream")
    void projection_buildsReadModel() {
        accountService.openAccount("acc-001", "Alice", 100);
        accountService.deposit("acc-001", 200);
        accountService.withdraw("acc-001", 50);
        accountService.deposit("acc-001", 300);

        BalanceProjection projection = new BalanceProjection();
        eventStore.load("acc-001").forEach(projection::onEvent);

        assertThat(projection.totalDeposited()).isEqualTo(600);
        assertThat(projection.totalWithdrawn()).isEqualTo(50);
        assertThat(projection.currentBalance()).isEqualTo(550);
        assertThat(projection.transactionCount()).isEqualTo(4);
    }

    @Test
    @DisplayName("Withdraw more than balance throws DomainException")
    void withdrawOverBalance_throwsDomainException() {
        accountService.openAccount("acc-001", "Alice", 100);

        assertThatThrownBy(() -> accountService.withdraw("acc-001", 150))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Insufficient funds");

        // State unchanged
        assertThat(accountService.load("acc-001").balance()).isEqualTo(100);
        assertThat(eventStore.latestVersion("acc-001")).isEqualTo(1);
    }

    @Test
    @DisplayName("Deposit non-positive amount throws DomainException")
    void depositNonPositive_throwsDomainException() {
        accountService.openAccount("acc-001", "Alice", 100);

        assertThatThrownBy(() -> accountService.deposit("acc-001", 0))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("must be positive");
    }

    @Test
    @DisplayName("Close account prevents further transactions")
    void closeAccount_preventsFurtherTransactions() {
        accountService.openAccount("acc-001", "Alice", 100);
        accountService.closeAccount("acc-001");

        assertThat(accountService.load("acc-001").isClosed()).isTrue();

        assertThatThrownBy(() -> accountService.deposit("acc-001", 50))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("closed account");

        assertThatThrownBy(() -> accountService.withdraw("acc-001", 10))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("closed account");
    }

    @Test
    @DisplayName("Loading non-existent aggregate throws AggregateNotFoundException")
    void loadNonExistent_throwsAggregateNotFoundException() {
        assertThatThrownBy(() -> accountService.load("acc-999"))
                .isInstanceOf(AggregateNotFoundException.class)
                .hasMessageContaining("acc-999");
    }

    @Test
    @DisplayName("EventStore append with stale expectedVersion throws OptimisticLockException")
    void appendWithStaleVersion_throwsOptimisticLockException() {
        accountService.openAccount("acc-001", "Alice", 100);
        accountService.deposit("acc-001", 200);

        var conflictEvent = new com.javastarterkit.patterns.eventsourcing.domain.event.MoneyDeposited(
                "acc-001", 500, Instant.now(), 3);

        // Try to append with stale expectedVersion (1 instead of current 2)
        assertThatThrownBy(() ->
                eventStore.append("acc-001", java.util.List.of(conflictEvent), 1))
                .isInstanceOf(OptimisticLockException.class)
                .hasMessageContaining("Version conflict");

        // Stream unchanged
        assertThat(eventStore.latestVersion("acc-001")).isEqualTo(2);
    }

    @Test
    @DisplayName("Negative initial balance throws DomainException")
    void negativeInitialBalance_throwsDomainException() {
        assertThatThrownBy(() -> accountService.openAccount("acc-001", "Alice", -10))
                .isInstanceOf(DomainException.class)
                .hasMessageContaining("Initial balance cannot be negative");
    }
}