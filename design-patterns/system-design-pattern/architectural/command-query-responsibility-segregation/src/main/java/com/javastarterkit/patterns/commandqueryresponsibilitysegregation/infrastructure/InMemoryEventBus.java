package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.infrastructure;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.projection.EventHandler;
import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.domain.event.DomainEvent;

import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.CopyOnWriteArrayList;

/**
 * Thread-safe, in-memory implementation of {@link EventBus}.
 *
 * <h3>Concurrency Strategy</h3>
 * <ul>
 *   <li>The subscriber map is a {@link ConcurrentHashMap} keyed by event
 *       type. Registration (write) and publish (read) can run concurrently
 *       without blocking each other.</li>
 *   <li>Each event type's subscriber list is a {@link CopyOnWriteArrayList},
 *       making iteration during {@link #publish} lock-free and safe even
 *       if another thread registers a new handler concurrently.</li>
 * </ul>
 */
public final class InMemoryEventBus implements EventBus {

    /**
     * Maps each event type to its list of subscriber handlers.
     * CopyOnWriteArrayList ensures safe concurrent iteration.
     */
    private final ConcurrentMap<Class<?>, List<EventHandler<?>>> handlers = new ConcurrentHashMap<>();

    @Override
    public <E extends DomainEvent> void register(Class<E> eventType, EventHandler<E> handler) {
        handlers.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>()).add(handler);
    }

    @Override
    public void publish(DomainEvent event) {
        var listeners = handlers.getOrDefault(event.getClass(), List.of());
        for (EventHandler<?> handler : listeners) {
            invokeHandler(handler, event);
        }
    }

    /**
     * The raw-type cast is safe because {@code EventHandler<E>.handle(E)}
     * erases to {@code handle(DomainEvent)} — every handler accepts a
     * {@code DomainEvent} at the bytecode level. The EventBus only
     * dispatches events to handlers registered for the event's concrete
     * type, so the dispatch is type-safe at both compile and run time.
     */
    @SuppressWarnings("rawtypes")
    private static void invokeHandler(EventHandler handler, DomainEvent event) {
        handler.handle(event);
    }
}
