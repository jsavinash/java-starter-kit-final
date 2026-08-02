package com.javastarterkit.patterns.eventsourcing.infrastructure;

import com.javastarterkit.patterns.eventsourcing.domain.model.AccountAggregate;

/**
 * Immutable snapshot of aggregate state at a specific version.
 *
 * <p>Snapshots optimize loading of long event streams by capturing a
 * pre-computed state. Rehydration then only requires replaying events
 * <em>after</em> the snapshot version instead of the full history.
 *
 * @param version the aggregate version at which this snapshot was taken
 * @param state   the serializable account state
 */
public record Snapshot(long version, AccountState state) {

    /**
     * Captures the current state of an aggregate as a snapshot.
     *
     * @param aggregate the aggregate to snapshot
     * @return a new immutable snapshot
     */
    public static Snapshot take(AccountAggregate aggregate) {
        return new Snapshot(
                aggregate.version(),
                new AccountState(
                        aggregate.id(),
                        aggregate.owner(),
                        aggregate.balance(),
                        aggregate.isClosed()));
    }
}