package com.javastarterkit.patterns.eventsourcing.application.projection;

import com.javastarterkit.patterns.eventsourcing.domain.event.AccountOpened;
import com.javastarterkit.patterns.eventsourcing.domain.event.DomainEvent;
import com.javastarterkit.patterns.eventsourcing.domain.event.MoneyDeposited;
import com.javastarterkit.patterns.eventsourcing.domain.event.MoneyWithdrawn;

/**
 * A projection (read model) that tracks aggregate financial metrics by
 * consuming domain events.
 *
 * <p>This is a query-optimized read model that could be stored in a separate
 * database table or cache in a real system. It is rebuilt (or updated
 * incrementally) from the event stream, demonstrating the Event Sourcing
 * read-side pattern.
 *
 * <p><b>Thread-Safety:</b> The projection must be confined to a single thread
 * during event consumption, or synchronized externally when updated from
 * multiple threads.
 */
public final class BalanceProjection {

    private int totalDeposited;
    private int totalWithdrawn;
    private int currentBalance;
    private int transactionCount;

    /**
     * Consumes a single domain event and updates the read model.
     *
     * @param event the domain event to apply
     */
    public void onEvent(DomainEvent event) {
        switch (event) {
            case AccountOpened e -> {
                totalDeposited += e.initialBalance();
                currentBalance += e.initialBalance();
                transactionCount++;
            }
            case MoneyDeposited e -> {
                totalDeposited += e.amount();
                currentBalance += e.amount();
                transactionCount++;
            }
            case MoneyWithdrawn e -> {
                totalWithdrawn += e.amount();
                currentBalance -= e.amount();
                transactionCount++;
            }
            default -> {
                // AccountClosed does not affect balance metrics
            }
        }
    }

    /** @return total amount deposited (including initial balance) */
    public int totalDeposited() {
        return totalDeposited;
    }

    /** @return total amount withdrawn */
    public int totalWithdrawn() {
        return totalWithdrawn;
    }

    /** @return current computed balance */
    public int currentBalance() {
        return currentBalance;
    }

    /** @return total number of balance-affecting transactions */
    public int transactionCount() {
        return transactionCount;
    }
}