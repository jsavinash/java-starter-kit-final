package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountReadModel;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountView;

import java.util.List;

/**
 * Handler for the {@link ListAllAccounts} query.
 *
 * <p>Returns a snapshot list of all accounts from the read model. The
 * returned list is a defensive copy — callers cannot mutate the internal
 * store.
 */
public final class ListAllAccountsHandler
        implements QueryHandler<ListAllAccounts, List<AccountView>> {

    private final AccountReadModel readModel;

    public ListAllAccountsHandler(AccountReadModel readModel) {
        this.readModel = readModel;
    }

    @Override
    public List<AccountView> handle(ListAllAccounts query) {
        return readModel.findAll();
    }
}
