package com.javastarterkit.patterns.commandqueryresponsibilitysegregation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * Command Query Responsibility Segregation (CQRS) Pattern Example
 *
 * <p>Separates the model used to update state (write side / command side) from the
 * model used to read state (read side / query side). Commands mutate the write
 * model and emit domain events; a projection subscribes to those events and
 * builds an optimized read model that queries consume.
 *
 * <p>This self-contained example models a simple bank account aggregate:
 * <ul>
 *   <li><b>Command side</b> — {@link AccountAggregate}, {@link AccountRepository},
 *       {@link Command}, {@link CommandHandler}</li>
 *   <li><b>Query side</b> — {@link AccountView}, {@link AccountReadModel},
 *       {@link Query}, {@link QueryHandler}</li>
 *   <li><b>Synchronization</b> — {@link EventBus} publishes events from the
 *       command side to the {@link AccountProjection} which updates the read model</li>
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
        AccountWriteStore writeStore = new AccountWriteStore();
        AccountRepository repository = new AccountRepository(writeStore, eventBus);
        AccountReadModel readModel = new AccountReadModel();
        AccountProjection projection = new AccountProjection(readModel);

        // The projection subscribes to domain events so the read model stays in sync.
        eventBus.subscribe(AccountOpened.class, projection::onAccountOpened);
        eventBus.subscribe(MoneyDeposited.class, projection::onMoneyDeposited);
        eventBus.subscribe(MoneyWithdrawn.class, projection::onMoneyWithdrawn);

        // Command bus dispatches commands to their handlers.
        CommandBus commandBus = new CommandBus();
        commandBus.register(new OpenAccountHandler(repository));
        commandBus.register(new DepositMoneyHandler(repository));
        commandBus.register(new WithdrawMoneyHandler(repository));

        // Query bus dispatches queries to their handlers.
        QueryBus queryBus = new QueryBus();
        queryBus.register(new FindAccountByIdHandler(readModel));
        queryBus.register(new ListAllAccountsHandler(readModel));
        queryBus.register(new CountAccountsHandler(readModel));

        // --- Command side: mutate the write model --------------------------------
        System.out.println("--- COMMAND SIDE (writes) ---");
        String aliceId = commandBus.dispatch(new OpenAccount("Alice", 100));
        String bobId = commandBus.dispatch(new OpenAccount("Bob", 50));
        commandBus.dispatch(new DepositMoney(aliceId, 200));
        commandBus.dispatch(new WithdrawMoney(bobId, 20));
        commandBus.dispatch(new DepositMoney(bobId, 70));

        // --- Query side: read from the optimized read model ----------------------
        System.out.println("\n--- QUERY SIDE (reads) ---");
        System.out.println("Find Alice by id: " + queryBus.dispatch(new FindAccountById(aliceId)));
        System.out.println("Find Bob by id:   " + queryBus.dispatch(new FindAccountById(bobId)));
        System.out.println("All accounts:     " + queryBus.dispatch(new ListAllAccounts()));
        System.out.println("Account count:    " + queryBus.dispatch(new CountAccounts()));

        System.out.println("\nBenefits:");
        System.out.println("- Write model is optimized for business rules & validation");
        System.out.println("- Read model is optimized for queries (denormalized, fast lookups)");
        System.out.println("- Read and write sides can scale independently");
        System.out.println("- Read model can be rebuilt from the event stream at any time");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // Domain Events — emitted by the command side, consumed by projections
    // =========================================================================

    /** Base type for all domain events. */
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

    // =========================================================================
    // Event Bus — in-process pub/sub connecting command side to read side
    // =========================================================================

    /**
     * A minimal in-memory event bus. Projections register as subscribers for
     * specific event types; the command side publishes events after each mutation.
     */
    static final class EventBus {
        private final Map<Class<?>, List<Consumer<?>>> subscribers = new HashMap<>();

        <E> void subscribe(Class<E> eventType, Consumer<E> subscriber) {
            subscribers.computeIfAbsent(eventType, k -> new ArrayList<>()).add(subscriber);
        }

        @SuppressWarnings("unchecked")
        void publish(DomainEvent event) {
            List<Consumer<?>> subs = subscribers.get(event.getClass());
            if (subs != null) {
                for (Consumer<?> sub : subs) {
                    ((Consumer<DomainEvent>) sub).accept(event);
                }
            }
        }
    }

    // =========================================================================
    // Command Side — aggregate, repository, commands, handlers, command bus
    // =========================================================================

    /**
     * The write-side aggregate. Enforces business invariants (e.g. no negative
     * balances) and emits domain events on every state change.
     */
    static final class AccountAggregate {
        private final String id;
        private final String owner;
        private int balance;
        private final List<DomainEvent> uncommittedEvents = new ArrayList<>();

        AccountAggregate(String id, String owner) {
            this.id = id;
            this.owner = owner;
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

        /** Factory: opens a new account with an initial balance. */
        static AccountAggregate open(String id, String owner, int initialBalance) {
            if (initialBalance < 0) {
                throw new IllegalArgumentException("Initial balance cannot be negative");
            }
            AccountAggregate account = new AccountAggregate(id, owner);
            account.apply(new AccountOpened(id, owner, initialBalance));
            return account;
        }

        void deposit(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            apply(new MoneyDeposited(id, amount));
        }

        void withdraw(int amount) {
            if (amount <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
            if (balance < amount) {
                throw new IllegalStateException("Insufficient funds: balance=" + balance + ", requested=" + amount);
            }
            apply(new MoneyWithdrawn(id, amount));
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

        @SuppressWarnings("unused")
        private void handle(DomainEvent event) {
            switch (event) {
                case AccountOpened e -> this.balance = e.initialBalance();
                case MoneyDeposited e -> this.balance += e.amount();
                case MoneyWithdrawn e -> this.balance -= e.amount();
                default -> { }
            }
        }

        List<DomainEvent> pullUncommittedEvents() {
            List<DomainEvent> copy = new ArrayList<>(uncommittedEvents);
            uncommittedEvents.clear();
            return copy;
        }
    }

    /** Persistent store for the write side (simulated with an in-memory map). */
    static final class AccountWriteStore {
        private final Map<String, List<DomainEvent>> streams = new HashMap<>();

        void save(String id, List<DomainEvent> newEvents) {
            streams.computeIfAbsent(id, k -> new ArrayList<>()).addAll(newEvents);
        }

        List<DomainEvent> load(String id) {
            return streams.getOrDefault(id, List.of());
        }

        boolean exists(String id) {
            return streams.containsKey(id);
        }
    }

    /**
     * Repository for the write side. Loads aggregates by replaying their event
     * stream, and saves new events (publishing them to the event bus so the read
     * model stays synchronized).
     */
    static final class AccountRepository {
        private final AccountWriteStore store;
        private final EventBus eventBus;

        AccountRepository(AccountWriteStore store, EventBus eventBus) {
            this.store = store;
            this.eventBus = eventBus;
        }

        String create(String owner, int initialBalance) {
            String id = UUID.randomUUID().toString();
            AccountAggregate account = AccountAggregate.open(id, owner, initialBalance);
            persist(account);
            return id;
        }

        AccountAggregate find(String id) {
            if (!store.exists(id)) {
                throw new IllegalStateException("Account not found: " + id);
            }
            List<DomainEvent> history = store.load(id);
            // The owner is parsed from the first AccountOpened event.
            String owner = history.stream()
                    .filter(AccountOpened.class::isInstance)
                    .map(AccountOpened.class::cast)
                    .findFirst()
                    .map(AccountOpened::owner)
                    .orElse("unknown");
            AccountAggregate account = new AccountAggregate(id, owner);
            history.forEach(account::replay);
            return account;
        }

        void persist(AccountAggregate account) {
            List<DomainEvent> newEvents = account.pullUncommittedEvents();
            store.save(account.id(), newEvents);
            newEvents.forEach(eventBus::publish);
        }
    }

    // --- Commands & handlers -------------------------------------------------

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

    /** Marker for handlers that produce a result. */
    interface CommandHandler<C extends Command, R> {
        Class<C> commandType();

        R handle(C command);
    }

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

    /** Routes commands to their registered handlers. */
    static final class CommandBus {
        private final Map<Class<?>, CommandHandler<?, ?>> handlers = new HashMap<>();

        <C extends Command, R> void register(CommandHandler<C, R> handler) {
            handlers.put(handler.commandType(), handler);
        }

        @SuppressWarnings("unchecked")
        <R> R dispatch(Command command) {
            CommandHandler<Command, R> handler =
                    (CommandHandler<Command, R>) handlers.get(command.getClass());
            if (handler == null) {
                throw new IllegalStateException("No handler for command: " + command.getClass().getSimpleName());
            }
            return handler.handle(command);
        }
    }

    // =========================================================================
    // Query Side — read model, projection, queries, handlers, query bus
    // =========================================================================

    /** Denormalized read model DTO optimized for fast queries. */
    record AccountView(String id, String owner, int balance) {
        @Override
        public String toString() {
            return "AccountView{id=" + id.substring(0, 8) + ", owner=" + owner + ", balance=" + balance + "}";
        }
    }

    /**
     * The read model store. In a real system this might be a NoSQL document
     * store, a search index, or a materialized view. Here it is an in-memory map
     * keyed by account id for O(1) lookups.
     */
    static final class AccountReadModel {
        private final Map<String, AccountView> views = new HashMap<>();

        void upsert(AccountView view) {
            views.put(view.id(), view);
        }

        Optional<AccountView> findById(String id) {
            return Optional.ofNullable(views.get(id));
        }

        List<AccountView> findAll() {
            return List.copyOf(views.values());
        }

        int count() {
            return views.size();
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

        void onAccountOpened(AccountOpened event) {
            readModel.upsert(new AccountView(event.aggregateId(), event.owner(), event.initialBalance()));
        }

        void onMoneyDeposited(MoneyDeposited event) {
            readModel.findById(event.aggregateId()).ifPresent(existing ->
                    readModel.upsert(new AccountView(existing.id(), existing.owner(), existing.balance() + event.amount())));
        }

        void onMoneyWithdrawn(MoneyWithdrawn event) {
            readModel.findById(event.aggregateId()).ifPresent(existing ->
                    readModel.upsert(new AccountView(existing.id(), existing.owner(), existing.balance() - event.amount())));
        }
    }

    // --- Queries & handlers --------------------------------------------------

    interface Query<R> {
    }

    /** Query: find a single account by id. */
    record FindAccountById(String accountId) implements Query<Optional<AccountView>> {
    }

    /** Query: list all accounts. */
    record ListAllAccounts() implements Query<List<AccountView>> {
    }

    /** Query: count the number of accounts. */
    record CountAccounts() implements Query<Integer> {
    }

    interface QueryHandler<Q extends Query<R>, R> {
        Class<Q> queryType();

        R handle(Q query);
    }

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
            return readModel.findAll();
        }
    }

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

    /** Routes queries to their registered handlers. */
    static final class QueryBus {
        private final Map<Class<?>, QueryHandler<?, ?>> handlers = new HashMap<>();

        <Q extends Query<R>, R> void register(QueryHandler<Q, R> handler) {
            handlers.put(handler.queryType(), handler);
        }

        @SuppressWarnings("unchecked")
        <R> R dispatch(Query<R> query) {
            QueryHandler<Query<R>, R> handler =
                    (QueryHandler<Query<R>, R>) handlers.get(query.getClass());
            if (handler == null) {
                throw new IllegalStateException("No handler for query: " + query.getClass().getSimpleName());
            }
            return handler.handle(query);
        }
    }
}