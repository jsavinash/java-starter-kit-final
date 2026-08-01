package com.javastarterkit.patterns.composablearchitecture.ui.reducers;

import com.javastarterkit.patterns.composablearchitecture.core.Reducer;
import com.javastarterkit.patterns.composablearchitecture.core.component.Component;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.DeliveryAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.OrderAction;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.PizzaAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.DeliveryState;
import com.javastarterkit.patterns.composablearchitecture.ui.models.OrderState;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaState;

/**
 * Composed parent reducer for the order feature.
 *
 * <p>This reducer is the concrete application of the central composition
 * primitive {@link Reducer#pullback}: it lifts the independent
 * {@link PizzaReducer} and {@link DeliveryReducer} (wrapped in their
 * {@link Component}s) into the parent {@link OrderState} / {@link OrderAction}
 * space and combines them so each action is routed to the correct child.
 *
 * <p>The composed reducer is stateless and immutable, hence inherently
 * thread-safe and sharedable across stores.
 */
public final class OrderReducer {

    private OrderReducer() {
        // Prevent instantiation of a pure-functional composition holder.
    }

    /** The immutable pizza feature component. */
    private static final Component<PizzaState, PizzaAction> PIZZA_COMPONENT =
            Component.of(PizzaState::new, PizzaState.class, PizzaReducer.INSTANCE);

    /** The immutable delivery feature component. */
    private static final Component<DeliveryState, DeliveryAction> DELIVERY_COMPONENT =
            Component.of(DeliveryState::new, DeliveryState.class, DeliveryReducer.INSTANCE);

    /**
     * Returns the fully composed reducer for the whole order feature.
     *
     * @return the immutable composed parent reducer
     */
    public static Reducer<OrderState, OrderAction> composed() {
        return Reducer.combine(
                Reducer.pullback(
                        PIZZA_COMPONENT,
                        OrderState::pizza,
                        OrderState::withPizza,
                        action -> action instanceof OrderAction.Pizza p ? p.action() : null),
                Reducer.pullback(
                        DELIVERY_COMPONENT,
                        OrderState::delivery,
                        OrderState::withDelivery,
                        action -> action instanceof OrderAction.Delivery d ? d.action() : null));
    }
}