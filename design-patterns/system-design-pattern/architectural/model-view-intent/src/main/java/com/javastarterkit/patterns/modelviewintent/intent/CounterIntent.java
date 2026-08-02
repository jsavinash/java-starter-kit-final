package com.javastarterkit.patterns.modelviewintent.intent;

/**
 * Sealed base for counter intents.
 *
 * <p>Intents are immutable user actions. They are the <b>only</b> way to
 * change state in the MVI pattern.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public sealed interface CounterIntent permits Increment, Decrement, Reset {
}