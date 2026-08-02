package com.javastarterkit.patterns.eventsourcing.infrastructure;

/**
 * Serializable account state captured in a snapshot.
 *
 * <p>This is a plain immutable data holder used to restore aggregate state
 * without replaying the entire event stream.
 *
 * @param id      the account ID
 * @param owner   the account owner
 * @param balance the current balance
 * @param closed  whether the account is closed
 */
public record AccountState(String id, String owner, int balance, boolean closed) {
}