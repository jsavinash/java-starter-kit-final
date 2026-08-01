package com.javastarterkit.patterns.composablearchitecture;

import com.javastarterkit.patterns.composablearchitecture.exception.InvalidPizzaException;
import com.javastarterkit.patterns.composablearchitecture.ui.actions.PizzaAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaSize;
import com.javastarterkit.patterns.composablearchitecture.ui.models.PizzaState;
import com.javastarterkit.patterns.composablearchitecture.ui.models.Topping;
import com.javastarterkit.patterns.composablearchitecture.ui.reducers.PizzaReducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Set;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;

/**
 * Unit tests for the pizza feature reducer.
 */
class PizzaReducerTest {

    @Test
    @DisplayName("selectSize changes the size and keeps other fields")
    void selectSize_updatesSize() {
        PizzaState state = new PizzaState();

        PizzaState next = PizzaReducer.INSTANCE.reduce(
                state, new PizzaAction.selectSize(PizzaSize.LARGE));

        assertThat(next.size()).isEqualTo(PizzaSize.LARGE);
        assertThat(next.quantity()).isEqualTo(1);
        assertThat(next.toppings()).containsExactly(Topping.CHEESE);
    }

    @Test
    @DisplayName("toggleTopping adds a topping when absent")
    void toggleTopping_addsTopping() {
        PizzaState state = new PizzaState();

        PizzaState next = PizzaReducer.INSTANCE.reduce(
                state, new PizzaAction.toggleTopping(Topping.PEPPERONI));

        assertThat(next.toppings()).contains(Topping.CHEESE, Topping.PEPPERONI);
    }

    @Test
    @DisplayName("toggleTopping removes a topping when present")
    void toggleTopping_removesTopping() {
        PizzaState state = new PizzaState().withToppingToggled(Topping.PEPPERONI);

        PizzaState next = PizzaReducer.INSTANCE.reduce(
                state, new PizzaAction.toggleTopping(Topping.PEPPERONI));

        assertThat(next.toppings()).doesNotContain(Topping.PEPPERONI);
    }

    @Test
    @DisplayName("setQuantity updates quantity")
    void setQuantity_updatesQuantity() {
        PizzaState state = new PizzaState();

        PizzaState next = PizzaReducer.INSTANCE.reduce(
                state, new PizzaAction.setQuantity(3));

        assertThat(next.quantity()).isEqualTo(3);
    }

    @Test
    @DisplayName("setQuantity with zero throws InvalidPizzaException")
    void setQuantity_zero_throws() {
        PizzaState state = new PizzaState();

        assertThatThrownBy(() -> PizzaReducer.INSTANCE.reduce(
                state, new PizzaAction.setQuantity(0)))
                .isInstanceOf(InvalidPizzaException.class)
                .hasMessageContaining(">= 1");
    }

    @Test
    @DisplayName("totalPrice accounts for size, toppings, and quantity")
    void totalPrice_isCalculated() {
        // Large base 13.0 + pepperoni 1.5 -> 14.5 per pizza, quantity 2 -> 29.0
        PizzaState state = new PizzaState(PizzaSize.LARGE, Set.of(Topping.PEPPERONI), 2);

        assertThat(state.totalPrice()).isEqualTo(29.0);
    }
}