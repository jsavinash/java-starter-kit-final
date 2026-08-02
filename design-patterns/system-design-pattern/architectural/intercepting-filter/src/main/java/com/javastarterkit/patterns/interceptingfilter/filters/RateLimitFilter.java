package com.javastarterkit.patterns.interceptingfilter.filters;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * Filter that limits the request rate per user.
 *
 * <p>This filter tracks the number of requests per user and aborts the chain
 * when the limit is exceeded. It is thread-safe and uses ConcurrentHashMap
 * for concurrent access.
 */
public class RateLimitFilter extends Filter {
    private final int maxRequests;
    private final ConcurrentMap<String, AtomicInteger> counts = new ConcurrentHashMap<>();

    /**
     * Creates a new RateLimitFilter with the specified maximum requests.
     *
     * @param maxRequests the maximum number of requests allowed
     */
    public RateLimitFilter(int maxRequests) {
        this.maxRequests = maxRequests;
    }

    @Override
    public boolean before(Request request) {
        String key = request.user();
        AtomicInteger count = counts.computeIfAbsent(key, k -> new AtomicInteger(0));
        int current = count.incrementAndGet();
        
        if (current > maxRequests) {
            System.out.println("  [RATE] User '" + key + "' exceeded limit of " + maxRequests);
            return false;
        }
        
        System.out.println("  [RATE] User '" + key + "' request " + current + "/" + maxRequests);
        return true;
    }
}