package com.javastarterkit.patterns.eventdrivenarchitecture.core;

/**
 * Functional interface for event subscribers.
 *
 * <p>Implementations are notified when an event of the subscribed type
 * is published to the {@link EventBus}.</p>
 *
 * @param <T> the concrete event type this listener handles
 */
@FunctionalInterface
public interface EventListener<T extends Event> {

    /**
     * Handles the published event.
     *
     * @param event the event to process
     */
    void onEvent(T event);
}