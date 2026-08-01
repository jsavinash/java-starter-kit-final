package com.javastarterkit.patterns.commandqueryresponsibilitysegregation;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.AccountReadModel;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.AccountProjection;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.AccountRepository;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.AccountWriteStore;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.CommandBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.CountAccounts;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.DepositMoney;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.EventBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.FindAccountById;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.ListAllAccounts;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.OpenAccount;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.QueryBus;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.CommandQueryResponsibilitySegregation.WithdrawMoney;
import java.util.Optional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the CQRS flow: commands mutate the write model, events
 * synchronize the read model, and queries return the projected state.
 */
class CommandQueryResponsibilitySegregationTest {

    private CommandBus commandBus;
    private QueryBus queryBus;

    @BeforeEach
    void setUp() {
        EventBus eventBus = new EventBus();
        AccountWriteStore writeStore = new AccountWriteStore();
        AccountRepository repository = new AccountRepository(writeStore, eventBus);
        AccountReadModel readModel = new AccountReadModel();
        AccountProjection projection = new AccountProjection(readModel);

        eventBus.subscribe(CommandQueryResponsibilitySegregation.AccountOpened.class, projection::onAccountOpened);
        eventBus.subscribe(CommandQueryResponsibilitySegregation.MoneyDeposited.class, projection::onMoneyDeposited);
        eventBus.subscribe(CommandQueryResponsibilitySegregation.MoneyWithdrawn.class, projection::onMoneyWithdrawn);

        commandBus = new CommandBus();
        commandBus.register(new CommandQueryResponsibilitySegregation.OpenAccountHandler(repository));
        commandBus.register(new CommandQueryResponsibilitySegregation.DepositMoneyHandler(repository));
        commandBus.register(new CommandQueryResponsibilitySegregation.WithdrawMoneyHandler(repository));

        queryBus = new QueryBus();
        queryBus.register(new CommandQueryResponsibilitySegregation.FindAccountByIdHandler(readModel));
        queryBus.register(new CommandQueryResponsibilitySegregation.ListAllAccountsHandler(readModel));
        queryBus.register(new CommandQueryResponsibilitySegregation.CountAccountsHandler(readModel));
    }

    @Test
    @DisplayName("open account command projects a read model entry")
    void openAccountProjectsToReadModel() {
        String id = commandBus.dispatch(new OpenAccount("Alice", 100));

        Optional<CommandQueryResponsibilitySegregation.AccountView> view =
                queryBus.dispatch(new FindAccountById(id));

        assertTrue(view.isPresent());
        assertEquals("Alice", view.get().owner());
        assertEquals(100, view.get().balance());
    }

    @Test
    @DisplayName("deposit and withdraw commands keep the read model in sync")
    void depositAndWithdrawUpdateReadModel() {
        String id = commandBus.dispatch(new OpenAccount("Bob", 50));
        commandBus.dispatch(new DepositMoney(id, 200));
        commandBus.dispatch(new WithdrawMoney(id, 70));

        CommandQueryResponsibilitySegregation.AccountView view =
                queryBus.dispatch(new FindAccountById(id)).orElseThrow();

        assertEquals(180, view.balance());
    }

    @Test
    @DisplayName("count and list queries reflect all projected accounts")
    void countAndListQueriesReflectAllAccounts() {
        commandBus.dispatch(new OpenAccount("Alice", 100));
        commandBus.dispatch(new OpenAccount("Bob", 50));

        assertEquals(2, queryBus.dispatch(new CountAccounts()));
        assertEquals(2, queryBus.dispatch(new ListAllAccounts()).size());
    }

    @Test
    @DisplayName("withdrawal exceeding balance is rejected on the write side")
    void withdrawalExceedingBalanceIsRejected() {
        String id = commandBus.dispatch(new OpenAccount("Alice", 100));

        assertThrows(IllegalStateException.class, () ->
                commandBus.dispatch(new WithdrawMoney(id, 200)));
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        CommandQueryResponsibilitySegregation.demonstrate();
    }
}