package com.javastarterkit.patterns.hexagonalarchitecture;

import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.EmailNotificationAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.InMemoryAccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.JdbcAccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven.SmsNotificationAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driving.ConsoleAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.adapters.driving.RestAdapter;
import com.javastarterkit.patterns.hexagonalarchitecture.application.AccountService;
import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;
import com.javastarterkit.patterns.hexagonalarchitecture.domain.Money;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.AccountRepository;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.NotificationPort;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests verifying the hexagonal architecture pattern: the domain enforces
 * business rules, the application service orchestrates use cases through ports,
 * and driving/driven adapters are swappable without changing core logic.
 */
@DisplayName("Hexagonal Architecture Tests")
class HexagonalArchitectureAppTest {

    @Test
    @DisplayName("domain Account enforces deposit and withdrawal business rules")
    void accountEnforcesBusinessRules() {
        Account account = new Account("acc-1", "Alice", Money.of("100.00"));

        account.deposit(Money.of("50.00"));
        assertThat(account.balance()).isEqualTo(Money.of("150.00"));

        account.withdraw(Money.of("30.00"));
        assertThat(account.balance()).isEqualTo(Money.of("120.00"));

        assertThatThrownBy(() -> account.deposit(Money.of("0")))
            .isInstanceOf(IllegalArgumentException.class);

        assertThatThrownBy(() -> account.withdraw(Money.of("500.00")))
            .isInstanceOf(IllegalStateException.class);
    }

    @Test
    @DisplayName("Money value object rejects negative amounts")
    void moneyRejectsNegativeAmounts() {
        assertThatThrownBy(() -> Money.of("-1.00"))
            .isInstanceOf(IllegalArgumentException.class);
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
        assertThat(loaded.owner()).isEqualTo("Alice");
        assertThat(loaded.balance()).isEqualTo(Money.of("120.00"));
        assertThat(loaded.transactions()).hasSize(2);
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

        assertThat(inMemoryService.getAccount(a.id()).balance()).isEqualTo(Money.of("150.00"));
        assertThat(jdbcService.getAccount(b.id()).balance()).isEqualTo(Money.of("300.00"));
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

        assertThat(emailMessages).hasSize(1);
        assertThat(smsMessages).hasSize(1);
        assertThat(emailMessages.get(0)).startsWith("Alice:");
        assertThat(smsMessages.get(0)).startsWith("Bob:");
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

        assertThat(service.getAccount(id).balance()).isEqualTo(Money.of("120.00"));
    }

    @Test
    @DisplayName("RestAdapter drives the application through the service")
    void restAdapterDrivesApplication() {
        AccountService service = new AccountService(
                new InMemoryAccountRepository(), new EmailNotificationAdapter());
        RestAdapter rest = new RestAdapter(service);

        String id = rest.post("/accounts", "{\"owner\":\"Bob\",\"initialBalance\":\"200.00\"}");
        rest.post("/accounts/" + id + "/deposits", "{\"amount\":\"100.00\"}");

        assertThat(service.getAccount(id).balance()).isEqualTo(Money.of("300.00"));
    }

    @Test
    @DisplayName("unknown account id throws IllegalArgumentException")
    void unknownAccountThrows() {
        AccountService service = new AccountService(
                new InMemoryAccountRepository(), new EmailNotificationAdapter());

        assertThatThrownBy(() -> service.getAccount("missing"))
            .isInstanceOf(IllegalArgumentException.class)
            .hasMessageContaining("Account not found");
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        HexagonalArchitectureApp.demonstrate();
    }

    @Test
    @DisplayName("domain Account tracks transaction history")
    void accountTracksTransactionHistory() {
        Account account = new Account("acc-1", "Alice", Money.of("100.00"));

        account.deposit(Money.of("50.00"));
        account.withdraw(Money.of("30.00"));

        assertThat(account.transactions()).hasSize(2);
        assertThat(account.transactions().get(0).type()).isEqualTo("DEPOSIT");
        assertThat(account.transactions().get(1).type()).isEqualTo("WITHDRAW");
    }

    @Test
    @DisplayName("insufficient funds withdrawal throws IllegalStateException")
    void insufficientFundsWithdrawalThrows() {
        Account account = new Account("acc-1", "Alice", Money.of("100.00"));

        assertThatThrownBy(() -> account.withdraw(Money.of("150.00")))
            .isInstanceOf(IllegalStateException.class)
            .hasMessageContaining("Insufficient funds");
    }
}