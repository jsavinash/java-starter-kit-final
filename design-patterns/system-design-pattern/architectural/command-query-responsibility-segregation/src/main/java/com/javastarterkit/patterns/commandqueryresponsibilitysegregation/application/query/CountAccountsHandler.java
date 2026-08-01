package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountReadModel;

/**
 * Handler for the {@link CountAccounts} query.
 *
 * <p>Returns the total number of accounts currently held in the read model.
 */
public final class CountAccountsHandler
        implements QueryHandler<CountAccounts, Integer> {

    private final AccountReadModel readModel;

    public CountAccountsHandler(AccountReadModel readModel) {
        this.readModel = readModel;
    }

    @Override
    public Integer handle(CountAccounts query) {
        return readModel.count();
    }
}
