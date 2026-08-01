package com.javastarterkit.patterns.interceptingfilter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.AuditFilter;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.AuthenticationFilter;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.CompressionFilter;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.Filter;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.FilterChain;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.FilterManager;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.HomePageTarget;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.LoggingFilter;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.RateLimitFilter;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.Request;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.Response;
import com.javastarterkit.patterns.interceptingfilter.InterceptingFilter.Target;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

/**
 * Unit tests verifying the intercepting filter pattern: filters execute in
 * order around the target, filters can abort the chain, and cross-cutting
 * concerns are isolated into reusable filters.
 */
class InterceptingFilterTest {

    @Test
    @DisplayName("filters execute in order around the target")
    void filtersExecuteInOrder() {
        StringBuilder order = new StringBuilder();

        Filter first = new Filter() {
            @Override
            boolean before(Request request) {
                order.append("first-before;");
                return true;
            }

            @Override
            void after(Request request, Response response) {
                order.append("first-after;");
            }
        };

        Filter second = new Filter() {
            @Override
            boolean before(Request request) {
                order.append("second-before;");
                return true;
            }

            @Override
            void after(Request request, Response response) {
                order.append("second-after;");
            }
        };

        Target target = (request, response) -> order.append("target;");

        FilterChain chain = new FilterChain(target);
        chain.addFilter(first);
        chain.addFilter(second);
        chain.proceed(new Request("GET", "alice", "/home", "{}"));

        assertEquals("first-before;second-before;target;second-after;first-after;", order.toString());
    }

    @Test
    @DisplayName("authentication filter aborts the chain for unauthenticated users")
    void authenticationAbortsChain() {
        FilterManager manager = new FilterManager(new HomePageTarget());
        manager.addFilter(new AuthenticationFilter());
        manager.addFilter(new LoggingFilter());

        Response response = manager.process(new Request("GET", "", "/admin", "{}"));

        assertEquals(403, response.status());
        assertTrue(response.body().contains("Blocked by"));
    }

    @Test
    @DisplayName("rate limit filter aborts the chain when limit is exceeded")
    void rateLimitAbortsChain() {
        FilterManager manager = new FilterManager(new HomePageTarget());
        manager.addFilter(new RateLimitFilter(2));

        Request request = new Request("GET", "bob", "/home", "{}");

        Response first = manager.process(request);
        Response second = manager.process(request);
        Response third = manager.process(request);

        assertEquals(200, first.status());
        assertEquals(200, second.status());
        assertEquals(403, third.status());
    }

    @Test
    @DisplayName("compression filter wraps the response body")
    void compressionWrapsResponse() {
        FilterManager manager = new FilterManager(new HomePageTarget());
        manager.addFilter(new CompressionFilter());

        Response response = manager.process(new Request("GET", "alice", "/home", "{}"));

        assertEquals(200, response.status());
        assertTrue(response.body().startsWith("<compressed>"));
        assertTrue(response.body().endsWith("</compressed>"));
    }

    @Test
    @DisplayName("audit filter runs after the target without affecting the response")
    void auditRunsAfterTarget() {
        FilterManager manager = new FilterManager(new HomePageTarget());
        manager.addFilter(new AuditFilter());

        Response response = manager.process(new Request("GET", "alice", "/home", "{}"));

        assertEquals(200, response.status());
        assertTrue(response.body().contains("Welcome alice"));
    }

    @Test
    @DisplayName("target is not invoked when a filter aborts the chain")
    void targetNotInvokedWhenAborted() {
        boolean[] targetInvoked = {false};

        Target target = (request, response) -> targetInvoked[0] = true;

        FilterChain chain = new FilterChain(target);
        chain.addFilter(new AuthenticationFilter());
        chain.proceed(new Request("GET", "", "/admin", "{}"));

        assertFalse(targetInvoked[0]);
    }

    @Test
    @DisplayName("demonstrate runs without throwing")
    void demonstrateRunsSuccessfully() {
        // Smoke test: the public demonstration should complete without errors.
        InterceptingFilter.demonstrate();
    }
}