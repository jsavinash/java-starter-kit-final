package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

/**
 * Sealed base type for all commands in the CQRS write model.
 *
 * <p>A <i>command</i> is an intent to change state. Commands are processed
 * by the {@link CommandBus} which routes each command to its registered
 * {@link CommandHandler}. The sealed hierarchy guarantees that every
 * command type is known at compile time, enabling exhaustive pattern
 * matching and preventing accidental extension outside the permitted set.
 *
 * <p>Implementations are immutable Java records — a command is a pure data
 * carrier with no behaviour beyond validation.
 */
public sealed interface Command
        permits OpenAccount, DepositMoney, WithdrawMoney {
}
