package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountView;

import java.util.Optional;

/**
 * Query to retrieve a single account's read-side view by its ID.
 *
 * @param accountId the account identifier to look up
 */
public record FindAccountById(String accountId)
        implements Query<Optional<AccountView>> {
}
