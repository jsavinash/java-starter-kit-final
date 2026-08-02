package com.javastarterkit.patterns.flux.actions;

import com.javastarterkit.patterns.flux.actions.Action;
import com.javastarterkit.patterns.flux.models.Filter;

/**
 * Sealed interface for filter-related actions.
 *
 * <p>This allows extending filter actions in the future without modifying
 * the core Action interface.
 */
public sealed interface FilterAction extends Action {

    /**
     * Action to set the current visibility filter.
     *
     * @param filter the filter to apply; must not be null
     */
    record Set(Filter filter) implements FilterAction {

        /**
         * Creates a new Set filter action.
         *
         * @param filter the filter; must not be null
         * @throws NullPointerException if filter is null
         */
        public Set {
            if (filter == null) {
                throw new NullPointerException("filter must not be null");
            }
        }
    }
}