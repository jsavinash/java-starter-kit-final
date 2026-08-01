package com.javastarterkit.patterns.interceptingfilter;

import java.util.ArrayList;
import java.util.List;

/**
 * Intercepting Filter Pattern Example
 *
 * <p>The <b>Intercepting Filter</b> pattern provides a mechanism to process a
 * request before it reaches the target handler (and after the response is
 * produced). Instead of embedding cross-cutting concerns directly into the
 * handler, these concerns (authentication, logging, compression, etc.) are
 * implemented as reusable <b>filters</b> that are chained together. Each
 * filter runs in sequence, optionally aborting the chain, and the target
 * handler executes only after all filters pass.
 *
 * <p>This self-contained example models a simple web request pipeline:
 * <ul>
 *   <li><b>Filter</b> — {@link Filter} base class/interface with
 *       {@code before} / {@code after} hooks</li>
 *   <li><b>Request/Response</b> — {@link Request} and {@link Response}
 *       carry the data through the pipeline</li>
 *   <li><b>Filter Chain</b> — {@link FilterChain} holds an ordered list of
 *       filters and invokes them around the {@link Target}</li>
 *   <li><b>Target</b> — {@link Target} is the actual business handler that
 *       produces the response</li>
 *   <li><b>Concrete Filters</b> — {@link AuthenticationFilter},
 *       {@link LoggingFilter}, {@link CompressionFilter},
 *       {@link RateLimitFilter}, {@link AuditFilter}</li>
 *   <li><b>FilterManager</b> — {@link FilterManager} registers filters and
 *       executes the chain</li>
 * </ul>
 *
 * <p>Key benefit: adding a new cross-cutting concern (e.g. rate limiting)
 * requires only adding a new filter to the chain — no changes to the target
 * handler or existing filters.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public class InterceptingFilter {

    /**
     * Demonstrates the intercepting filter pattern: build a filter chain with
     * authentication, logging, and rate-limit filters, then process several
     * requests to show the filters executing in order around the target.
     */
    public static void demonstrate() {
        System.out.println("\n=== Intercepting Filter Pattern ===");
        System.out.println("Process requests through a chain of reusable filters\n");

        // --- Build the filter pipeline -----------------------------------------
        FilterManager manager = new FilterManager(new HomePageTarget());
        manager.addFilter(new AuthenticationFilter());
        manager.addFilter(new LoggingFilter());
        manager.addFilter(new RateLimitFilter(3));
        manager.addFilter(new CompressionFilter());

        // --- Process a few requests --------------------------------------------
        System.out.println("--- Request 1: valid user ---");
        Request r1 = new Request("GET", "alice", "/home", "{\"query\":\"hello\"}");
        Response res1 = manager.process(r1);
        System.out.println("  Response status: " + res1.status() + " | body: " + res1.body());

        System.out.println("\n--- Request 2: blocked (rate limit exceeded) ---");
        for (int i = 0; i < 3; i++) {
            manager.process(new Request("GET", "alice", "/home", "{\"query\":\"ping\"}"));
        }
        Request r2 = new Request("GET", "alice", "/home", "{\"query\":\"pong\"}");
        Response res2 = manager.process(r2);
        System.out.println("  Response status: " + res2.status() + " | body: " + res2.body());

        System.out.println("\n--- Request 3: unauthenticated ---");
        Request r3 = new Request("POST", "", "/admin", "{}");
        Response res3 = manager.process(r3);
        System.out.println("  Response status: " + res3.status() + " | body: " + res3.body());

        System.out.println("\nBenefits:");
        System.out.println("- Cross-cutting concerns are isolated into reusable filters");
        System.out.println("- Filters can abort the chain (auth failure, rate limit)");
        System.out.println("- New concerns added without changing the target handler");
        System.out.println("- Filters execute in configurable order");
    }

    public static void main(String[] args) {
        demonstrate();
    }

    // =========================================================================
    // Request / Response — data flowing through the pipeline
    // =========================================================================

    /** Immutable request carrying method, user, path, and payload. */
    record Request(String method, String user, String path, String payload) {
    }

    /** Mutable response produced by the pipeline. */
    static final class Response {
        private int status = 200;
        private String body = "";

        Response status(int status) {
            this.status = status;
            return this;
        }

        Response body(String body) {
            this.body = body;
            return this;
        }

        int status() {
            return status;
        }

        String body() {
            return body;
        }
    }

    // =========================================================================
    // Filter — abstract base for all intercepting filters
    // =========================================================================

    /**
     * Base for all filters. Subclasses override {@link #before} and/or
     * {@link #after} to perform pre/post-processing. Returning {@code false}
     * from {@link #before} aborts the chain (the target is not invoked).
     */
    abstract static class Filter {
        abstract boolean before(Request request);

        void after(Request request, Response response) {
        }

        @Override
        public String toString() {
            return getClass().getSimpleName();
        }
    }

    // =========================================================================
    // Filter Chain — invokes filters in order around the target
    // =========================================================================

    /**
     * Manages the ordered list of filters and invokes them around a target.
     * If any filter's {@code before} returns {@code false}, the chain stops
     * and the target is never reached.
     */
    static final class FilterChain {
        private final List<Filter> filters = new ArrayList<>();
        private final Target target;

        FilterChain(Target target) {
            this.target = target;
        }

        void addFilter(Filter filter) {
            filters.add(filter);
        }

        Response proceed(Request request) {
            Response response = new Response();

            // Run before-hooks; abort if any filter says "stop"
            for (Filter filter : filters) {
                if (!filter.before(request)) {
                    response.status(403).body("Blocked by " + filter);
                    return response;
                }
            }

            // Invoke the target handler
            target.execute(request, response);

            // Run after-hooks in reverse order
            for (int i = filters.size() - 1; i >= 0; i--) {
                filters.get(i).after(request, response);
            }

            return response;
        }
    }

    // =========================================================================
    // Target — the actual business handler
    // =========================================================================

    /** The target handler that ultimately fulfills the request. */
    interface Target {
        void execute(Request request, Response response);
    }

    /** Simple target that renders the home page. */
    static final class HomePageTarget implements Target {
        @Override
        public void execute(Request request, Response response) {
            response.body("Welcome " + (request.user().isEmpty() ? "Guest" : request.user())
                    + " to the home page!");
        }
    }

    // =========================================================================
    // Concrete Filters — reusable cross-cutting concerns
    // =========================================================================

    /** Authenticates the request; aborts if no user is present. */
    static final class AuthenticationFilter extends Filter {
        @Override
        boolean before(Request request) {
            if (request.user() == null || request.user().isEmpty()) {
                System.out.println("  [AUTH] Rejecting unauthenticated request to " + request.path());
                return false;
            }
            System.out.println("  [AUTH] Authenticated user '" + request.user() + "'");
            return true;
        }
    }

    /** Logs every request and response. */
    static final class LoggingFilter extends Filter {
        @Override
        boolean before(Request request) {
            System.out.println("  [LOG]  -> " + request.method() + " " + request.path()
                    + " user=" + request.user());
            return true;
        }

        @Override
        void after(Request request, Response response) {
            System.out.println("  [LOG]  <- status=" + response.status() + " body='" + response.body() + "'");
        }
    }

    /** Limits request rate per user; aborts when the limit is exceeded. */
    static final class RateLimitFilter extends Filter {
        private final int maxRequests;
        private final java.util.Map<String, Integer> counts = new java.util.HashMap<>();

        RateLimitFilter(int maxRequests) {
            this.maxRequests = maxRequests;
        }

        @Override
        boolean before(Request request) {
            String key = request.user();
            int current = counts.getOrDefault(key, 0);
            if (current >= maxRequests) {
                System.out.println("  [RATE] User '" + key + "' exceeded limit of " + maxRequests);
                return false;
            }
            counts.put(key, current + 1);
            System.out.println("  [RATE] User '" + key + "' request " + (current + 1) + "/" + maxRequests);
            return true;
        }
    }

    /** Compresses the response body (simulated). */
    static final class CompressionFilter extends Filter {
        @Override
        boolean before(Request request) {
            System.out.println("  [COMP] Preparing compression for " + request.path());
            return true;
        }

        @Override
        void after(Request request, Response response) {
            String body = response.body();
            // Simulate gzip-style compression: prefix with <compressed>
            response.body("<compressed>" + body + "</compressed>");
            System.out.println("  [COMP] Compressed response (" + body.length() + " -> "
                    + response.body().length() + " chars)");
        }
    }

    /** Audits requests after processing. */
    static final class AuditFilter extends Filter {
        @Override
        boolean before(Request request) {
            return true;
        }

        @Override
        void after(Request request, Response response) {
            System.out.println("  [AUDIT] Recorded " + request.method() + " " + request.path()
                    + " for user '" + request.user() + "' (status=" + response.status() + ")");
        }
    }

    // =========================================================================
    // Filter Manager — entry point that builds and runs the chain
    // =========================================================================

    /**
     * The filter manager is the entry point for clients. It owns the filter
     * chain and exposes a simple {@code process()} method.
     */
    static final class FilterManager {
        private final FilterChain chain;

        FilterManager(Target target) {
            this.chain = new FilterChain(target);
        }

        void addFilter(Filter filter) {
            chain.addFilter(filter);
        }

        Response process(Request request) {
            return chain.proceed(request);
        }
    }

}
