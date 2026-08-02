package com.javastarterkit.patterns.eventsourcing;

import com.javastarterkit.patterns.eventsourcing.application.projection.BalanceProjection;
import com.javastarterkit.patterns.eventsourcing.application.service.AccountService;
import com.javastarterkit.patterns.eventsourcing.infrastructure.EventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.InMemoryEventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.PerAggregateLock;
import com.javastarterkit.patterns.eventsourcing.infrastructure.SnapshotStore;

/**
 * Entry point for the Event Sourcing pattern demonstration.
 *
 * <p>Wires together the infrastructure components and executes an end-to-end
 * flow: open an account, deposit/withdraw funds, take a snapshot, replay from
 * the snapshot, and query a projection.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class Main {

    private Main() {
    }

    public static void main(String[] args) {
        // ── Wire up infrastructure ──────────────────────────────────────────
        EventStore eventStore = new InMemoryEventStore();
        SnapshotStore snapshotStore = new SnapshotStore();
        PerAggregateLock perAggregateLock = new PerAggregateLock();
        AccountService accountService = new AccountService(eventStore, snapshotStore, perAggregateLock);
        BalanceProjection projection = new BalanceProjection();

        System.out.println("\n=== Event Sourcing Pattern ===");
        System.out.println("Store state changes as a sequence of immutable events\n");

        // ── 1. Open account ─────────────────────────────────────────────────
        String accountId = "acc-001";
        accountService.openAccount(accountId, "Alice", 100);
        System.out.println("Opened account: " + accountService.load(accountId));

        // ── 2. Deposit / withdraw ───────────────────────────────────────────
        accountService.deposit(accountId, 200);
        accountService.withdraw(accountId, 50);
        accountService.deposit(accountId, 300);
        System.out.println("After transactions: " + accountService.load(accountId));

        // ── 3. Apply a new command (withdrawal) ─────────────────────────────
        accountService.withdraw(accountId, 80);
        System.out.println("After withdrawal of 80: " + accountService.load(accountId));

        // ── 4. Snapshot optimization ────────────────────────────────────────
        accountService.takeSnapshot(accountId);
        System.out.println("Snapshot taken at version "
                + snapshotStore.load(accountId).orElseThrow().version());

        // Rehydrate from snapshot + tail events
        var rehydrated = accountService.load(accountId);
        System.out.println("Rehydrated from snapshot: " + rehydrated);

        // ── 5. Projection (read model from events) ──────────────────────────
        eventStore.load(accountId).forEach(projection::onEvent);
        System.out.println("\n--- Projection (read model from events) ---");
        System.out.println("Total deposited:   " + projection.totalDeposited());
        System.out.println("Total withdrawn:   " + projection.totalWithdrawn());
        System.out.println("Current balance:   " + projection.currentBalance());
        System.out.println("Transaction count: " + projection.transactionCount());

        // ── 6. Full audit trail ─────────────────────────────────────────────
        System.out.println("\n--- Full audit trail ---");
        eventStore.load(accountId).forEach(e ->
                System.out.println("  v" + e.version() + " | "
                        + e.getClass().getSimpleName() + " | " + e));

        // ── 7. Close account ────────────────────────────────────────────────
        accountService.closeAccount(accountId);
        System.out.println("\nAfter close: " + accountService.load(accountId));

        // ── 8. Demonstrate failure: withdraw after close ────────────────────
        try {
            accountService.withdraw(accountId, 10);
        } catch (RuntimeException ex) {
            System.out.println("Expected failure after close: " + ex.getMessage());
        }

        System.out.println("\nBenefits:");
        System.out.println("- Complete audit trail of all state changes");
        System.out.println("- State can be rebuilt at any point in time by replaying events");
        System.out.println("- Snapshots optimize loading for long event streams");
        System.out.println("- Projections build query-optimized read models from events");
        System.out.println("- Events are immutable and append-only (no lost history)");
    }
}