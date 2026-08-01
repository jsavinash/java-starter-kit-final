package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

/**
 * Command to deposit a positive amount into an existing account.
 *
 * @param accountId the identifier of the account to deposit into
 * @param amount    the positive amount to deposit (smallest currency unit)
 */
public record DepositMoney(String accountId, int amount)
        implements Command {
}
