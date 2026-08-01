package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.query;

/**
 * Query to count the total number of accounts in the read model.
 *
 * @param <R> bound to {@link Integer}, making the result type explicit
 */
public record CountAccounts()
        implements Query<Integer> {
}
