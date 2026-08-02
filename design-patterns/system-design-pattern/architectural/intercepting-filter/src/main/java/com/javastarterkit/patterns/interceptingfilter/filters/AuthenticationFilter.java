package com.javastarterkit.patterns.interceptingfilter.filters;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * Filter that authenticates the request.
 *
 * <p>This filter checks if the user is authenticated. If not, it aborts
 * the filter chain and returns a 403 Forbidden response.
 */
public class AuthenticationFilter extends Filter {

    @Override
    public boolean before(Request request) {
        if (!request.isAuthenticated()) {
            System.out.println("  [AUTH] Rejecting unauthenticated request to " + request.path());
            return false;
        }
        System.out.println("  [AUTH] Authenticated user '" + request.user() + "'");
        return true;
    }
}