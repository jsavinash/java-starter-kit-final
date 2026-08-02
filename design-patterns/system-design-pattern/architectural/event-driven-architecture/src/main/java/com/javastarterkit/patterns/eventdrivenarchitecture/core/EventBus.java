package com.javastarterkit.patterns.eventdrivenarchitecture.core;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicLong;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Thread-safe in-memory event bus implementing the publish-subscribe pattern.
 *
 * <p>Concurrency strategy:</p>
 * <ul>
 *   <li>{@link ConcurrentHashMap} for thread-safe listener registry keyed by event type.</li>
 *   <li>{@link CopyOnWriteArrayList} for thread-safe iteration during publication.</li>
 *   <li>Virtual-thread-per-task {@link ExecutorService} for asynchronous dispatch.</li>
 *   <li>{@link AtomicLong} for monotonically increasing sequence numbers.</li>
 * </ul>
 *
 * <p>Supports both synchronous and asynchronous event delivery.</p>
 */
public final class EventBus {

    private static final Logger LOGGER = LoggerFactory.getLogger(EventBus.class);

    private final Map<Class<? extends Event>, List<EventListener<? extends Event>>> listeners =
            new ConcurrentHashMap<>();

    private final ExecutorService dispatcher;
    private final AtomicLong sequence = new AtomicLong(0);

    /**
     * Creates an event bus with a virtual-thread-per-task dispatcher.
     */
    public EventBus() {
        this.dispatcher = Executors.newVirtualThreadPerTaskExecutor();
    }

    /**
     * Creates an event bus with a caller-provided dispatcher.
     *
     * @param dispatcher the executor used for asynchronous dispatch
     */
    public EventBus(ExecutorService dispatcher) {
        this.dispatcher = dispatcher;
    }

    /**
     * Registers a listener for a specific event type.
     *
     * @param eventType the event class to subscribe to
     * @param listener  the listener to invoke
     * @param <T>       the concrete event type
     */
    public <T extends Event> void subscribe(Class<T> eventType, EventListener<T> listener) {
        listeners.computeIfAbsent(eventType, k -> new CopyOnWriteArrayList<>())
                .add(listener);
        LOGGER.debug("Subscribed listener {} to {}", listener.getClass().getSimpleName(),
                eventType.getSimpleName());
    }

    /**
     * Removes a listener for a specific event type.
     *
     * @param eventType the event class to unsubscribe from
     * @param listener  the listener to remove
     * @param <T>       the concrete event type
     * @return {@code true} if the listener was removed
     */
    public <T extends Event> boolean unsubscribe(Class<T> eventType, EventListener<T> listener) {
        List<EventListener<? extends Event>> eventListeners = listeners.get(eventType);
        boolean removed = eventListeners != null && eventListeners.remove(listener);
        if (removed) {
            LOGGER.debug("Unsubscribed listener {} from {}", listener.getClass().getSimpleName(),
                    eventType.getSimpleName());
        }
        return removed;
    }

    /**
     * Publishes an event synchronously to all subscribed listeners.
     * Listener exceptions are caught and logged to isolate failures.
     *
     * @param event the event to publish
     * @param <T>   the concrete event type
     */
    public <T extends Event> void publish(T event) {
        long seq = sequence.incrementAndGet();
        LOGGER.debug("Publishing event #{}: {}", seq, event.getClass().getSimpleName());
        dispatch(event);
    }

    /**
     * Publishes an event asynchronously using the configured dispatcher.
     *
     * @param event the event to publish
     * @param <T>   the concrete event type
     */
    public <T extends Event> void publishAsync(T event) {
        long seq = sequence.incrementAndGet();
        LOGGER.debug("Publishing async event #{}: {}", seq, event.getClass().getSimpleName());
        dispatcher.submit(() -> dispatch(event));
    }

    /**
     * Shuts down the dispatcher executor.
     */
    public void shutdown() {
        dispatcher.shutdown();
    }

    @SuppressWarnings("unchecked")
    private <T extends Event> void dispatch(T event) {
        List<EventListener<? extends Event>> eventListeners = listeners.get(event.getClass());
        if (eventListeners == null || eventListeners.isEmpty()) {
            LOGGER.debug("No listeners for {}", event.getClass().getSimpleName());
            return;
        }
        for (EventListener<? extends Event> listener : eventListeners) {
            try {
                ((EventListener<T>) listener).onEvent(event);
            } catch (RuntimeException ex) {
                LOGGER.error("Listener {} failed processing {}",
                        listener.getClass().getSimpleName(), event.getClass().getSimpleName(), ex);
            }
        }
    }
}