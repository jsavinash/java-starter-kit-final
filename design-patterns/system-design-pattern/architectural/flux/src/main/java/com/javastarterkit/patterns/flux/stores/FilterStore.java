package com.javastarterkit.patterns.flux.stores;

import com.javastarterkit.patterns.flux.actions.FilterAction;
import com.javastarterkit.patterns.flux.actions.Action;
import com.javastarterkit.patterns.flux.core.Store;
import com.javastarterkit.patterns.flux.models.Filter;
import com.javastarterkit.patterns.flux.models.FilterState;

/**
 * Store that manages the visibility filter state.
 *
 * <p>Handles filter-related actions and maintains an immutable {@link FilterState}.
 */
public final class FilterStore extends Store<FilterState> {

    /**
     * Creates a new FilterStore with the default filter (ALL).
     */
    public FilterStore() {
        super(new FilterState(Filter.ALL));
    }

    @Override
    public void onAction(final Action action) {
        if (action instanceof FilterAction.Set set) {
            setState(getState().withFilter(set.filter()));
        }
    }
}