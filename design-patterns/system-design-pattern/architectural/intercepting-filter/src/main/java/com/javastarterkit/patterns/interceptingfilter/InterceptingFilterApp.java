package com.javastarterkit.patterns.interceptingfilter;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.core.FilterManager;
import com.javastarterkit.patterns.interceptingfilter.core.HomePageTarget;
import com.javastarterkit.patterns.interceptingfilter.core.Target;
import com.javastarterkit.patterns.interceptingfilter.filters.AuditFilter;
import com.javastarterkit.patterns.interceptingfilter.filters.AuthenticationFilter;
import com.javastarterkit.patterns.interceptingfilter.filters.CompressionFilter;
import com.javastarterkit.patterns.interceptingfilter.filters.LoggingFilter;
import com.javastarterkit.patterns.interceptingfilter.filters.RateLimitFilter;
import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * Main demonstration class for the Intercepting Filter pattern.
 *
 * <p>Shows how a request flows through an ordered chain of filters (authentication,
 * logging, rate-limiting, compression, audit) before reaching the target handler.
 * Demonstrates chain abortion when a filter returns {@code false} from its
 * {@code before} hook, and the stack-like execution of after-hooks in reverse order.
 */
public class InterceptingFilterApp {

    /**
     * Constructs a fully-configured FilterManager with the standard filter pipeline.
     *
     * <p>Filter order (before hooks):
     * <ol>
     *   <li>AuthenticationFilter — validates user; aborts if unauthenticated</li>
     *   <li>LoggingFilter — logs incoming request</li>
     *   <li>RateLimitFilter — limits requests per user; aborts if exceeded</li>
     *   <li>CompressionFilter — prepares compression of response</li>
     *   <li>AuditFilter — records audit trail</li>
     * </ol>
     *
     * @return a new FilterManager with the standard pipeline
     */
    public static FilterManager createDefaultPipeline() {
        Target target = new HomePageTarget();
        FilterManager manager = new FilterManager(target);

        manager.addFilter(new AuthenticationFilter());
        manager.addFilter(new LoggingFilter());
        manager.addFilter(new RateLimitFilter(3));
        manager.addFilter(new CompressionFilter());
        manager.addFilter(new AuditFilter());

        return manager;
    }

    /**
     * Demonstrates the Intercepting Filter pattern with several scenarios.
     */
    public static void demonstrate() {
        System.out.println("\n=== Intercepting Filter Pattern ===");
        System.out.println("Process requests through a chain of reusable filters\n");

        FilterManager manager = createDefaultPipeline();

        // --- Request 1: valid user, within rate limit --------------------------
        System.out.println("--- Request 1: valid user 'alice' ---");
        Request request1 = new Request("GET", "alice", "/home", "");
        Response response1 = manager.process(request1);
        System.out.println("  Response status: " + response1.status()
                + " | body: " + response1.body());
        System.out.println();

        // --- Request 2: rate limit exceeded ------------------------------------
        System.out.println("--- Request 2: rate limit exceeded for 'alice' ---");
        FilterManager rateLimitManager = createDefaultPipeline();
        for (int i = 0; i < 4; i++) {
            Request req = new Request("GET", "alice", "/home", "");
            Response resp = rateLimitManager.process(req);
            if (resp.status() == 403) {
                System.out.println("  Response status: " + resp.status()
                        + " | body: " + resp.body());
                break;
            }
        }
        System.out.println();

        // --- Request 3: unauthenticated ------------------------------------------
        System.out.println("--- Request 3: unauthenticated request ---");
        Request request3 = new Request("POST", "", "/admin", "{\"data\":\"secret\"}");
        Response response3 = manager.process(request3);
        System.out.println("  Response status: " + response3.status()
                + " | body: " + response3.body());

        System.out.println();
        System.out.println("Benefits:");
        System.out.println("- Cross-cutting concerns isolated into reusable filters");
        System.out.println("- Filters can abort the chain (auth failure, rate limit)");
        System.out.println("- New concerns added without changing the target handler");
        System.out.println("- Filters execute in configurable order");
    }

    /**
     * Main entry point.
     *
     * @param args command-line arguments (not used)
     */
    public static void main(String[] args) {
        demonstrate();
    }

    /**
     * Creates a custom pipeline with only the specified filters.
     *
     * @param target the target handler
     * @param filters the filters to add, in execution order
     * @return a new FilterManager
     */
    public static FilterManager createPipeline(Target target, Filter... filters) {
        FilterManager manager = new FilterManager(target);
        for (Filter filter : filters) {
            manager.addFilter(filter);
        }
        return manager;
    }
}
