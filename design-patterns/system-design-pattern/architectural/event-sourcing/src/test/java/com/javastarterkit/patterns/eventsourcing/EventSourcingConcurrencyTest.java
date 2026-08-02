package com.javastarterkit.patterns.eventsourcing;

import com.javastarterkit.patterns.eventsourcing.application.service.AccountService;
import com.javastarterkit.patterns.eventsourcing.infrastructure.EventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.InMemoryEventStore;
import com.javastarterkit.patterns.eventsourcing.infrastructure.PerAggregateLock;
import com.javastarterkit.patterns.eventsourcing.infrastructure.SnapshotStore;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Concurrency tests verifying that the Event Sourcing system serializes
 * concurrent commands on the same aggregate via {@link PerAggregateLock}
 * and {@link InMemoryEventStore} optimistic locking.
 *
 * <p>16 threads × 50 operations = 800 concurrent commands on the same
 * aggregate. The final balance must be deterministic regardless of thread
 * interleaving.
 */
class EventSourcingConcurrencyTest {

    private static final String ACCOUNT_ID = "acc-conc-001";
    private static final int THREADS = 16;
    private static final int OPERATIONS_PER_THREAD = 50;

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
    @DisplayName("16 threads x 50 deposits on same aggregate produce deterministic result")
    void concurrentDeposits_areSerialized_andProduceDeterministicBalance() throws Exception {
        // Open account with 0 balance
        accountService.openAccount(ACCOUNT_ID, "Concurrent", 0);

        int depositPerOp = 10;
        int totalExpected = THREADS * OPERATIONS_PER_THREAD * depositPerOp;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(THREADS);

        try (ExecutorService executor = Executors.newFixedThreadPool(THREADS)) {
            for (int t = 0; t < THREADS; t++) {
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                            accountService.deposit(ACCOUNT_ID, depositPerOp);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            assertThat(doneLatch.await(30, TimeUnit.SECONDS)).isTrue();
        }

        // Verify deterministic final state
        var account = accountService.load(ACCOUNT_ID);
        assertThat(account.balance()).isEqualTo(totalExpected);
        assertThat(account.version()).isEqualTo(1 + (long) THREADS * OPERATIONS_PER_THREAD);
        assertThat(eventStore.load(ACCOUNT_ID)).hasSize(1 + THREADS * OPERATIONS_PER_THREAD);
    }

    @Test
    @DisplayName("Concurrent deposits and withdrawals maintain a correct balance")
    void concurrentDepositsAndWithdrawals_maintainCorrectBalance() throws Exception {
        accountService.openAccount(ACCOUNT_ID, "Concurrent", 1000);

        int depositPerOp = 10;
        int withdrawPerOp = 5;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(THREADS);
        AtomicInteger successCount = new AtomicInteger();

        try (ExecutorService executor = Executors.newFixedThreadPool(THREADS)) {
            for (int t = 0; t < THREADS; t++) {
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        for (int i = 0; i < OPERATIONS_PER_THREAD; i++) {
                            accountService.deposit(ACCOUNT_ID, depositPerOp);
                            accountService.withdraw(ACCOUNT_ID, withdrawPerOp);
                            successCount.incrementAndGet();
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            assertThat(doneLatch.await(30, TimeUnit.SECONDS)).isTrue();
        }

        // Each thread: 50 deposits of 10 + 50 withdrawals of 5 = +500 per thread
        // Total for 16 threads = +8000
        int expectedChange = THREADS * OPERATIONS_PER_THREAD * (depositPerOp - withdrawPerOp);
        assertThat(successCount.get()).isEqualTo(THREADS * OPERATIONS_PER_THREAD);

        var account = accountService.load(ACCOUNT_ID);
        assertThat(account.balance()).isEqualTo(1000 + expectedChange);
    }

    @Test
    @DisplayName("Different aggregates proceed independently in parallel")
    void differentAggregates_areIndependent() throws Exception {
        int numAccounts = 8;
        int opsPerAccount = 100;
        int depositPerOp = 5;

        CountDownLatch startLatch = new CountDownLatch(1);
        CountDownLatch doneLatch = new CountDownLatch(numAccounts);

        for (int a = 0; a < numAccounts; a++) {
            final String aggId = "acc-par-" + a;
            accountService.openAccount(aggId, "Owner-" + a, 0);
        }

        try (ExecutorService executor = Executors.newFixedThreadPool(numAccounts)) {
            for (int a = 0; a < numAccounts; a++) {
                final String aggId = "acc-par-" + a;
                executor.submit(() -> {
                    try {
                        startLatch.await();
                        for (int i = 0; i < opsPerAccount; i++) {
                            accountService.deposit(aggId, depositPerOp);
                        }
                    } catch (InterruptedException e) {
                        Thread.currentThread().interrupt();
                    } finally {
                        doneLatch.countDown();
                    }
                });
            }

            startLatch.countDown();
            assertThat(doneLatch.await(30, TimeUnit.SECONDS)).isTrue();
        }

        for (int a = 0; a < numAccounts; a++) {
            String aggId = "acc-par-" + a;
            var account = accountService.load(aggId);
            assertThat(account.balance()).isEqualTo(opsPerAccount * depositPerOp);
            assertThat(account.version()).isEqualTo(1L + opsPerAccount);
        }
    }
}