package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountReadModel;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountView;

import java.util.Optional;

/**
 * Handler for the {@link FindAccountById} query.
 *
 * <p>Reads directly from the {@link AccountReadModel} — the denormalized,
 * query-optimized projection maintained by {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection.AccountProjection
 * AccountProjection}.
 */
public final class FindAccountByIdHandler
        implements QueryHandler<FindAccountById, Optional<AccountView>> {

    private final AccountReadModel readModel;

    public FindAccountByIdHandler(AccountReadModel readModel) {
        this.readModel = readModel;
    }

    @Override
    public Optional<AccountView> handle(FindAccountById query) {
        return readModel.findById(query.accountId());
    }
}
