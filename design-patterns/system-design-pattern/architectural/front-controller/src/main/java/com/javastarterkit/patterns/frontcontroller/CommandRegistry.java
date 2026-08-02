package com.javastarterkit.patterns.frontcontroller;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.Objects;

/**
 * Thread-safe registry for command mappings.
 *
 * <p>Uses ConcurrentHashMap for O(1) lookup and lock-free concurrent access.
 * This registry is thread-safe and can be safely shared across multiple threads.
 */
public class CommandRegistry {

    private final ConcurrentMap<String, Command> commands = new ConcurrentHashMap<>();

    /**
     * Registers a command for a specific path.
     *
     * <p>This operation is thread-safe and can be called concurrently.
     *
     * @param path the request path (e.g., "/home", "/login")
     * @param command the command to handle requests for this path
     * @throws IllegalArgumentException if path or command is null
     */
    public void register(String path, Command command) {
        Objects.requireNonNull(path, "Path cannot be null");
        Objects.requireNonNull(command, "Command cannot be null");
        commands.put(path, command);
    }

    /**
     * Retrieves the command registered for the given path.
     *
     * @param path the request path
     * @return the command, or null if no command is registered for this path
     */
    public Command get(String path) {
        Objects.requireNonNull(path, "Path cannot be null");
        return commands.get(path);
    }

    /**
     * Checks if a command is registered for the given path.
     *
     * @param path the request path
     * @return true if a command is registered for this path
     */
    public boolean hasCommand(String path) {
        Objects.requireNonNull(path, "Path cannot be null");
        return commands.containsKey(path);
    }

    /**
     * Returns the number of registered commands.
     *
     * @return the number of commands
     */
    public int size() {
        return commands.size();
    }

    /**
     * Clears all registered commands.
     *
     * <p>This operation is thread-safe.
     */
    public void clear() {
        commands.clear();
    }
}