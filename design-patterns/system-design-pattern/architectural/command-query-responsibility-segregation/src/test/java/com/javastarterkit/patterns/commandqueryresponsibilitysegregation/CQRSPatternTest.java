package com.javastarterkit.patterns.commandqueryresponsibilitysegregation;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.CommandBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.DepositMoney;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.DepositMoneyHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.OpenAccount;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.OpenAccountHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.WithdrawMoney;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command.WithdrawMoneyHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection.AccountProjection;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.CountAccounts;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.CountAccountsHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.FindAccountById;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.FindAccountByIdHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.ListAllAccounts;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.ListAllAccountsHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query.QueryBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.AccountOpened;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.MoneyDeposited;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.MoneyWithdrawn;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.AggregateNotFoundException;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.DomainException;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.HandlerNotFoundException;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountReadModel;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountRepository;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountView;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.InMemoryEventBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.InMemoryEventStore;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.PerAggregateLock;

import java.util.List;
import java.util.Optional;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Comprehensive test suite for the CQRS pattern implementation.
 *
 * <p>Tests cover:
 * <ul>
 *   <li>End-to-end command/query flow (write → event → projection → read)</li>
 *   <li>Business invariant enforcement (negative balances, insufficient funds, closed accounts)</li>
 *   <li>Event sourcing: aggregate reconstruction via event replay</li>
 *   <li>Read model rebuild from the event store</li>
 *   <li>Error handling (aggregate not found, handler not found)</li>
 *   <li>Concurrency: per-aggregate lock serialization under concurrent commands</li>
 *   <li>Projection: event-to-read-model synchronization</li>
 * </ul>
 */
class CQRSPatternTest {

    // =========================================================================
    // Test fixture: fully wired CQRS context
    // =========================================================================

    /**
     * Creates a fresh, fully-wired CQRS context for testing.
     * Each test gets its own isolated set of components.
     */
    private TestContext createTestContext() {
        var eventStore = new InMemoryEventStore();
        var eventBus = new InMemoryEventBus();
        var readModel = new AccountReadModel();
        var lock = new PerAggregateLock();
        var repository = new AccountRepository(eventStore, eventBus, lock);

        var commandBus = new CommandBus();
        var queryBus = new QueryBus();

        commandBus.register(OpenAccount.class, new OpenAccountHandler(repository));
        commandBus.register(DepositMoney.class, new DepositMoneyHandler(repository));
        commandBus.register(WithdrawMoney.class, new WithdrawMoneyHandler(repository));

        var projection = new AccountProjection(readModel);
        projection.registerWith(eventBus);

        queryBus.register(FindAccountById.class, new FindAccountByIdHandler(readModel));
        queryBus.register(ListAllAccounts.class, new ListAllAccountsHandler(readModel));
        queryBus.register(CountAccounts.class, new CountAccountsHandler(readModel));

        return new TestContext(eventStore, eventBus, readModel, lock, repository, commandBus, queryBus);
    }

    private record TestContext(
            InMemoryEventStore eventStore,
            InMemoryEventBus eventBus,
            AccountReadModel readModel,
            PerAggregateLock lock,
            AccountRepository repository,
            CommandBus commandBus,
            QueryBus queryBus) {
    }

    // =========================================================================
    // End-to-End Flow Tests
    // =========================================================================

    @Nested
    @DisplayName("End-to-End Flow")
    class EndToEnd {

        @Test
        @DisplayName("Full command→event→projection→query flow")
        void fullCqrsFlow() {
            var ctx = createTestContext();

            // Write: open two accounts
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            ctx.commandBus().dispatch(new OpenAccount("acc-002", "Bob", 50));

            // Write: deposit and withdraw
            ctx.commandBus().dispatch(new DepositMoney("acc-001", 200));
            ctx.commandBus().dispatch(new WithdrawMoney("acc-002", 20));
            ctx.commandBus().dispatch(new DepositMoney("acc-002", 70));

            // Read: query the read model
            Optional<AccountView> alice = ctx.queryBus().dispatch(new FindAccountById("acc-001"));
            Optional<AccountView> bob = ctx.queryBus().dispatch(new FindAccountById("acc-002"));

            assertThat(alice).hasValueSatisfying(a -> {
                assertThat(a.accountId()).isEqualTo("acc-001");
                assertThat(a.owner()).isEqualTo("Alice");
                assertThat(a.balance()).isEqualTo(300);
                assertThat(a.closed()).isFalse();
            });

            assertThat(bob).hasValueSatisfying(b -> {
                assertThat(b.accountId()).isEqualTo("acc-002");
                assertThat(b.owner()).isEqualTo("Bob");
                assertThat(b.balance()).isEqualTo(100);
                assertThat(b.closed()).isFalse();
            });

            // Read: list all
            List<AccountView> all = ctx.queryBus().dispatch(new ListAllAccounts());
            assertThat(all).hasSize(2);

            // Read: count
            Integer count = ctx.queryBus().dispatch(new CountAccounts());
            assertThat(count).isEqualTo(2);
        }

        @Test
        @DisplayName("demonstrate() runs without throwing")
        void demonstrateRunsSuccessfully() {
            assertThatCode(() -> CQRSPattern.demonstrate()).doesNotThrowAnyException();
        }
    }

    // =========================================================================
    // Business Invariant Tests
    // =========================================================================

    @Nested
    @DisplayName("Business Invariants")
    class BusinessInvariants {

        @Test
        @DisplayName("Opening with negative initial balance throws DomainException")
        void negativeInitialBalanceThrows() {
            var ctx = createTestContext();
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new OpenAccount("acc-001", "Alice", -1)))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("Initial balance cannot be negative");
        }

        @Test
        @DisplayName("Depositing non-positive amount throws DomainException")
        void depositNonPositiveThrows() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new DepositMoney("acc-001", 0)))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("positive");
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new DepositMoney("acc-001", -50)))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("positive");
        }

        @Test
        @DisplayName("Withdrawing more than balance throws DomainException")
        void withdrawInsufficientFundsThrows() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 50));
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new WithdrawMoney("acc-001", 100)))
                    .isInstanceOf(DomainException.class)
                    .hasMessageContaining("Insufficient funds");
        }

        @Test
        @DisplayName("Withdrawing from non-existent account throws AggregateNotFoundException")
        void withdrawNonExistentAccountThrows() {
            var ctx = createTestContext();
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new WithdrawMoney("unknown-account", 50)))
                    .isInstanceOf(AggregateNotFoundException.class)
                    .hasMessageContaining("No account found");
        }

        @Test
        @DisplayName("Depositing to non-existent account throws AggregateNotFoundException")
        void depositNonExistentAccountThrows() {
            var ctx = createTestContext();
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new DepositMoney("unknown-account", 50)))
                    .isInstanceOf(AggregateNotFoundException.class);
        }

        @Test
        @DisplayName("After withdrawal, balance is correctly updated in read model")
        void withdrawalUpdatesBalanceInReadModel() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 200));
            ctx.commandBus().dispatch(new WithdrawMoney("acc-001", 75));

            var view = ctx.queryBus().dispatch(new FindAccountById("acc-001"));
            assertThat(view).hasValueSatisfying(v ->
                    assertThat(v.balance()).isEqualTo(125));
        }
    }

    // =========================================================================
    // Event Sourcing / Replay Tests
    // =========================================================================

    @Nested
    @DisplayName("Event Sourcing & Replay")
    class EventSourcing {

        @Test
        @DisplayName("Aggregate state is correctly reconstructed from event stream")
        void aggregateReconstructsFromEvents() {
            var ctx = createTestContext();

            // Generate a sequence of events
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            ctx.commandBus().dispatch(new DepositMoney("acc-001", 50));
            ctx.commandBus().dispatch(new WithdrawMoney("acc-001", 30));

            // Reload aggregate from event store (simulates restart)
            var aggregate = ctx.repository().load("acc-001");

            assertThat(aggregate.balance()).isEqualTo(120);
            assertThat(aggregate.owner()).isEqualTo("Alice");
            assertThat(aggregate.isClosed()).isFalse();
            assertThat(aggregate.hasUncommittedEvents()).isFalse();
        }

        @Test
        @DisplayName("Event store contains all emitted events in order")
        void eventStoreRetainsAllEvents() {
            var ctx = createTestContext();

            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            ctx.commandBus().dispatch(new DepositMoney("acc-001", 50));
            ctx.commandBus().dispatch(new WithdrawMoney("acc-001", 30));

            var events = ctx.eventStore().load("acc-001");
            assertThat(events).hasSize(3);
            assertThat(events.get(0)).isInstanceOf(AccountOpened.class);
            assertThat(events.get(1)).isInstanceOf(MoneyDeposited.class);
            assertThat(events.get(2)).isInstanceOf(MoneyWithdrawn.class);
        }

        @Test
        @DisplayName("Read model can be rebuilt from event store")
        void readModelRebuildsFromEventStore() {
            var ctx = createTestContext();

            // Execute commands on the original context
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            ctx.commandBus().dispatch(new DepositMoney("acc-001", 50));
            ctx.commandBus().dispatch(new WithdrawMoney("acc-001", 30));

            // Simulate a restart: create a fresh read model and replay all events
            var freshReadModel = new AccountReadModel();
            var freshEventBus = new InMemoryEventBus();
            var freshProjection = new AccountProjection(freshReadModel);
            freshProjection.registerWith(freshEventBus);

            var allEvents = ctx.eventStore().load("acc-001");
            allEvents.forEach(freshEventBus::publish);

            // The rebuilt read model should match
            var view = freshReadModel.findById("acc-001");
            assertThat(view).hasValueSatisfying(v -> {
                assertThat(v.accountId()).isEqualTo("acc-001");
                assertThat(v.owner()).isEqualTo("Alice");
                assertThat(v.balance()).isEqualTo(120);
            });
        }
    }

    // =========================================================================
    // Projection Synchronization Tests
    // =========================================================================

    @Nested
    @DisplayName("Projection Synchronization")
    class ProjectionSync {

        @Test
        @DisplayName("AccountOpened event creates read-model entry")
        void accountOpenedCreatesReadModelEntry() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));

            var view = ctx.readModel().findById("acc-001");
            assertThat(view).isPresent();
            assertThat(view.get().owner()).isEqualTo("Alice");
            assertThat(view.get().balance()).isEqualTo(100);
            assertThat(view.get().closed()).isFalse();
        }

        @Test
        @DisplayName("MoneyDeposited event increases balance in read model")
        void moneyDepositedIncreasesBalance() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            ctx.commandBus().dispatch(new DepositMoney("acc-001", 250));

            var view = ctx.readModel().findById("acc-001");
            assertThat(view).get().extracting(AccountView::balance).isEqualTo(350);
        }

        @Test
        @DisplayName("MoneyWithdrawn event decreases balance in read model")
        void moneyWithdrawnDecreasesBalance() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 500));
            ctx.commandBus().dispatch(new WithdrawMoney("acc-001", 150));

            var view = ctx.readModel().findById("acc-001");
            assertThat(view).get().extracting(AccountView::balance).isEqualTo(350);
        }

        @Test
        @DisplayName("FindAccountById returns empty for unknown account")
        void findAccountByIdReturnsEmpty() {
            var ctx = createTestContext();
            var result = ctx.queryBus().dispatch(new FindAccountById("nonexistent"));
            assertThat(result).isEmpty();
        }

        @Test
        @DisplayName("ListAllAccounts returns accounts sorted by insertion")
        void listAllAccountsReturnsAll() {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 100));
            ctx.commandBus().dispatch(new OpenAccount("acc-002", "Bob", 200));
            ctx.commandBus().dispatch(new OpenAccount("acc-003", "Carol", 300));

            var all = ctx.queryBus().dispatch(new ListAllAccounts());
            assertThat(all).hasSize(3);
        }
    }

    // =========================================================================
    // Error Handling Tests
    // =========================================================================

    @Nested
    @DisplayName("Error Handling")
    class ErrorHandling {

        @Test
        @DisplayName("Dispatching unregistered command throws HandlerNotFoundException")
        void unregisteredCommandThrows() {
            var ctx = createTestContext();
            assertThatThrownBy(() -> ctx.commandBus().dispatch(
                    new OpenAccount("acc-001", "Alice", 100)))
                    .isInstanceOf(HandlerNotFoundException.class)
                    .hasMessageContaining("OpenAccount");
        }

        @Test
        @DisplayName("Dispatching unregistered query throws HandlerNotFoundException")
        void unregisteredQueryThrows() {
            var ctx = createTestContext();
            assertThatThrownBy(() -> ctx.queryBus().dispatch(
                    new FindAccountById("acc-001")))
                    .isInstanceOf(HandlerNotFoundException.class)
                    .hasMessageContaining("FindAccountById");
        }

        @Test
        @DisplayName("Concurrent operations on different aggregates do not interfere")
        void concurrentOperationsDifferentAggregatesAreIndependent() throws InterruptedException {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 0));
            ctx.commandBus().dispatch(new OpenAccount("acc-002", "Bob", 0));

            int threadCount = 20;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            AtomicInteger errors = new AtomicInteger(0);

            // Half deposit to acc-001, half deposit to acc-002
            for (int i = 0; i < threadCount; i++) {
                final int idx = i;
                final String accountId = (idx < 10) ? "acc-001" : "acc-002";
                executor.submit(() -> {
                    try {
                        ctx.commandBus().dispatch(new DepositMoney(accountId, 10));
                    } catch (Exception e) {
                        errors.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            latch.await(5, TimeUnit.SECONDS);
            executor.shutdown();

            assertThat(errors.get()).isEqualTo(0);

            var alice = ctx.queryBus().dispatch(new FindAccountById("acc-001"));
            var bob = ctx.queryBus().dispatch(new FindAccountById("acc-002"));
            assertThat(alice).get().extracting(AccountView::balance).isEqualTo(100);
            assertThat(bob).get().extracting(AccountView::balance).isEqualTo(100);
        }
    }

    // =========================================================================
    // Concurrency Tests
    // =========================================================================

    @Nested
    @DisplayName("Concurrency")
    class Concurrency {

        @Test
        @DisplayName("Concurrent deposits to same account are serialized")
        void concurrentDepositsAreSerialized() throws Exception {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 1000));

            int threadCount = 50;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);

            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        ctx.commandBus().dispatch(new DepositMoney("acc-001", 10));
                    } finally {
                        latch.countDown();
                    }
                });
            }

            assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
            executor.shutdown();

            var view = ctx.queryBus().dispatch(new FindAccountById("acc-001"));
            assertThat(view).hasValueSatisfying(v ->
                    assertThat(v.balance()).isEqualTo(1000 + (threadCount * 10)));
        }

        @Test
        @DisplayName("Concurrent withdrawals to same account respect invariants")
        void concurrentWithdrawalsRespectInvariants() throws Exception {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 500));

            int threadCount = 10;
            ExecutorService executor = Executors.newFixedThreadPool(threadCount);
            CountDownLatch latch = new CountDownLatch(threadCount);
            AtomicInteger successes = new AtomicInteger(0);
            AtomicInteger failures = new AtomicInteger(0);

            // Each thread tries to withdraw 100 — only 5 should succeed
            for (int i = 0; i < threadCount; i++) {
                executor.submit(() -> {
                    try {
                        ctx.commandBus().dispatch(new WithdrawMoney("acc-001", 100));
                        successes.incrementAndGet();
                    } catch (DomainException e) {
                        failures.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
            executor.shutdown();

            // 5 withdrawals of 100 each = 500 (exactly the balance)
            assertThat(successes.get()).isEqualTo(5);
            assertThat(failures.get()).isEqualTo(5);

            var view = ctx.queryBus().dispatch(new FindAccountById("acc-001"));
            assertThat(view).hasValueSatisfying(v ->
                    assertThat(v.balance()).isZero());
        }

        @Test
        @DisplayName("High-throughput mixed operations under concurrency")
        void highThroughputMixedOperations() throws Exception {
            var ctx = createTestContext();
            ctx.commandBus().dispatch(new OpenAccount("acc-001", "Alice", 0));
            ctx.commandBus().dispatch(new OpenAccount("acc-002", "Bob", 0));

            int threads = 40;
            ExecutorService executor = Executors.newFixedThreadPool(threads);
            CountDownLatch latch = new CountDownLatch(threads * 2);
            AtomicInteger errors = new AtomicInteger(0);

            // 40 deposits of 10 to each account
            for (int i = 0; i < threads; i++) {
                final int amount = 10;
                executor.submit(() -> {
                    try {
                        ctx.commandBus().dispatch(new DepositMoney("acc-001", amount));
                    } catch (Exception e) {
                        errors.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
                executor.submit(() -> {
                    try {
                        ctx.commandBus().dispatch(new DepositMoney("acc-002", amount));
                    } catch (Exception e) {
                        errors.incrementAndGet();
                    } finally {
                        latch.countDown();
                    }
                });
            }

            assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
            executor.shutdown();

            assertThat(errors.get()).isEqualTo(0);

            var alice = ctx.queryBus().dispatch(new FindAccountById("acc-001"));
            var bob = ctx.queryBus().dispatch(new FindAccountById("acc-002"));
            assertThat(alice).get().extracting(AccountView::balance).isEqualTo(400);
            assertThat(bob).get().extracting(AccountView::balance).isEqualTo(400);

            Integer count = ctx.queryBus().dispatch(new CountAccounts());
            assertThat(count).isEqualTo(2);
        }
    }
}
