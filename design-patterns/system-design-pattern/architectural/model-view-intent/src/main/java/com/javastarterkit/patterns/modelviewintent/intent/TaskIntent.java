package com.javastarterkit.patterns.modelviewintent.intent;

/**
 * Sealed base for task list intents.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public sealed interface TaskIntent permits AddTask, CompleteTask {
}