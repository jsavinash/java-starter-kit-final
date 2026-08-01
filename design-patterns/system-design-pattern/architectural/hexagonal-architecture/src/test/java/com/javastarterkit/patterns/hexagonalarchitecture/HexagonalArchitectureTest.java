package com.javastarterkit.patterns.hexagonalarchitecture;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.Account;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.AccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.AccountService;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.ConsoleAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.EmailNotificationAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.InMemoryAccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.JdbcAccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.Money;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.NotificationPort;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.RestAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.HexagonalArchitecture.SmsNotificationAdapter;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the hexagonal architecture pattern: the domain enforces
 * business rules, the application service orchestrates use cases through ports,
 * and driving/driven adapters are swappable without changing core logic.
 */
class HexagonalArchitectureTest {

    @Test
    @DisplayName("domain Account enforces deposit and withdrawal business rules")
    void accountEnforcesBusinessRules() {
        Account account = new Account("acc-1", "Alice", Money.of("100.00"));

        account.deposit(Money.of("50.00"));
        assertEquals("150.00", account.balance().toString());

        account.withdraw(Money.of("30.00"));
        assertEquals("120.00", account.balance().toString());

        assertThrows(IllegalArgumentException.class, () -> account.deposit(Money.of("0")));
        assertThrows(IllegalStateException.class, () -> account.withdraw(Money.of("500.00")));
    }

    @Test
    @DisplayName("Money value object rejects negative amounts")
    void moneyRejectsNegativeAmounts() {
        assertThrows(IllegalArgumentException.class, () -> Money.of("-1.00"));
    }

    @Test
    @DisplayName("AccountService opens, deposits, and withdraws through the repository port")
    void serviceOrchestratesUseCases() {
        AccountRepository repository = new InMemoryAccountRepository();
        NotificationPort notifier = new EmailNotificationAdapter();
        AccountService service = new AccountService(repository, notifier);

        Account account = service.openAccount("Alice", "100.00");
        service.deposit(account.id(), "50.00");
        service.withdraw(account.id(), "30.00");

        Account loaded = service.getAccount(account.id());
        assertEquals("Alice", loaded.owner());
        assertEquals("120.00", loaded.balance().toString());
        assertEquals(2, loaded.transactions().size());
    }

    @Test
    @DisplayName("swapping the repository adapter (InMemory -> Jdbc) requires no core changes")
    void repositoryAdapterIsSwappable() {
        AccountRepository inMemory = new InMemoryAccountRepository();
        AccountRepository jdbc = new JdbcAccountRepository();

        AccountService inMemoryService = new AccountService(inMemory, new EmailNotificationAdapter());
        AccountService jdbcService = new AccountService(jdbc, new SmsNotificationAdapter());

        Account a = inMemoryService.openAccount("Alice", "100.00");
        Account b = jdbcService.openAccount("Bob", "200.00");

        inMemoryService.deposit(a.id(), "50.00");
        jdbcService.deposit(b.id(), "100.00");

        assertEquals("150.00", inMemoryService.getAccount(a.id()).balance().toString());
        assertEquals("300.00", jdbcService.getAccount(b.id()).balance().toString());
    }

    @Test
    @DisplayName("swapping the notification adapter (Email -> SMS) requires no core changes")
    void notificationAdapterIsSwappable() {
        List<String> emailMessages = new ArrayList<>();
        List<String> smsMessages = new ArrayList<>();

        NotificationPort email = (recipient, message) -> emailMessages.add(recipient + ": " + message);
        NotificationPort sms = (recipient, message) -> smsMessages.add(recipient + ": " + message);

        AccountService emailService = new AccountService(new InMemoryAccountRepository(), email);
        AccountService smsService = new AccountService(new InMemoryAccountRepository(), sms);

        emailService.openAccount("Alice", "100.00");
        smsService.openAccount("Bob", "200.00");

        assertEquals(1, emailMessages.size());
        assertEquals(1, smsMessages.size());
        assertTrue(emailMessages.get(0).startsWith("Alice:"));
        assertTrue(smsMessages.get(0).startsWith("Bob:"));
    }

    @Test
    @DisplayName("ConsoleAdapter drives the application through the service")
    void consoleAdapterDrivesApplication() {
        AccountService service = new AccountService(
                new InMemoryAccountRepository(), new EmailNotificationAdapter());
        ConsoleAdapter console = new ConsoleAdapter(service);

        String id = console.openAccount("Alice", "100.00");
        console.deposit(id, "50.00");
        console.withdraw(id, "30.00");

        assertEquals("120.00", service.getAccount(id).balance().toString());
    }

    @Test
    @DisplayName("RestAdapter drives the application through the service")
    void restAdapterDrivesApplication() {
        AccountService service = new AccountService(
                new InMemoryAccountRepository(), new EmailNotificationAdapter());
        RestAdapter rest = new RestAdapter(service);

        String id = rest.post("/accounts", "{\"owner\":\"Bob\",\"initialBalance\":\"200.00\"}");
        rest.post("/accounts/" + id + "/deposits", "{\"amount\":\"100.00\"}");

        assertEquals("300.00", service.getAccount(id).balance().toString());
    }

    @Test
    @DisplayName("unknown account id throws IllegalArgumentException")
    void unknownAccountThrows() {
        AccountService service = new AccountService(
                new InMemoryAccountRepository(), new EmailNotificationAdapter());

        assertThrows(IllegalArgumentException.class, () -> service.getAccount("missing"));
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        HexagonalArchitecture.demonstrate();
    }
}