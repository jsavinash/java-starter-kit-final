package com.javastarterkit.patterns.flux.models;

/**
 * Enumeration of todo visibility filters.
 *
 * <p>Represents the three standard filtering modes for a todo list application.
 */
public enum Filter {
    /** Show all todos regardless of completion status */
    ALL,
    /** Show only active (incomplete) todos */
    ACTIVE,
    /** Show only completed todos */
    COMPLETED
}