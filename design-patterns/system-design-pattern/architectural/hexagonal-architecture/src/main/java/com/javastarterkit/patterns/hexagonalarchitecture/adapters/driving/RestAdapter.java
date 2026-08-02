package com.javastarterkit.patterns.hexagonalarchitecture.adapters.driving;

import com.javastarterkit.patterns.hexagonalarchitecture.application.AccountService;
import com.javastarterkit.patterns.hexagonalarchitecture.domain.Account;

/**
 * Driving adapter: a simulated REST controller.
 *
 * <p>This adapter simulates HTTP endpoints that drive the application
 * through the AccountService. In a real system, this would be a Spring
 * MVC/JAX-RS controller.
 */
public class RestAdapter {
    private final AccountService service;

    /**
     * Creates a new RestAdapter with the given service.
     *
     * @param service the account service
     */
    public RestAdapter(AccountService service) {
        this.service = service;
    }

    /**
     * Simulates POST /accounts endpoint.
     *
     * @param body JSON-like body with owner and initialBalance
     * @return the created account ID
     */
    public String post(String path, String body) {
        if (path.equals("/accounts")) {
            String owner = extract(body, "owner");
            String initialBalance = extract(body, "initialBalance");
            Account account = service.openAccount(owner, initialBalance);
            System.out.println("  [REST] POST " + path + " -> 201 Created: " + account.id());
            return account.id();
        }
        if (path.endsWith("/deposits")) {
            String accountId = path.split("/")[2];
            String amount = extract(body, "amount");
            Account account = service.deposit(accountId, amount);
            System.out.println("  [REST] POST " + path + " -> 200 OK: " + account);
            return account.id();
        }
        throw new IllegalArgumentException("Unsupported POST path: " + path);
    }

    /**
     * Simulates GET /accounts/{id} endpoint.
     *
     * @param path the request path
     */
    public void get(String path) {
        String accountId = path.split("/")[2];
        Account account = service.getAccount(accountId);
        System.out.println("  [REST] GET " + path + " -> 200 OK: " + account);
    }

    /**
     * Extracts a field value from a JSON-like string.
     *
     * @param body the JSON-like body
     * @param key the field key
     * @return the field value
     */
    private static String extract(String body, String key) {
        String marker = "\"" + key + "\":\"";
        int start = body.indexOf(marker) + marker.length();
        int end = body.indexOf('"', start);
        return body.substring(start, end);
    }
}