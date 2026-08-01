package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountRepository;

/**
 * Handler for the {@link DepositMoney} command.
 *
 * <p>Delegates to the repository's {@code executeAtomically} method, which
 * acquires a per-aggregate lock, loads the current aggregate state from the
 * event store, applies the deposit, and persists the resulting event — all
 * under the lock to guarantee serial consistency per aggregate.
 */
public final class DepositMoneyHandler implements CommandHandler<DepositMoney> {

    private final AccountRepository repository;

    public DepositMoneyHandler(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(DepositMoney command) {
        repository.executeAtomically(
                command.accountId(),
                aggregate -> {
                    aggregate.deposit(command.amount());
                    return null;
                });
    }
}
