package com.javastarterkit.patterns.hexagonalarchitecture;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

/**
 * Hexagonal Architecture (Ports & Adapters) Pattern Example
 *
 * <p>Hexagonal Architecture, also known as <b>Ports & Adapters</b>, isolates
 * the core business logic (the <b>domain</b>) from external concerns such as
 * databases, UIs, and messaging systems. The domain sits at the center and
 * communicates with the outside world only through well-defined
 * <b>ports</b> (interfaces). Each port can have multiple <b>adapters</b>
 * (implementations) that plug into it — for example, a REST controller or a
 * console command as a <i>driving</i> adapter, and an in-memory or JDBC
 * repository as a <i>driven</i> adapter.
 *
 * <p>This self-contained example models a simple bank account system:
 * <ul>
 *   <li><b>Domain (core)</b> — {@link Account}, {@link Money}, {@link Transaction}
 *       (pure business logic with no framework dependencies)</li>
 *   <li><b>Application (use cases)</b> — {@link AccountService} orchestrates
 *       use cases; {@link AccountRepository} and {@link NotificationPort} are
 *       the <i>ports</i> (interfaces) the application depends on</li>
 *   <li><b>Driving Adapters</b> — {@link ConsoleAdapter} (CLI) and
 *       {@link RestAdapter} (simulated HTTP) both drive the application through
 *       the same use-case service</li>
 *   <li><b>Driven Adapters</b> — {@link InMemoryAccountRepository} and
 *       {@link JdbcAccountRepository} implement the repository port;
 *       {@link EmailNotificationAdapter} and {@link SmsNotificationAdapter}
 *       implement the notification port</li>
 * </ul>
 *
 * <p>The key insight: the domain and application layers know nothing about
 * databases, HTTP, or the console. Swapping an adapter (e.g. replacing the
 * in-memory repository with a JDBC one) requires <b>zero</b> changes to the
 * core business logic.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class HexagonalArchitecture {

    /**
     * Demonstrates hexagonal architecture: create accounts, deposit/withdraw
     * money through different driving adapters, and show that the same core
     * logic works with different driven adapters (in-memory vs JDBC-style).
     */
    public static void demonstrate() {
        System.out.println("\n=== Hexagonal Architecture (Ports & Adapters) ===");
        System.out.println("Isolate core business logic from external concerns\n");

        // --- Build the application with in-memory adapters ---------------------
        AccountRepository repository = new InMemoryAccountRepository();
        NotificationPort notifier = new EmailNotificationAdapter();
        AccountService service = new AccountService(repository, notifier);

        // --- Driving adapter 1: Console (CLI) ----------------------------------
        ConsoleAdapter console = new ConsoleAdapter(service);
        System.out.println("--- Driving adapter: Console (CLI) ---");
        String aliceId = console.openAccount("Alice", "100.00");
        console.deposit(aliceId, "50.00");
        console.withdraw(aliceId, "30.00");
        console.printBalance(aliceId);

        // --- Driving adapter 2: REST (simulated HTTP) --------------------------
        System.out.println("\n--- Driving adapter: REST (simulated HTTP) ---");
        RestAdapter rest = new RestAdapter(service);
        String bobId = rest.post("/accounts", "{\"owner\":\"Bob\",\"initialBalance\":\"200.00\"}");
        rest.post("/accounts/" + bobId + "/deposits", "{\"amount\":\"100.00\"}");
        rest.get("/accounts/" + bobId);

        // --- Swap the driven adapter: JDBC-style repository --------------------
        System.out.println("\n--- Swapping driven adapter: InMemory -> Jdbc ---");
        AccountRepository jdbcRepo = new JdbcAccountRepository();
        AccountService jdbcService = new AccountService(jdbcRepo, new SmsNotificationAdapter());
        ConsoleAdapter jdbcConsole = new ConsoleAdapter(jdbcService);
        String carolId = jdbcConsole.openAccount("Carol", "500.00");
        jdbcConsole.withdraw(carolId, "120.00");
        jdbcConsole.printBalance(carolId);

        System.out.println("\nBenefits:");
        System.out.println("- Core domain is framework-agnostic (no DB/HTTP/UI dependencies)");
        System.out.println("- Ports define contracts; adapters are swappable");
        System.out.println("- Driving adapters (CLI, REST) share the same use cases");
        System.out.println("- Driven adapters (InMemory, Jdbc) are interchangeable");
        System.out.println("- Easy to test: mock ports instead of real infrastructure");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // DOMAIN (Core) — pure business logic, no framework dependencies
    // =========================================================================

    /** Value object representing a non-negative amount of money. */
    record Money(BigDecimal amount) {
        Money {
            if (amount == null || amount.signum() < 0) {
                throw new IllegalArgumentException("Amount must be non-negative");
            }
        }

        static Money of(String value) {
            return new Money(new BigDecimal(value));
        }

        Money add(Money other) {
            return new Money(amount.add(other.amount));
        }

        Money subtract(Money other) {
            return new Money(amount.subtract(other.amount));
        }

        boolean isGreaterThan(Money other) {
            return amount.compareTo(other.amount) > 0;
        }

        @Override
        public String toString() {
            return amount.toPlainString();
        }
    }

    /** Domain entity: a bank account with an owner and a balance. */
    static final class Account {
        private final String id;
        private final String owner;
        private Money balance;
        private final List<Transaction> transactions = new ArrayList<>();

        Account(String id, String owner, Money initialBalance) {
            this.id = id;
            this.owner = owner;
            this.balance = initialBalance;
        }

        String id() {
            return id;
        }

        String owner() {
            return owner;
        }

        Money balance() {
            return balance;
        }

        List<Transaction> transactions() {
            return List.copyOf(transactions);
        }

        /** Domain operation: deposit money. Enforces business rules. */
        void deposit(Money amount) {
            if (amount.amount().signum() <= 0) {
                throw new IllegalArgumentException("Deposit amount must be positive");
            }
            balance = balance.add(amount);
            transactions.add(new Transaction("DEPOSIT", amount, balance));
        }

        /** Domain operation: withdraw money. Enforces business rules. */
        void withdraw(Money amount) {
            if (amount.amount().signum() <= 0) {
                throw new IllegalArgumentException("Withdrawal amount must be positive");
            }
            if (amount.isGreaterThan(balance)) {
                throw new IllegalStateException(
                        "Insufficient funds: balance=" + balance + ", requested=" + amount);
            }
            balance = balance.subtract(amount);
            transactions.add(new Transaction("WITHDRAW", amount, balance));
        }

        @Override
        public String toString() {
            return "Account{id=" + id + ", owner=" + owner + ", balance=" + balance + "}";
        }
    }

    /** Value object: an immutable record of a single transaction. */
    record Transaction(String type, Money amount, Money balanceAfter) {
    }

    // =========================================================================
    // APPLICATION (Use Cases) — orchestrates domain, depends only on ports
    // =========================================================================

    /**
     * Outbound port (driven): persistence contract. The application depends on
     * this interface, never on a concrete database implementation.
     */
    interface AccountRepository {
        void save(Account account);

        Optional<Account> findById(String id);
    }

    /**
     * Outbound port (driven): notification contract. The application sends
     * notifications through this port without knowing the delivery mechanism.
     */
    interface NotificationPort {
        void notify(String recipient, String message);
    }

    /**
     * Application service: implements use cases by coordinating the domain and
     * the ports. It knows nothing about HTTP, the console, or databases.
     */
    static final class AccountService {
        private final AccountRepository repository;
        private final NotificationPort notifier;

        AccountService(AccountRepository repository, NotificationPort notifier) {
            this.repository = repository;
            this.notifier = notifier;
        }

        /** Use case: open a new account. */
        Account openAccount(String owner, String initialBalance) {
            Account account = new Account(UUID.randomUUID().toString(), owner, Money.of(initialBalance));
            repository.save(account);
            notifier.notify(owner, "Welcome! Account " + account.id() + " opened with " + account.balance());
            return account;
        }

        /** Use case: deposit money into an account. */
        Account deposit(String accountId, String amount) {
            Account account = getAccount(accountId);
            account.deposit(Money.of(amount));
            repository.save(account);
            notifier.notify(account.owner(), "Deposited " + amount + ". New balance: " + account.balance());
            return account;
        }

        /** Use case: withdraw money from an account. */
        Account withdraw(String accountId, String amount) {
            Account account = getAccount(accountId);
            account.withdraw(Money.of(amount));
            repository.save(account);
            notifier.notify(account.owner(), "Withdrew " + amount + ". New balance: " + account.balance());
            return account;
        }

        /** Use case: query account balance. */
        Account getAccount(String accountId) {
            return repository.findById(accountId)
                    .orElseThrow(() -> new IllegalArgumentException("Account not found: " + accountId));
        }
    }

    // =========================================================================
    // DRIVING ADAPTERS — inbound side: how the outside world calls the app
    // =========================================================================

    /**
     * Driving adapter: a simple command-line interface. It translates user
     * input into calls on the {@link AccountService} (the application port).
     */
    static final class ConsoleAdapter {
        private final AccountService service;

        ConsoleAdapter(AccountService service) {
            this.service = service;
        }

        String openAccount(String owner, String initialBalance) {
            Account account = service.openAccount(owner, initialBalance);
            System.out.println("  [CLI] Opened account " + account.id() + " for " + owner
                    + " with balance " + account.balance());
            return account.id();
        }

        void deposit(String accountId, String amount) {
            Account account = service.deposit(accountId, amount);
            System.out.println("  [CLI] Deposited " + amount + " -> " + account);
        }

        void withdraw(String accountId, String amount) {
            Account account = service.withdraw(accountId, amount);
            System.out.println("  [CLI] Withdrew " + amount + " -> " + account);
        }

        void printBalance(String accountId) {
            Account account = service.getAccount(accountId);
            System.out.println("  [CLI] Balance for " + account.owner() + ": " + account.balance());
        }
    }

    /**
     * Driving adapter: a simulated REST controller. In a real system this would
     * be a Spring MVC / JAX-RS controller; here we simulate HTTP verbs with
     * simple string payloads to keep the example self-contained.
     */
    static final class RestAdapter {
        private final AccountService service;

        RestAdapter(AccountService service) {
            this.service = service;
        }

        /** Simulated POST /accounts with a JSON-ish body. */
        String post(String path, String body) {
            if (path.equals("/accounts")) {
                String owner = extract(body, "owner");
                String initialBalance = extract(body, "initialBalance");
                Account account = service.openAccount(owner, initialBalance);
                System.out.println("  [REST] POST " + path + " -> 201 Created: " + account.id());
                return account.id();
            }
            if (path.endsWith("/deposits")) {
                String accountId = path.split("/")[2];
                String amount = extract(body, "amount");
                Account account = service.deposit(accountId, amount);
                System.out.println("  [REST] POST " + path + " -> 200 OK: " + account);
                return account.id();
            }
            throw new IllegalArgumentException("Unsupported POST path: " + path);
        }

        /** Simulated GET /accounts/{id}. */
        void get(String path) {
            String accountId = path.split("/")[2];
            Account account = service.getAccount(accountId);
            System.out.println("  [REST] GET " + path + " -> 200 OK: " + account);
        }

        /** Minimal JSON-ish field extractor: {"key":"value"}. */
        private static String extract(String body, String key) {
            String marker = "\"" + key + "\":\"";
            int start = body.indexOf(marker) + marker.length();
            int end = body.indexOf('"', start);
            return body.substring(start, end);
        }
    }

    // =========================================================================
    // DRIVEN ADAPTERS — outbound side: implementations of the ports
    // =========================================================================

    /**
     * Driven adapter: in-memory implementation of {@link AccountRepository}.
     * Used for tests, demos, and prototypes.
     */
    static final class InMemoryAccountRepository implements AccountRepository {
        private final Map<String, Account> store = new LinkedHashMap<>();

        @Override
        public void save(Account account) {
            store.put(account.id(), account);
        }

        @Override
        public Optional<Account> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }
    }

    /**
     * Driven adapter: simulated JDBC implementation of {@link AccountRepository}.
     * In a real system this would use JDBC/JPA; here we simulate the behavior
     * to show that swapping adapters requires no core changes.
     */
    static final class JdbcAccountRepository implements AccountRepository {
        private final Map<String, Account> store = new LinkedHashMap<>();

        @Override
        public void save(Account account) {
            // Simulate an INSERT ... ON CONFLICT UPDATE
            store.put(account.id(), account);
            System.out.println("  [JDBC] Persisted account " + account.id()
                    + " (balance=" + account.balance() + ")");
        }

        @Override
        public Optional<Account> findById(String id) {
            return Optional.ofNullable(store.get(id));
        }
    }

    /** Driven adapter: email-based notification. */
    static final class EmailNotificationAdapter implements NotificationPort {
        @Override
        public void notify(String recipient, String message) {
            System.out.println("  [EMAIL] To: " + recipient + " | " + message);
        }
    }

    /** Driven adapter: SMS-based notification. */
    static final class SmsNotificationAdapter implements NotificationPort {
        @Override
        public void notify(String recipient, String message) {
            System.out.println("  [SMS]   To: " + recipient + " | " + message);
        }
    }
}