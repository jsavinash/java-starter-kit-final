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
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountReadModel;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountRepository;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountView;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.InMemoryEventBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.InMemoryEventStore;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.PerAggregateLock;

import java.util.List;
import java.util.Optional;

/**
 * Command Query Responsibility Segregation (CQRS) Pattern — Production-Grade
 * LLD Implementation.
 *
 * <h2>Pattern Overview</h2>
 * <p>CQRS separates the <b>command model</b> (write side — enforces business
 * invariants, uses event sourcing) from the <b>query model</b> (read side —
 * optimized for fast, denormalized reads). The two models are synchronized
 * via an event bus: commands emit domain events, a projection updates the
 * read model, and queries read from the read model.</p>
 *
 * <h2>Architecture (Clean / Onion)</h2>
 * <pre>
 * ┌──────────────────────────────────────────────────────────┐
 * │  Presentation / Entry Point                               │
 * │  CQRSPattern (composition root)                          │
 * │  ┌───────────────┐     ┌───────────────┐                │
 * │  │  CommandBus    │     │  QueryBus     │                │
 * │  └──────┬─────────┘     └───────┬───────┘                │
 * │         │                       │                        │
 * │  ┌──────┴─────────┐     ┌───────┴───────┐                │
 * │  │ CommandHandler │     │ QueryHandler  │                │
 * │  └──────┬─────────┘     └───────┬───────┘                │
 * │         │                       │                        │
 * │  ┌──────┴─────────┐              │                        │
 * │  │  AccountRepo   │              │                        │
 * │  └──────┬─────────┘              │                        │
 * │         │                        │                        │
 * │  ┌──────┴─────────┐     ┌───────┴───────┐                │
 * │  │  EventStore     │     │ AccountReadM. │                │
 * │  └──────┬─────────┘     └───────────────┘                │
 * │         │                                                │
 * │  ┌──────┴─────────┐     ┌───────────────┐                │
 * │  │  EventBus       │────>│ AccountProj.  │                │
 * │  │ (pub/sub)      │     │ (projection)  │                │
 * │  └────────────────┘     └───────────────┘                │
 * │         │                                                │
 * │  ┌──────┴─────────┐                                      │
 * │  │ AccountAggregate│     (event-sourced, domain model)   │
 * │  │ (event-sourced)│                                      │
 * │  └────────────────┘                                      │
 * │                                                        │
 * │  Event flow:  Command → Handler → Aggregate → EventStore → EventBus → Projection → ReadModel → Query → Handler → Result
 * └──────────────────────────────────────────────────────────┘
 * </pre>
 *
 * <h2>Thread-Safety Strategy</h2>
 * <ul>
 *   <li><b>Command side</b>: {@link PerAggregateLock} serializes all
 *       read-mutate-write cycles per aggregate using
 *       {@link java.util.concurrent.locks.ReentrantLock}, backed by a
 *       {@link java.util.concurrent.ConcurrentHashMap} for lock discovery.
 *       Concurrent commands to <i>different</i> aggregates never block.</li>
 *   <li><b>Event store</b>: {@link java.util.concurrent.ConcurrentHashMap}
 *       with atomic {@code compute} operations for appends.</li>
 *   <li><b>Event bus</b>: {@link java.util.concurrent.CopyOnWriteArrayList}
 *       for subscriber lists, enabling lock-free iteration during publish.</li>
 *   <li><b>Read model</b>: {@link java.util.concurrent.ConcurrentHashMap}
 *       with {@code computeIfPresent} for atomic per-key updates; reads
 *       return defensive copies.</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public final class CQRSPattern {

    /** Private constructor — this class is not instantiable (all methods are static). */
    private CQRSPattern() {
    }

    // =========================================================================
    // Composition Root
    // =========================================================================

    /**
     * Wires all CQRS components together and returns a fully configured
     * {@link CqrsContext} containing the command bus, query bus, and
     * repository.
     *
     * @return a ready-to-use CQRS component set
     */
    public static CqrsContext createContext() {
        // --- Infrastructure layer ---
        var eventStore = new InMemoryEventStore();
        var eventBus = new InMemoryEventBus();
        var readModel = new AccountReadModel();
        var perAggregateLock = new PerAggregateLock();
        var repository = new AccountRepository(eventStore, eventBus, perAggregateLock);

        // --- Application layer ---
        var commandBus = new CommandBus();
        var queryBus = new QueryBus();

        // Register command handlers (write side)
        commandBus.register(OpenAccount.class, new OpenAccountHandler(repository));
        commandBus.register(DepositMoney.class, new DepositMoneyHandler(repository));
        commandBus.register(WithdrawMoney.class, new WithdrawMoneyHandler(repository));

        // Register projection (synchronization bridge: write → read)
        var projection = new AccountProjection(readModel);
        projection.registerWith(eventBus);

        // Register query handlers (read side)
        queryBus.register(FindAccountById.class, new FindAccountByIdHandler(readModel));
        queryBus.register(ListAllAccounts.class, new ListAllAccountsHandler(readModel));
        queryBus.register(CountAccounts.class, new CountAccountsHandler(readModel));

        return new CqrsContext(commandBus, queryBus);
    }

    // =========================================================================
    // Entry Point — Executable Demonstration
    // =========================================================================

    /**
     * Demonstrates the complete CQRS flow end-to-end:
     * <ol>
     *   <li>Open two accounts</li>
     *   <li>Deposit and withdraw money</li>
     *   <li>Query the read model to verify the projection updated it</li>
     * </ol>
     */
    public static void demonstrate() {
        System.out.println("=== Command Query Responsibility Segregation (CQRS) Pattern ===");
        System.out.println("Separates write (command) and read (query) models for independent");
        System.out.println("scaling and optimization\n");

        var context = createContext();
        var commandBus = context.commandBus();
        var queryBus = context.queryBus();

        // ── Write side: dispatch commands ──────────────────────────────────
        System.out.println("--- COMMAND SIDE (writes) ---");
        commandBus.dispatch(new OpenAccount("acc-001", "Alice", 100));
        System.out.println("  [CMD] OpenAccount owner=Alice initialBalance=100");
        commandBus.dispatch(new OpenAccount("acc-002", "Bob", 50));
        System.out.println("  [CMD] OpenAccount owner=Bob initialBalance=50");
        commandBus.dispatch(new DepositMoney("acc-001", 200));
        System.out.println("  [CMD] DepositMoney id=acc-001 amount=200");
        commandBus.dispatch(new WithdrawMoney("acc-002", 20));
        System.out.println("  [CMD] WithdrawMoney id=acc-002 amount=20");
        commandBus.dispatch(new DepositMoney("acc-002", 70));
        System.out.println("  [CMD] DepositMoney id=acc-002 amount=70");

        // ── Read side: dispatch queries ────────────────────────────────────
        System.out.println("\n--- QUERY SIDE (reads) ---");
        Optional<AccountView> alice = queryBus.dispatch(new FindAccountById("acc-001"));
        System.out.println("  Find Alice by id: " + alice);
        Optional<AccountView> bob = queryBus.dispatch(new FindAccountById("acc-002"));
        System.out.println("  Find Bob by id:   " + bob);
        List<AccountView> all = queryBus.dispatch(new ListAllAccounts());
        System.out.println("  All accounts:     " + all);
        Integer count = queryBus.dispatch(new CountAccounts());
        System.out.println("  Account count:    " + count);

        System.out.println("\nBenefits:");
        System.out.println("  - Write model is optimized for business rules & validation");
        System.out.println("  - Read model is optimized for queries (denormalized, fast lookups)");
        System.out.println("  - Read and write sides can scale independently");
        System.out.println("  - Read model can be rebuilt from the event stream at any time");
        System.out.println("  - Thread-safe: per-aggregate locks serialize concurrent commands");
    }

    /**
     * Runnable entry point — executes the demonstration.
     *
     * @param args command-line arguments (ignored)
     */
    public static void main(String[] args) {
        demonstrate();
    }

    /**
     * Immutable record holding the wired CQRS component set.
     *
     * @param commandBus the command bus for dispatching write operations
     * @param queryBus   the query bus for dispatching read operations
     */
    public record CqrsContext(CommandBus commandBus, QueryBus queryBus) {
    }
}
