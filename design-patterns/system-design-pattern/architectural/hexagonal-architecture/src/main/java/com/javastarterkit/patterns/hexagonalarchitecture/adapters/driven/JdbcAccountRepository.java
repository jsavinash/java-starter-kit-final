package com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven;

import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.AccountRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Driven adapter: simulated JDBC implementation of AccountRepository.
 *
 * <p>This adapter simulates a JDBC-based persistence mechanism.
 * In a real system, this would use JDBC or JPA to persist accounts.
 *
 * <p>This implementation is thread-safe.
 */
public class JdbcAccountRepository implements AccountRepository {

    private final Map<String, Account> store = new ConcurrentHashMap<>();

    /**
     * Saves an account to the database (simulated).
     *
     * @param account the account to save
     */
    @Override
    public void save(Account account) {
        // Simulate an INSERT ... ON CONFLICT UPDATE
        store.put(account.id(), account);
        System.out.println("  [JDBC] Persisted account " + account.id()
            + " (balance=" + account.balance() + ")");
    }

    /**
     * Finds an account by its ID from the database (simulated).
     *
     * @param id the account ID
     * @return an Optional containing the account if found
     */
    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}