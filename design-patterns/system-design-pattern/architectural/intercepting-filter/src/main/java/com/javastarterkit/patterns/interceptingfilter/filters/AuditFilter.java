package com.javastarterkit.patterns.interceptingfilter.filters;

import com.javastarterkit.patterns.interceptingfilter.core.Filter;
import com.javastarterkit.patterns.interceptingfilter.models.Request;
import com.javastarterkit.patterns.interceptingfilter.models.Response;

/**
 * Filter that audits requests after processing.
 */
public class AuditFilter extends Filter {

    @Override
    public boolean before(Request request) {
        return true;
    }

    @Override
    public void after(Request request, Response response) {
        System.out.println("  [AUDIT] Recorded " + request.method() + " " + request.path()
            + " for user '" + request.user() + "' (status=" + response.status() + ")");
    }
}
