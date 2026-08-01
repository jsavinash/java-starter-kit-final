package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountView;

import java.util.List;

/**
 * Query to retrieve all accounts as a list of read-side views.
 *
 * <p>Returns an empty list when no accounts exist (never {@code null}).
 */
public record ListAllAccounts()
        implements Query<List<AccountView>> {
}
