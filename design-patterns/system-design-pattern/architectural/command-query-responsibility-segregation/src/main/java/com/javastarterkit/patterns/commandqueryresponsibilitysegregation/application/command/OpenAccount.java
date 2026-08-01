package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

/**
 * Command to open a new bank account.
 *
 * @param accountId      the unique identifier assigned to the new account
 * @param owner          the name of the account owner
 * @param initialBalance the starting balance in the smallest currency unit
 *                       (e.g. cents); must be non-negative
 */
public record OpenAccount(String accountId, String owner, int initialBalance)
        implements Command {
}
