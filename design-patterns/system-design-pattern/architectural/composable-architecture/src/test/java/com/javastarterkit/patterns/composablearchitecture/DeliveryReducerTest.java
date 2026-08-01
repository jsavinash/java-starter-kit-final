package com.javastarterkit.patterns.composablearchitecture;

import com.javastarterkit.patterns.composablearchitecture.ui.actions.DeliveryAction;
import com.javastarterkit.patterns.composablearchitecture.ui.models.DeliveryState;
import com.javastarterkit.patterns.composablearchitecture.ui.reducers.DeliveryReducer;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Unit tests for the delivery feature reducer.
 */
class DeliveryReducerTest {

    @Test
    @DisplayName("setName updates name")
    void setName_updatesName() {
        DeliveryState state = new DeliveryState();

        DeliveryState next = DeliveryReducer.INSTANCE.reduce(
                state, new DeliveryAction.setName("Alice"));

        assertThat(next.name()).isEqualTo("Alice");
    }

    @Test
    @DisplayName("setAddress updates address")
    void setAddress_updatesAddress() {
        DeliveryState state = new DeliveryState();

        DeliveryState next = DeliveryReducer.INSTANCE.reduce(
                state, new DeliveryAction.setAddress("123 Main St"));

        assertThat(next.address()).isEqualTo("123 Main St");
    }

    @Test
    @DisplayName("setCity updates city")
    void setCity_updatesCity() {
        DeliveryState state = new DeliveryState();

        DeliveryState next = DeliveryReducer.INSTANCE.reduce(
                state, new DeliveryAction.setCity("Springfield"));

        assertThat(next.city()).isEqualTo("Springfield");
    }

    @Test
    @DisplayName("setPhone updates phone")
    void setPhone_updatesPhone() {
        DeliveryState state = new DeliveryState();

        DeliveryState next = DeliveryReducer.INSTANCE.reduce(
                state, new DeliveryAction.setPhone("555-0100"));

        assertThat(next.phone()).isEqualTo("555-0100");
    }

    @Test
    @DisplayName("isComplete is false when any field is blank")
    void isComplete_falseWhenIncomplete() {
        DeliveryState incomplete = new DeliveryState("Alice", "123 Main St", "", "555-0100");

        assertThat(incomplete.isComplete()).isFalse();
    }

    @Test
    @DisplayName("isComplete is true when all fields are filled")
    void isComplete_trueWhenComplete() {
        DeliveryState complete = new DeliveryState("Alice", "123 Main St", "Springfield", "555-0100");

        assertThat(complete.isComplete()).isTrue();
    }
}