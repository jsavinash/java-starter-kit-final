package com.javastarterkit.patterns.modelviewintent.core;

/**
 * Observer notified when the store's state changes.
 *
 * @param <S> the state type
 * @author Java Starter Kit
 * @version 1.0.0
 */
public interface ViewObserver<S> {

    /**
     * Called when the store's state changes.
     *
     * @param state the new state
     */
    void onStateChanged(S state);
}