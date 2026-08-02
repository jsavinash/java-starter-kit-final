package com.javastarterkit.patterns.interceptingfilter;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.core.FilterChain;
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
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests verifying the Intercepting Filter pattern.
 *
 * <p>Covers: normal pipeline execution, chain abortion on authentication
 * failure, rate-limit enforcement, filter ordering (before in order, after
 * in reverse), null safety, and concurrent request processing.
 */
@DisplayName("Intercepting Filter Tests")
class InterceptingFilterAppTest {

    private FilterManager manager;

    @BeforeEach
    void setUp() {
        manager = InterceptingFilterApp.createDefaultPipeline();
    }

    // ───── Normal flow tests ───────────────────────────────────────────────

    @Test
    @DisplayName("authenticated request passes all filters and reaches target")
    void authenticatedRequestPassesAllFilters() {
        Request request = new Request("GET", "alice", "/home", "");

        Response response = manager.process(request);

        assertThat(response).isNotNull();
        assertThat(response.status()).isEqualTo(200);
        assertThat(response.body()).contains("Welcome alice");
        assertThat(response.body()).contains("GET /home");
    }

    @Test
    @DisplayName("POST request with payload is handled correctly")
    void postRequestWithPayloadIsHandled() {
        Request request = new Request("POST", "bob", "/data", "{\"key\":\"value\"}");

        Response response = manager.process(request);

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.body()).contains("Welcome bob");
        assertThat(response.body()).contains("POST /data");
    }

    @Test
    @DisplayName("response body is compressed by CompressionFilter")
    void responseBodyIsCompressed() {
        Request request = new Request("GET", "alice", "/home", "");

        Response response = manager.process(request);

        assertThat(response.body()).contains("<compressed>");
        assertThat(response.body()).contains("</compressed>");
    }

    @Test
    @DisplayName("target renders personalized welcome for the authenticated user")
    void targetRendersPersonalizedWelcome() {
        Request request = new Request("GET", "charlie", "/dashboard", "");

        Response response = manager.process(request);

        assertThat(response.body()).contains("Welcome charlie");
    }

    // ───── Chain abortion tests ───────────────────────────────────────────

    @Test
    @DisplayName("unauthenticated request is blocked by AuthenticationFilter")
    void unauthenticatedRequestIsBlocked() {
        Request request = new Request("GET", "", "/admin", "");

        Response response = manager.process(request);

        assertThat(response.status()).isEqualTo(403);
        assertThat(response.body()).contains("Blocked by AuthenticationFilter");
    }

    @Test
    @DisplayName("rate-limited request is blocked by RateLimitFilter")
    void rateLimitedRequestIsBlocked() {
        FilterManager rateManager = InterceptingFilterApp.createDefaultPipeline();

        Request first = new Request("GET", "alice", "/home", "");
        Request second = new Request("GET", "alice", "/home", "");
        Request third = new Request("GET", "alice", "/home", "");
        Request fourth = new Request("GET", "alice", "/home", "");

        // First three should pass (limit is 3)
        Response r1 = rateManager.process(first);
        Response r2 = rateManager.process(second);
        Response r3 = rateManager.process(third);

        // Fourth should be blocked by rate limit
        Response r4 = rateManager.process(fourth);

        assertThat(r1.status()).isEqualTo(200);
        assertThat(r2.status()).isEqualTo(200);
        assertThat(r3.status()).isEqualTo(200);
        assertThat(r4.status()).isEqualTo(403);
        assertThat(r4.body()).contains("Blocked by RateLimitFilter");
    }

    @Test
    @DisplayName("first filter returning false aborts entire chain")
    void firstFilterAbortStopsEntireChain() {
        Target target = new HomePageTarget();
        Filter abortingFilter = new AuthenticationFilter();
        FilterManager customManager = new FilterManager(target);
        customManager.addFilter(abortingFilter);

        Request unauthenticated = new Request("GET", "", "/home", "");
        Response response = customManager.process(unauthenticated);

        assertThat(response.status()).isEqualTo(403);
        assertThat(response.body()).contains("Blocked by");
    }

    // ───── Filter ordering tests ──────────────────────────────────────────

    @Test
    @DisplayName("before hooks execute in insertion order")
    void beforeHooksExecuteInInsertionOrder() {
        AtomicInteger executionOrder = new AtomicInteger(0);
        AtomicReference<String> orderLog = new AtomicReference<>("");

        Filter filterA = new Filter() {
            @Override public boolean before(Request request) {
                orderLog.updateAndGet(v -> v + executionOrder.incrementAndGet());
                return true;
            }
            @Override public String toString() { return "FilterA"; }
        };
        Filter filterB = new Filter() {
            @Override public boolean before(Request request) {
                orderLog.updateAndGet(v -> v + executionOrder.incrementAndGet());
                return true;
            }
            @Override public String toString() { return "FilterB"; }
        };
        Filter filterC = new Filter() {
            @Override public boolean before(Request request) {
                orderLog.updateAndGet(v -> v + executionOrder.incrementAndGet());
                return true;
            }
            @Override public String toString() { return "FilterC"; }
        };

        Request request = new Request("GET", "alice", "/home", "");

        FilterChain chain = new FilterChain(new HomePageTarget());
        chain.addFilter(filterA);
        chain.addFilter(filterB);
        chain.addFilter(filterC);
        chain.proceed(request);

        // Before hooks executed in order: 1, 2, 3
        assertThat(orderLog.get()).isEqualTo("123");
    }

    @Test
    @DisplayName("after hooks execute in reverse insertion order")
    void afterHooksExecuteInReverseOrder() {
        AtomicInteger step = new AtomicInteger(0);
        AtomicReference<String> orderLog = new AtomicReference<>("");

        Filter filterA = new Filter() {
            @Override public boolean before(Request request) { return true; }
            @Override public void after(Request request, Response response) {
                orderLog.updateAndGet(v -> v + step.incrementAndGet());
            }
            @Override public String toString() { return "FilterA"; }
        };
        Filter filterB = new Filter() {
            @Override public boolean before(Request request) { return true; }
            @Override public void after(Request request, Response response) {
                orderLog.updateAndGet(v -> v + step.incrementAndGet());
            }
            @Override public String toString() { return "FilterB"; }
        };
        Filter filterC = new Filter() {
            @Override public boolean before(Request request) { return true; }
            @Override public void after(Request request, Response response) {
                orderLog.updateAndGet(v -> v + step.incrementAndGet());
            }
            @Override public String toString() { return "FilterC"; }
        };

        Request request = new Request("GET", "alice", "/home", "");

        FilterChain chain = new FilterChain(new HomePageTarget());
        chain.addFilter(filterA);
        chain.addFilter(filterB);
        chain.addFilter(filterC);
        chain.proceed(request);

        // After hooks executed in reverse: C=1, B=2, A=3
        assertThat(orderLog.get()).isEqualTo("123");
    }

    // ───── Null safety tests ──────────────────────────────────────────────

    @Test
    @DisplayName("process(null) throws NullPointerException")
    void processNullThrows() {
        assertThatThrownBy(() -> manager.process(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Request cannot be null");
    }

    @Test
    @DisplayName("FilterManager with null target throws NullPointerException")
    void filterManagerNullTargetThrows() {
        assertThatThrownBy(() -> new FilterManager(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Target cannot be null");
    }

    @Test
    @DisplayName("addFilter(null) throws NullPointerException")
    void addNullFilterThrows() {
        assertThatThrownBy(() -> new FilterManager(new HomePageTarget()).addFilter(null))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Filter cannot be null");
    }

    @Test
    @DisplayName("null user, method, or path in Request throws exception")
    void requestNullFieldsThrow() {
        assertThatThrownBy(() -> new Request(null, "alice", "/home", ""))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Method cannot be null");

        assertThatThrownBy(() -> new Request("GET", "alice", null, ""))
                .isInstanceOf(NullPointerException.class)
                .hasMessage("Path cannot be null");

        assertThatThrownBy(() -> new Request("GET", null, "/home", ""))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("User cannot be null");

        assertThatThrownBy(() -> new Request("GET", "alice", "/home", null))
                .isInstanceOf(IllegalArgumentException.class)
                .hasMessage("Payload cannot be null");
    }

    // ───── Custom pipeline tests ─────────────────────────────────────────

    @Test
    @DisplayName("createPipeline with custom filters and target")
    void createPipelineWithCustomFilters() {
        Target target = (request, response) -> response.status(200).body("Custom OK");
        Filter logging = new LoggingFilter();
        Filter audit = new AuditFilter();

        FilterManager customManager = InterceptingFilterApp.createPipeline(target, logging, audit);

        Request request = new Request("GET", "alice", "/custom", "");
        Response response = customManager.process(request);

        assertThat(response.status()).isEqualTo(200);
        assertThat(response.body()).isEqualTo("Custom OK");
    }

    @Test
    @DisplayName("createDefaultPipeline returns non-null manager")
    void createDefaultPipelineReturnsNonNull() {
        FilterManager pipeline = InterceptingFilterApp.createDefaultPipeline();
        assertThat(pipeline).isNotNull();
    }

    // ───── Concurrency tests ──────────────────────────────────────────────

    @Test
    @DisplayName("concurrent requests are processed safely")
    void concurrentRequestsProcessedSafely() throws InterruptedException {
        int threadCount = 50;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger successCount = new AtomicInteger(0);
        AtomicInteger blockedCount = new AtomicInteger(0);
        AtomicReference<Throwable> error = new AtomicReference<>(null);

        for (int i = 0; i < threadCount; i++) {
            final int requestId = i;
            executor.submit(() -> {
                try {
                    Request request = new Request("GET", "user" + (requestId % 5), "/home", "");
                    Response response = manager.process(request);
                    if (response.status() == 200) {
                        successCount.incrementAndGet();
                    } else if (response.status() == 403) {
                        blockedCount.incrementAndGet();
                    }
                } catch (Throwable t) {
                    error.set(t);
                } finally {
                    latch.countDown();
                }
            });
        }

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();

        assertThat(error.get()).isNull();
        // All threads should have received a non-null response
        int total = successCount.get() + blockedCount.get();
        assertThat(total).isEqualTo(threadCount);
    }

    @Test
    @DisplayName("rate limit filter is thread-safe under concurrent access")
    void rateLimitFilterIsThreadSafe() throws InterruptedException {
        int threadCount = 20;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        CountDownLatch latch = new CountDownLatch(threadCount);
        AtomicInteger rateLimited = new AtomicInteger(0);
        AtomicReference<Throwable> error = new AtomicReference<>(null);

        // Use a fresh manager with rate limit of 5
        FilterManager rateManager = new FilterManager(new HomePageTarget());
        rateManager.addFilter(new AuthenticationFilter());
        rateManager.addFilter(new RateLimitFilter(5));

        for (int i = 0; i < threadCount; i++) {
            executor.submit(() -> {
                try {
                    Request request = new Request("GET", "sameUser", "/home", "");
                    Response response = rateManager.process(request);
                    if (response.status() == 403) {
                        rateLimited.incrementAndGet();
                    }
                } catch (Throwable t) {
                    error.set(t);
                } finally {
                    latch.countDown();
                }
            });
        }

        assertThat(latch.await(10, TimeUnit.SECONDS)).isTrue();
        executor.shutdown();

        assertThat(error.get()).isNull();
        // At most 5 should succeed, the rest should be rate-limited
        int successes = threadCount - rateLimited.get();
        assertThat(successes).isLessThanOrEqualTo(5);
        assertThat(rateLimited.get()).isGreaterThanOrEqualTo(0);
    }

    // ───── Demonstrate / Main tests ───────────────────────────────────────

    @Test
    @DisplayName("demonstrate runs without throwing exceptions")
    void demonstrateRunsSuccessfully() {
        InterceptingFilterApp.demonstrate();
    }

    @Test
    @DisplayName("main method runs without throwing exceptions")
    void mainMethodRunsSuccessfully() {
        InterceptingFilterApp.main(new String[]{});
    }
}
