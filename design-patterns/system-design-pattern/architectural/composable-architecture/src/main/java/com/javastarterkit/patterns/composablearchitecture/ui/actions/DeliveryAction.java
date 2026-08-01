package com.javastarterkit.patterns.composablearchitecture.ui.actions;

import com.javastarterkit.patterns.composablearchitecture.core.Action;

/**
 * Sealed hierarchy of all events that can change the delivery-details feature
 * state. Each variant is an immutable record carrying the data needed by the
 * reducer.
 *
 * <p>The interface is sealed so the compiler guarantees exhaustive handling in
 * switch expressions.
 *
 * @author Java Starter Kit
 * @version 1.0.0
 */
public sealed interface DeliveryAction extends Action {

    /**
     * Set the customer's name.
     *
     * @param name the new name
     */
    record setName(String name) implements DeliveryAction {}

    /**
     * Set the delivery address.
     *
     * @param address the new address
     */
    record setAddress(String address) implements DeliveryAction {}

    /**
     * Set the delivery city.
     *
     * @param city the new city
     */
    record setCity(String city) implements DeliveryAction {}

    /**
     * Set the phone/contact.
     *
     * @param phone the new phone/contact
     */
    record setPhone(String phone) implements DeliveryAction {}
}