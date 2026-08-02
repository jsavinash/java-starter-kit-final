package com.javastarterkit.patterns.hexagonalarchitecture.ports;

import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;
import java.util.Optional;

/**
 * Outbound port (driven): persistence contract for accounts.
 *
 * <p>This interface defines the contract for persisting and retrieving accounts.
 * The application layer depends on this interface, not on concrete implementations.
 * This allows swapping different persistence mechanisms (in-memory, JDBC, JPA, etc.)
 * without changing the application logic.
 */
public interface AccountRepository {

    /**
     * Saves an account to the repository.
     *
     * @param account the account to save
     */
    void save(Account account);

    /**
     * Finds an account by its ID.
     *
     * @param id the account ID
     * @return an Optional containing the account if found, or empty if not found
     */
    Optional<Account> findById(String id);
}