package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.AccountClosed;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.AccountOpened;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.MoneyDeposited;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.MoneyWithdrawn;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.AccountReadModel;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure.EventBus;

/**
 * Read-side projection that subscribes to domain events and maintains the
 * {@link AccountReadModel} in sync with the write model.
 *
 * <h3>Responsibility</h3>
 * <p>The projection is the <b>synchronization bridge</b> between the command
 * (write) side and the query (read) side. Every domain event that passes
 * through the {@link EventBus} is handled here, transforming the event into
 * denormalized state suitable for fast, purpose-built queries.
 *
 * <h3>Thread-Safety</h3>
 * <p>The projection itself holds no mutable state — all mutations are
 * delegated to the thread-safe {@link AccountReadModel}. Each event handler
 * method is therefore safe to invoke from any thread, provided the underlying
 * read model is thread-safe (which it is).
 */
public final class AccountProjection {

    private final AccountReadModel readModel;

    /**
     * @param readModel the denormalized read model to keep in sync
     */
    public AccountProjection(AccountReadModel readModel) {
        this.readModel = readModel;
    }

    /**
     * Registers all event handlers with the given {@link EventBus}.
     *
     * <p>Called once during application start-up. Each registration binds a
     * specific event type to a method reference on this projection.
     *
     * @param eventBus the event bus through which domain events flow
     */
    public void registerWith(EventBus eventBus) {
        eventBus.register(AccountOpened.class, this::onAccountOpened);
        eventBus.register(MoneyDeposited.class, this::onMoneyDeposited);
        eventBus.register(MoneyWithdrawn.class, this::onMoneyWithdrawn);
        eventBus.register(AccountClosed.class, this::onAccountClosed);
    }

    /** Handles {@link AccountOpened}: creates a new entry in the read model. */
    public void onAccountOpened(AccountOpened event) {
        readModel.addAccount(event.aggregateId(), event.owner(), event.initialBalance());
    }

    /** Handles {@link MoneyDeposited}: increases the balance in the read model. */
    public void onMoneyDeposited(MoneyDeposited event) {
        readModel.updateBalance(event.aggregateId(), event.amount(), true);
    }

    /** Handles {@link MoneyWithdrawn}: decreases the balance in the read model. */
    public void onMoneyWithdrawn(MoneyWithdrawn event) {
        readModel.updateBalance(event.aggregateId(), event.amount(), false);
    }

    /** Handles {@link AccountClosed}: marks the account as closed in the read model. */
    public void onAccountClosed(AccountClosed event) {
        readModel.closeAccount(event.aggregateId());
    }
}
