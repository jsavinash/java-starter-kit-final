package com.javastarterkit.patterns.hexagonalarchitecture.adapters.driven;

import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;
import com.javastarterkit.patterns.hexagonalarchitecture.ports.AccountRepository;

import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Driven adapter: in-memory implementation of AccountRepository.
 *
 * <p>This adapter stores accounts in memory using a ConcurrentHashMap.
 * It is suitable for testing, demos, and prototypes.
 *
 * <p>This implementation is thread-safe.
 */
public class InMemoryAccountRepository implements AccountRepository {

    private final Map<String, Account> store = new ConcurrentHashMap<>();

    /**
     * Saves an account to the in-memory store.
     *
     * @param account the account to save
     */
    @Override
    public void save(Account account) {
        store.put(account.id(), account);
    }

    /**
     * Finds an account by its ID.
     *
     * @param id the account ID
     * @return an Optional containing the account if found
     */
    @Override
    public Optional<Account> findById(String id) {
        return Optional.ofNullable(store.get(id));
    }
}