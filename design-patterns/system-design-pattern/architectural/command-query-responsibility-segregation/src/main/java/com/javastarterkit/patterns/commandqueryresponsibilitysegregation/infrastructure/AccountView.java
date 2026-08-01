package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

/**
 * Immutable, denormalized read-model DTO representing a single account
 * from the query side.
 *
 * <h3>Design</h3>
 * <p>{@code AccountView} is a Java {@linkplain java.lang.invoke.Record}
 * that exposes only the data a query client needs — a flattened,
 * projection-optimized representation. It is intentionally decoupled from
 * the write-side {@link com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.model.AccountAggregate
 * AccountAggregate}, whose internal invariants (event sourcing, uncommitted
 * event buffer) are irrelevant to read operations.</p>
 *
 * <p>All query result handlers return instances of this record, guaranteeing
 * that clients receive an immutable snapshot that cannot be mutated after
 * construction.</p>
 *
 * @param accountId the unique account identifier
 * @param owner     the account owner's name
 * @param balance   the current balance (smallest currency unit)
 * @param closed    whether the account has been closed
 */
public record AccountView(String accountId, String owner, int balance, boolean closed) {
}
