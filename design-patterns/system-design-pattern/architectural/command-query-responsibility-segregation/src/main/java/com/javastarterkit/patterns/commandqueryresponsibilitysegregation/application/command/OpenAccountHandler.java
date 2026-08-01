package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.model.AccountAggregate;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountRepository;

/**
 * Handler for the {@link OpenAccount} command.
 *
 * <p>Creates a new {@link AccountAggregate} with the provided identity, owner,
 * and initial balance, then persists its initial events through the repository.
 * Since the account does not yet exist, there is no existing event stream to
 * load — the repository's {@code save} method handles the append-and-publish
 * flow atomically for new aggregates.
 */
public final class OpenAccountHandler implements CommandHandler<OpenAccount> {

    private final AccountRepository repository;

    /**
     * @param repository the repository through which the new aggregate's events
     *                   are persisted and published
     */
    public OpenAccountHandler(AccountRepository repository) {
        this.repository = repository;
    }

    @Override
    public void handle(OpenAccount command) {
        var aggregate = AccountAggregate.open(
                command.accountId(), command.owner(), command.initialBalance());
        repository.save(aggregate);
    }
}
