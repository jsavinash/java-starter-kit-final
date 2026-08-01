package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountRepository;

/**
 * Handler for the {@link WithdrawMoney} command.
 *
 * <p>Delegates to the repository's {@code executeAtomically} method, which
 * acquires a per-aggregate lock, loads the current aggregate state from the
 * event store, applies the withdrawal (subject to the aggregate's business
 * invariants), and persists the resulting event — all under the lock.
 */
public final class WithdrawMoneyHandler implements CommandHandler<WithdrawMoney> {

    private final AccountRepository repository;

    public WithdrawMoneyHandler(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(WithdrawMoney command) {
        repository.executeAtomically(
                command.accountId(),
                aggregate -> {
                    aggregate.withdraw(command.amount());
                    return null;
                });
    }
}
