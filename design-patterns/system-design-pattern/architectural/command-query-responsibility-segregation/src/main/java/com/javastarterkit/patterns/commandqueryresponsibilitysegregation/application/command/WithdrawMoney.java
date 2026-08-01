package com.javastarterkit.patterns.commandqueryresponsibilitysegregation.application.command;

/**
 * Command to withdraw a positive amount from an existing account.
 *
 * @param accountId the identifier of the account to withdraw from
 * @param amount    the positive amount to withdraw (smallest currency unit)
 */
public record WithdrawMoney(String accountId, int amount)
        implements Command {
}
