package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

/**
 * Contract for a command handler.
 *
 * <p>Each handler is responsible for exactly one {@link Command} type.
 * A handler loads the aggregate (when applicable), invokes the appropriate
 * domain method, and persists the resulting events through the repository.
 *
 * @param <C> the command type this handler processes
 */
public interface CommandHandler<C extends Command> {

    /**
     * Processes the given command, mutating domain state as required.
     *
     * @param command the command to handle (never {@code null})
     */
    void handle(C command);
}
