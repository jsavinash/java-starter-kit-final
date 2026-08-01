package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

import com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception.HandlerNotFoundException;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * Thread-safe mediator that routes commands to their registered handlers.
 *
 * <h3>Design Notes</h3>
 * <ul>
 *   <li>Handlers are registered at application start-up (single-threaded phase)
 *       and looked up at dispatch time. The {@link ConcurrentHashMap} provides
 *       lock-free reads for the hot dispatch path.</li>
 *   <li>Type safety is enforced at registration time. Due to Java type erasure,
 *       {@code CommandHandler<C>.handle(C)} erases to {@code handle(Command)},
 *       so a single raw-type cast in {@link #dispatch} is always safe.</li>
 * </ul>
 */
public final class CommandBus {

    /** Maps each command type to its handler. */
    private final ConcurrentMap<Class<?>, CommandHandler<?>> handlers = new ConcurrentHashMap<>();

    /**
     * Registers a handler for the given command type.
     *
     * @param commandType the command class this handler handles
     * @param handler     the handler instance
     * @param <C>         the command type
     */
    public <C extends Command> void register(Class<C> commandType, CommandHandler<C> handler) {
        handlers.put(commandType, handler);
    }

    /**
     * Dispatches a command to its registered handler.
     *
     * @param command the command to dispatch
     * @throws HandlerNotFoundException if no handler is registered for the command type
     */
    public void dispatch(Command command) {
        var handler = handlers.get(command.getClass());
        if (handler == null) {
            throw new HandlerNotFoundException(
                    "No handler registered for command: " + command.getClass().getSimpleName());
        }
        invokeHandler(handler, command);
    }

    /**
     * Invokes the handler for the given command.
     *
     * <p>The cast to the raw {@code CommandHandler} type is safe because
     * {@code CommandHandler<C extends Command>.handle(C)} erases to
     * {@code handle(Command)} — every handler accepts a {@code Command}
     * at the bytecode level regardless of its specific subtype {@code C}.
     */
    @SuppressWarnings("rawtypes")
    private static void invokeHandler(CommandHandler handler, Command command) {
        handler.handle(command);
    }
}
