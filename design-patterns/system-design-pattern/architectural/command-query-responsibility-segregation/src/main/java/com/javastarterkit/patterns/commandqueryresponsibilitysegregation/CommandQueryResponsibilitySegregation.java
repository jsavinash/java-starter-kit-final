package com.javastarterkit.patterns.commandqueryresponsibilitysegregation;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

/**
 * Command Query Responsibility Segregation (CQRS) Pattern — Production-Grade LLD Implementation
 *
 * <p>Separates the model used to <b>update</b> state (the <i>command</i> / <i>write</i> side)
 * from the model used to <b>read</b> state (the <i>query</i> / <i>read</i> side). Instead of a
 * single model serving both reads and writes, each side is optimized independently:
 *
 * <ul>
 *   <li><b>Command side</b> — enforces business invariants and emits domain events using
 *       event-sourcing. The aggregate is rebuilt by replaying its event stream.</li>
 *   <li><b>Query side</b> — maintains a denormalized, query-optimized read model that is
 *       kept synchronized via a projection that subscribes to domain events.</li>
 *   <li><b>Synchronization</b> — an in-process {@link EventBus} publishes events from the
 *       command side to the {@link AccountProjection}, which updates the read model.</li>
 * </ul>
 *
 * <h2>Architecture</h2>
 * <ul>
 *   <li><b>Command Side</b> — {@link AccountAggregate}, {@link EventStore},
 *       {@link AccountRepository}, {@link Command}, {@link CommandHandler},
 *       {@link CommandBus}</li>
 *   <li><b>Query Side</b> — {@link AccountView}, {@link AccountReadModel},
 *       {@link Query}, {@link QueryHandler}, {@link QueryBus}</li>
 *   <li><b>Synchronization</b> — {@link DomainEvent}, {@link EventBus},
 *       {@link AccountProjection}</li>
 * </ul>
 *
 * <h2>Thread-Safety Strategy</h2>
 * <ul>
 *   <li>{@link EventStore} uses {@link ConcurrentHashMap} for thread-safe event stream storage</li>
 *   <li>{@link AccountReadModel} uses {@link ConcurrentHashMap} for thread-safe read model access</li>
 *   <li>{@link EventBus} uses {@link ConcurrentHashMap} + {@link CopyOnWriteArrayList}
 *       for thread-safe pub/sub</li>
 *   <li>{@link CommandBus} and {@link QueryBus} use {@link ConcurrentHashMap} for handler routing</li>
 *   <li>All model objects ({@link AccountView}, commands, queries, events) are immutable
 *       Java records — no shared mutable state</li>
 * </ul>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class CommandQueryResponsibilitySegregation {

    /**
     * Demonstrates the CQRS flow end-to-end: issue commands, publish events,
     * project into the read model, then execute queries against it.
     */
    public static void demonstrate() {
        System.out.println("\n=== Command Query Responsibility Segregation (CQRS) Pattern ===");
        System.out.println("Separates write (command) and read (query) models for independent scaling and optimization\n");

        // --- Wire up the CQRS infrastructure -------------------------------------
        EventBus eventBus = new EventBus();
        EventStore eventStore = new EventStore();
        AccountRepository repository = new AccountRepository(eventStore, eventBus);
        AccountReadModel readModel = new AccountReadModel();
        AccountProjection projection = new AccountProjection(readModel);

        // The projection subscribes to domain events so the read model stays in sync.
        eventBus.subscribe(AccountOpened.class, projection::onAccountOpened);
        eventBus.subscribe(MoneyDeposited.class, projection::onMoneyDeposited);
        eventBus.subscribe(MoneyWithdrawn.class, projection::onMoneyWithdrawn);
        eventBus.subscribe(AccountClosed.class, projection::onAccountClosed);

        // Command bus dispatches commands to their handlers.
        CommandBus commandBus = new CommandBus();
        commandBus.register(new OpenAccountHandler(repository));
        commandBus.register(new DepositMoneyHandler(repository));
        commandBus.register(new WithdrawMoneyHandler(repository));
        commandBus.register(new CloseAccountHandler(repository));

        // Query bus dispatches queries to their handlers.
        QueryBus queryBus = new QueryBus();
        queryBus.register(new FindAccountByIdHandler(readModel));
        queryBus.register(new ListAllAccountsHandler(readModel));
        queryBus.register(new CountAccountsHandler(readModel));
        queryBus.register(new FindAccountsByOwnerHandler(readModel));

        // --- Command side: mutate the write model --------------------------------
        System.out.println("--- COMMAND SIDE (writes) ---");
        String aliceId = commandBus.dispatch(new OpenAccount("Alice", 100));
        String bobId = commandBus.dispatch(new OpenAccount("Bob", 50));
        commandBus.dispatch(new DepositMoney(aliceId, 200));
        commandBus.dispatch(new WithdrawMoney(bobId, 20));
        commandBus.dispatch(new DepositMoney(bobId, 70));
        commandBus.dispatch(new CloseAccount(bobId));

        // --- Query side: read from the optimized read model ----------------------
        System.out.println("\n--- QUERY SIDE (reads) ---");
        System.out.println("Find Alice by id:  " + queryBus.dispatch(new FindAccountById(aliceId)));
        System.out.println("Find Bob by id:    " + queryBus.dispatch(new FindAccountById(bobId)));
        System.out.println("All open accounts: " + queryBus.dispatch(new ListAllAccounts()));
        System.out.println("Open account count:" + queryBus.dispatch(new CountAccounts()));
        System.out.println("Accounts by owner: " + queryBus.dispatch(new FindAccountsByOwner("Alice")));

        System.out.println("\nBenefits:");
        System.out.println("- Write model is optimized for business rules & validation");
        System.out.println("- Read model is optimized for queries (denormalized, fast lookups)");
        System.out.println("- Read and write sides can scale independently");
        System.out.println("- Read model can be rebuilt from the event stream at any time");
        System.out.println("- Thread-safe infrastructure supports concurrent command/query dispatch");
    }

    /**
     * Main entry point — runs the end-to-end demonstration.
     *
     * @param args command-line arguments (unused)
     */
    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // EXCEPTIONS — domain-specific error types
    // =========================================================================

    /** Thrown when a command violates a business invariant. */
    static final class DomainException extends RuntimeException {
        DomainException(String message) {
            super(message);
        }
    }

    /** Thrown when an aggregate is not found in the event store. */
    static final class AggregateNotFoundException extends RuntimeException {
        AggregateNotFoundException(String message) {
            super(message);
        }
    }

    /** Thrown when no handler is registered for a command or query. */
    static final class HandlerNotFoundException extends RuntimeException {
        HandlerNotFoundException(String message) {
            super(message);
        }
    }

    // =========================================================================
    // Domain Events — emitted by the command side, consumed by projections
    // =========================================================================

    /** Base type for all domain events. Each event carries the aggregate id. */
    interface DomainEvent {
        String aggregateId();
    }

    /** Event: a new account was opened with an initial balance. */
    record AccountOpened(String aggregateId, String owner, int initialBalance) implements DomainEvent {
        @Override
        public String aggregateId() {
            return aggregateId;
        }
    }

    /** Event: money was deposited into an account. */
    record MoneyDeposited(String aggregateId, int amount) implements DomainEvent {
        @Override
        public String aggregateId() {
            return aggregateId;
        }
    }

    /** Event: money was withdrawn from an account. */
    record MoneyWithdrawn(String aggregateId, int amount) implements DomainEvent {
        @Override
        public String aggregateId() {
            return aggregateId;
        }
    }

    /** Event: an account was closed. */
    record AccountClosed(String aggregateId) implements DomainEvent {
        @Override
        public String aggregateId() {
            return aggregateId;
        }
    }

    // =========================================================================
    // Event Bus — thread-safe in-process pub/sub connecting command side to read side
    // =========================================================================

    /**
     * A thread-safe in-memory event bus. Projections register as subscribers for
     * specific event types; the command side publishes events after each mutation.
     *
     * <p>Thread-safety is achieved via {@link ConcurrentHashMap} for the subscriber
     * map and {@link CopyOnWriteArrayList} for the subscriber lists, supporting
     * concurrent publish and subscribe operations without external locking.
     */
    static final class EventBus {
        private final Map<Class<?>, List<Consumer<?>>> subscribers = new ConcurrentHashMap<>();

        /**
         * Subscribes a consumer to a specific event type.
         *
         * @param eventType  the class of the event to subscribe to
         * @param subscriber the consumer that will handle the event
         * @param <E>        the event type
         */
        <E> void subscribe(Class<E> eventType, Consumer<E> subscriber) {
            subscribers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(subscriber);
        }

        /**
         * Publishes an event to all subscribers of its type.
         *
         * @param event the domain event to publish
         */
        @SuppressWarnings("unchecked")
        void publish(DomainEvent event) {
            List<Consumer<?>> subs = subscribers.get(event.getClass());
            if (subs != null) {
                for (Consumer<?> sub : subs) {
                    ((Consumer<DomainEvent>) sub).accept(event);
                }
            }
        }

        /**
         * Returns the number of subscribers for a given event type.
         *
         * @param eventType the event class
         * @return the subscriber count
         */
        int subscriberCount(Class<?> eventType) {
            List<Consumer<?>> subs = subscribers.get(eventType);
            return subs == null ? 0 : subs.size();
        }
    }

    // =========================================================================
    // Command Side — aggregate, event store, repository, commands, handlers, bus
    // =========================================================================

    /**
     * The write-side aggregate. Enforces business invariants (e.g. no negative
     * balances, positive amounts, no operations on closed accounts) and emits
     * domain events on every state change. The aggregate is rebuilt by replaying
     * its event stream from the {@link EventStore}.
     */
    static final class AccountAggregate {
        private final String id;
        private String owner;
        private int balance;
        private boolean closed;
        private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

        AccountAggregate(String id) {
            this.id = Objects.requireNonNull(id, "Account id cannot be null");
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

        boolean isClosed() {
            return closed;
        }

        /** Factory: opens a new account with an initial balance. */
        static AccountAggregate open(String id, String owner, int initialBalance) {
            if (initialBalance < 0) {
                throw new DomainException("Initial balance cannot be negative");
            }
            Objects.requireNonNull(owner, "Owner cannot be null");
            AccountAggregate account = new AccountAggregate(id);
            account.apply(new AccountOpened(id, owner, initialBalance));
            return account;
        }

        /** Deposits a positive amount into the account. */
        void deposit(int amount) {
            if (closed) {
                throw new DomainException("Cannot deposit into a closed account: " + id);
            }
            if (amount <= 0) {
                throw new DomainException("Deposit amount must be positive");
            }
            apply(new MoneyDeposited(id, amount));
        }

        /** Withdraws a positive amount from the account if sufficient funds exist. */
        void withdraw(int amount) {
            if (closed) {
                throw new DomainException("Cannot withdraw from a closed account: " + id);
            }
            if (amount <= 0) {
                throw new DomainException("Withdrawal amount must be positive");
            }
            if (balance < amount) {
                throw new DomainException(
                        "Insufficient funds: balance=" + balance + ", requested=" + amount);
            }
            apply(new MoneyWithdrawn(id, amount));
        }

        /** Closes the account. No further operations are permitted after closing. */
        void close() {
            if (closed) {
                throw new DomainException("Account is already closed: " + id);
            }
            apply(new AccountClosed(id));
        }

        /** Applies an event to the aggregate and records it as uncommitted. */
        private void apply(DomainEvent event) {
            uncommittedEvents.add(event);
            handle(event);
        }

        /** Replays an event without recording it (used when restoring from store). */
        void replay(DomainEvent event) {
            handle(event);
        }

        /** Dispatches an event to the appropriate state mutation method. */
        private void handle(DomainEvent event) {
            switch (event) {
                case AccountOpened e -> {
                    this.owner = e.owner();
                    this.balance = e.initialBalance();
                }
                case MoneyDeposited e -> this.balance += e.amount();
                case MoneyWithdrawn e -> this.balance -= e.amount();
                case AccountClosed e -> this.closed = true;
                default -> { }
            }
        }

        /** Pulls all uncommitted events and clears the internal buffer. */
        List<DomainEvent> pullUncommittedEvents() {
            List<DomainEvent> copy = new ArrayList<>(uncommittedEvents);
            uncommittedEvents.clear();
            return copy;
        }
    }

    /**
     * Thread-safe persistent store for the write side (simulated with an
     * in-memory {@link ConcurrentHashMap}). Stores the event stream per aggregate.
     */
    static final class EventStore {
        private final Map<String, List<DomainEvent>> streams = new ConcurrentHashMap<>();

        /** Appends new events to the aggregate's event stream. */
        void save(String id, List<DomainEvent> newEvents) {
            streams.computeIfAbsent(id, k -> new CopyOnWriteArrayList<>()).addAll(newEvents);
        }

        /** Loads the full event stream for an aggregate. */
        List<DomainEvent> load(String id) {
            return List.copyOf(streams.getOrDefault(id, List.of()));
        }

        /** Checks whether an aggregate exists in the store. */
        boolean exists(String id) {
            return streams.containsKey(id);
        }

        /** Returns the number of aggregates in the store. */
        int aggregateCount() {
            return streams.size();
        }
    }

    /**
     * Repository for the write side. Loads aggregates by replaying their event
     * stream, and saves new events (publishing them to the event bus so the read
     * model stays synchronized).
     */
    static final class AccountRepository {
        private final EventStore store;
        private final EventBus eventBus;

        AccountRepository(EventStore store, EventBus eventBus) {
            this.store = store;
            this.eventBus = eventBus;
        }

        /** Creates a new account aggregate and persists its events. */
        String create(String owner, int initialBalance) {
            String id = UUID.randomUUID().toString();
            AccountAggregate account = AccountAggregate.open(id, owner, initialBalance);
            persist(account);
            return id;
        }

        /** Loads an aggregate by replaying its event stream from the store. */
        AccountAggregate find(String id) {
            if (!store.exists(id)) {
                throw new AggregateNotFoundException("Account not found: " + id);
            }
            List<DomainEvent> history = store.load(id);
            AccountAggregate account = new AccountAggregate(id);
            history.forEach(account::replay);
            return account;
        }

        /** Persists uncommitted events and publishes them to the event bus. */
        void persist(AccountAggregate account) {
            List<DomainEvent> newEvents = account.pullUncommittedEvents();
            if (!newEvents.isEmpty()) {
                store.save(account.id(), newEvents);
                newEvents.forEach(eventBus::publish);
            }
        }
    }

    // --- Commands & handlers -------------------------------------------------

    /** Marker interface for all commands (write-side intents). */
    interface Command {
    }

    /** Command: open a new account. Returns the new account id. */
    record OpenAccount(String owner, int initialBalance) implements Command {
    }

    /** Command: deposit money into an account. */
    record DepositMoney(String accountId, int amount) implements Command {
    }

    /** Command: withdraw money from an account. */
    record WithdrawMoney(String accountId, int amount) implements Command {
    }

    /** Command: close an account. */
    record CloseAccount(String accountId) implements Command {
    }

    /** Handler contract: each handler processes one command type and produces a result. */
    interface CommandHandler<C extends Command, R> {
        Class<C> commandType();

        R handle(C command);
    }

    /** Handler: opens a new account and returns its id. */
    static final class OpenAccountHandler implements CommandHandler<OpenAccount, String> {
        private final AccountRepository repository;

        OpenAccountHandler(AccountRepository repository) {
            this.repository = repository;
        }

        @Override
        public Class<OpenAccount> commandType() {
            return OpenAccount.class;
        }

        @Override
        public String handle(OpenAccount command) {
            String id = repository.create(command.owner(), command.initialBalance());
            System.out.println("  [CMD] OpenAccount owner=" + command.owner()
                    + " initialBalance=" + command.initialBalance() + " -> id=" + id.substring(0, 8));
            return id;
        }
    }

    /** Handler: deposits money into an existing account. */
    static final class DepositMoneyHandler implements CommandHandler<DepositMoney, Void> {
        private final AccountRepository repository;

        DepositMoneyHandler(AccountRepository repository) {
            this.repository = repository;
        }

        @Override
        public Class<DepositMoney> commandType() {
            return DepositMoney.class;
        }

        @Override
        public Void handle(DepositMoney command) {
            AccountAggregate account = repository.find(command.accountId());
            account.deposit(command.amount());
            repository.persist(account);
            System.out.println("  [CMD] DepositMoney id=" + command.accountId().substring(0, 8)
                    + " amount=" + command.amount() + " -> balance=" + account.balance());
            return null;
        }
    }

    /** Handler: withdraws money from an existing account. */
    static final class WithdrawMoneyHandler implements CommandHandler<WithdrawMoney, Void> {
        private final AccountRepository repository;

        WithdrawMoneyHandler(AccountRepository repository) {
            this.repository = repository;
        }

        @Override
        public Class<WithdrawMoney> commandType() {
            return WithdrawMoney.class;
        }

        @Override
        public Void handle(WithdrawMoney command) {
            AccountAggregate account = repository.find(command.accountId());
            account.withdraw(command.amount());
            repository.persist(account);
            System.out.println("  [CMD] WithdrawMoney id=" + command.accountId().substring(0, 8)
                    + " amount=" + command.amount() + " -> balance=" + account.balance());
            return null;
        }
    }

    /** Handler: closes an existing account. */
    static final class CloseAccountHandler implements CommandHandler<CloseAccount, Void> {
        private final AccountRepository repository;

        CloseAccountHandler(AccountRepository repository) {
            this.repository = repository;
        }

        @Override
        public Class<CloseAccount> commandType() {
            return CloseAccount.class;
        }

        @Override
        public Void handle(CloseAccount command) {
            AccountAggregate account = repository.find(command.accountId());
            account.close();
            repository.persist(account);
            System.out.println("  [CMD] CloseAccount id=" + command.accountId().substring(0, 8));
            return null;
        }
    }

    /**
     * Thread-safe command bus that routes commands to their registered handlers.
     * Uses {@link ConcurrentHashMap} for handler lookup.
     */
    static final class CommandBus {
        private final Map<Class<?>, CommandHandler<?, ?>> handlers = new ConcurrentHashMap<>();

        /** Registers a handler for a specific command type. */
        <C extends Command, R> void register(CommandHandler<C, R> handler) {
            handlers.put(handler.commandType(), handler);
        }

        /** Dispatches a command to its registered handler and returns the result. */
        @SuppressWarnings("unchecked")
        <R> R dispatch(Command command) {
            CommandHandler<Command, R> handler =
                    (CommandHandler<Command, R>) handlers.get(command.getClass());
            if (handler == null) {
                throw new HandlerNotFoundException(
                        "No handler for command: " + command.getClass().getSimpleName());
            }
            return handler.handle(command);
        }

        /** Returns the number of registered command handlers. */
        int handlerCount() {
            return handlers.size();
        }
    }

    // =========================================================================
    // Query Side — read model, projection, queries, handlers, query bus
    // =========================================================================

    /** Denormalized read model DTO optimized for fast queries. */
    record AccountView(String id, String owner, int balance, boolean closed) {
        @Override
        public String toString() {
            return "AccountView{id=" + id.substring(0, 8) + ", owner=" + owner
                    + ", balance=" + balance + ", closed=" + closed + "}";
        }
    }

    /**
     * Thread-safe read model store. In a real system this might be a NoSQL document
     * store, a search index, or a materialized view. Here it is an in-memory
     * {@link ConcurrentHashMap} keyed by account id for O(1) lookups.
     */
    static final class AccountReadModel {
        private final Map<String, AccountView> views = new ConcurrentHashMap<>();

        /** Inserts or updates a view in the read model. */
        void upsert(AccountView view) {
            views.put(view.id(), view);
        }

        /** Removes a view from the read model. */
        void remove(String id) {
            views.remove(id);
        }

        /** Finds a single account view by id. */
        Optional<AccountView> findById(String id) {
            return Optional.ofNullable(views.get(id));
        }

        /** Returns all open (non-closed) account views. */
        List<AccountView> findAllOpen() {
            return views.values().stream()
                    .filter(v -> !v.closed())
                    .toList();
        }

        /** Returns all account views (including closed). */
        List<AccountView> findAll() {
            return List.copyOf(views.values());
        }

        /** Returns the count of open accounts. */
        int count() {
            return (int) views.values().stream().filter(v -> !v.closed()).count();
        }

        /** Returns all open accounts owned by the given owner. */
        List<AccountView> findByOwner(String owner) {
            return views.values().stream()
                    .filter(v -> !v.closed())
                    .filter(v -> v.owner().equals(owner))
                    .toList();
        }
    }

    /**
     * Projection: listens to domain events and updates the read model. This is
     * the bridge that keeps the query side synchronized with the command side.
     */
    static final class AccountProjection {
        private final AccountReadModel readModel;

        AccountProjection(AccountReadModel readModel) {
            this.readModel = readModel;
        }

        /** Handles AccountOpened events by inserting a new view. */
        void onAccountOpened(AccountOpened event) {
            readModel.upsert(new AccountView(event.aggregateId(), event.owner(),
                    event.initialBalance(), false));
        }

        /** Handles MoneyDeposited events by updating the balance. */
        void onMoneyDeposited(MoneyDeposited event) {
            readModel.findById(event.aggregateId()).ifPresent(existing ->
                    readModel.upsert(new AccountView(existing.id(), existing.owner(),
                            existing.balance() + event.amount(), existing.closed())));
        }

        /** Handles MoneyWithdrawn events by updating the balance. */
        void onMoneyWithdrawn(MoneyWithdrawn event) {
            readModel.findById(event.aggregateId()).ifPresent(existing ->
                    readModel.upsert(new AccountView(existing.id(), existing.owner(),
                            existing.balance() - event.amount(), existing.closed())));
        }

        /** Handles AccountClosed events by marking the account as closed. */
        void onAccountClosed(AccountClosed event) {
            readModel.findById(event.aggregateId()).ifPresent(existing ->
                    readModel.upsert(new AccountView(existing.id(), existing.owner(),
                            existing.balance(), true)));
        }
    }

    // --- Queries & handlers --------------------------------------------------

    /** Marker interface for all queries (read-side requests). */
    interface Query<R> {
    }

    /** Query: find a single account by id. */
    record FindAccountById(String accountId) implements Query<Optional<AccountView>> {
    }

    /** Query: list all open accounts. */
    record ListAllAccounts() implements Query<List<AccountView>> {
    }

    /** Query: count the number of open accounts. */
    record CountAccounts() implements Query<Integer> {
    }

    /** Query: find all open accounts by owner name. */
    record FindAccountsByOwner(String owner) implements Query<List<AccountView>> {
    }

    /** Handler contract: each handler processes one query type and produces a result. */
    interface QueryHandler<Q extends Query<R>, R> {
        Class<Q> queryType();

        R handle(Q query);
    }

    /** Handler: finds a single account by id. */
    static final class FindAccountByIdHandler implements QueryHandler<FindAccountById, Optional<AccountView>> {
        private final AccountReadModel readModel;

        FindAccountByIdHandler(AccountReadModel readModel) {
            this.readModel = readModel;
        }

        @Override
        public Class<FindAccountById> queryType() {
            return FindAccountById.class;
        }

        @Override
        public Optional<AccountView> handle(FindAccountById query) {
            return readModel.findById(query.accountId());
        }
    }

    /** Handler: lists all open accounts. */
    static final class ListAllAccountsHandler implements QueryHandler<ListAllAccounts, List<AccountView>> {
        private final AccountReadModel readModel;

        ListAllAccountsHandler(AccountReadModel readModel) {
            this.readModel = readModel;
        }

        @Override
        public Class<ListAllAccounts> queryType() {
            return ListAllAccounts.class;
        }

        @Override
        public List<AccountView> handle(ListAllAccounts query) {
            return readModel.findAllOpen();
        }
    }

    /** Handler: counts the number of open accounts. */
    static final class CountAccountsHandler implements QueryHandler<CountAccounts, Integer> {
        private final AccountReadModel readModel;

        CountAccountsHandler(AccountReadModel readModel) {
            this.readModel = readModel;
        }

        @Override
        public Class<CountAccounts> queryType() {
            return CountAccounts.class;
        }

        @Override
        public Integer handle(CountAccounts query) {
            return readModel.count();
        }
    }

    /** Handler: finds all open accounts by owner name. */
    static final class FindAccountsByOwnerHandler
            implements QueryHandler<FindAccountsByOwner, List<AccountView>> {
        private final AccountReadModel readModel;

        FindAccountsByOwnerHandler(AccountReadModel readModel) {
            this.readModel = readModel;
        }

        @Override
        public Class<FindAccountsByOwner> queryType() {
            return FindAccountsByOwner.class;
        }

        @Override
        public List<AccountView> handle(FindAccountsByOwner query) {
            return readModel.findByOwner(query.owner());
        }
    }

    /**
     * Thread-safe query bus that routes queries to their registered handlers.
     * Uses {@link ConcurrentHashMap} for handler lookup.
     */
    static final class QueryBus {
        private final Map<Class<?>, QueryHandler<?, ?>> handlers = new ConcurrentHashMap<>();

        /** Registers a handler for a specific query type. */
        <Q extends Query<R>, R> void register(QueryHandler<Q, R> handler) {
            handlers.put(handler.queryType(), handler);
        }

        /** Dispatches a query to its registered handler and returns the result. */
        @SuppressWarnings("unchecked")
        <R> R dispatch(Query<R> query) {
            QueryHandler<Query<R>, R> handler =
                    (QueryHandler<Query<R>, R>) handlers.get(query.getClass());
            if (handler == null) {
                throw new HandlerNotFoundException(
                        "No handler for query: " + query.getClass().getSimpleName());
            }
            return handler.handle(query);
        }

        /** Returns the number of registered query handlers. */
        int handlerCount() {
            return handlers.size();
        }
    }
}