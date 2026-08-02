package com.javastarterkit.patterns.hexagonalarchitecture;

import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.EmailNotificationAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.InMemoryAccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.JdbcAccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.SmsNotificationAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driving.ConsoleAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driving.RestAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.application.AccountService;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.AccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.NotificationPort;

/**
 * Main class demonstrating Hexagonal Architecture (Ports & Adapters) pattern.
 *
 * <p>This class shows how the core business logic is isolated from external concerns,
 * and how different adapters can be swapped without changing the core logic.
 */
public class HexagonalArchitectureApp {

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

    /**
     * Main method to run the demonstration.
     *
     * @param args command line arguments (not used)
     */
    public static void main(String[] args) {
        demonstrate();
    }
}