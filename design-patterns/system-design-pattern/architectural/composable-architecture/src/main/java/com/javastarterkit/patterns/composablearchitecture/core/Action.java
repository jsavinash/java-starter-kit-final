package com.javastarterkit.patterns.composablearchitecture.core;

/**
 * Marker interface for all feature actions in the composable architecture.
 *
 * <p>An {@code Action} describes a discrete event — a user gesture, a system
 * notification, or an external trigger — that the store feeds into a reducer
 * to produce the next state. Actions are deliberately:
 * <ul>
 *   <li><b>Immutable</b> — each action is a value that captures everything the
 *       reducer needs to compute the next state.</li>
 *   <li><b>Sealed</b> — concrete feature actions are defined as sealed
 *       interfaces so the compiler enforces exhaustive handling in
 *       switch expressions (Java 21+ pattern matching).</li>
 *   <li><b>Serializable-friendly</b> — actions are plain data carriers so they
 *       can be logged, persisted, or sent across a process boundary.</li>
 * </ul>
 *
 * <p>Feature code declares its own action hierarchy, typically as a sealed
 * interface with nested record implementations:
 * <pre>{@code
 * sealed interface CounterAction extends Action {
 *     record increment() implements CounterAction {}
 *     record decrement() implements CounterAction {}
 * }
 * }</pre>
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface Action {
}