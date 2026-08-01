package com.javastarterkit.patterns.composablearchitecture.core;

/**
 * Marker interface for all feature state records in the composable architecture.
 *
 * <p>A {@code State} represents the immutable snapshot of data owned by a
 * feature. In a well-designed composable system, every state object must be:
 * <ul>
 *   <li><b>Immutable</b> — state is never mutated in place; reducers produce
 *       new state instances via copy-on-write.</li>
 *   <li><b>Comparable</b> — implementing {@link Object#equals} / {@link Object#hashCode}
 *       (as records do automatically) enables change detection and testing.</li>
 *   <li><b>Serializable-friendly</b> — states are plain data containers so they
 *       can be persisted, logged, or shipped across a process boundary.</li>
 * </ul>
 *
 * <p><b>Concurrency note:</b> Because states are immutable, they are inherently
 * thread-safe and can be published/shared freely across threads without
 * synchronization. This is one of the key enablers of safe concurrent
 * composition in this architecture.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface State {

    /**
     * A convenience no-op marker used to identify feature state containers.
     * The interface intentionally declares no methods — it exists solely to
     * give the type system a common upper bound for generic reducers,
     * components, and stores.
     */
}