package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.exception;

/**
 * Thrown when a command violates a business invariant or domain rule.
 *
 * <p>This is an unchecked (runtime) exception because domain violations are
 * programming errors that should be fixed at the source rather than recovered
 * at runtime. Throwing a checked exception would force every command handler
 * to declare it, even when no recovery strategy exists.
 */
public class DomainException extends RuntimeException {

    private static final long serialVersionUID = 1L;

    public DomainException(String message) {
        super(message);
    }

    public DomainException(String message, Throwable cause) {
        super(message, cause);
    }
}
