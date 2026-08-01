package com.javastarterkit.patterns.composablearchitecture.ui.models;

import com.javastarterkit.patterns.composablearchitecture.core.State;

import java.util.Objects;

/**
 * Immutable state for the delivery-details feature.
 *
 * <p>Holds the customer's contact and address information. The state is an
 * immutable record; every "withX" method returns a new instance (copy-on-write)
 * so it can be safely shared across threads.
 *
 * @param name    the customer's name (never {@code null})
 * @param address the street address (never {@code null})
 * @param city    the city (never {@code null})
 * @param phone   the phone number or email contact (never {@code null})
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record DeliveryState(String name, String address, String city, String phone)
        implements State {

    /** Default blank delivery details. */
    public DeliveryState() {
        this("", "", "", "");
    }

    /** Compact constructor enforces a non-null invariant. */
    public DeliveryState {
        name = Objects.requireNonNullElse(name, "");
        address = Objects.requireNonNullElse(address, "");
        city = Objects.requireNonNullElse(city, "");
        phone = Objects.requireNonNullElse(phone, "");
    }

    /**
     * Returns a copy with a new name.
     *
     * @param newName the new name
     * @return a new state
     */
    public DeliveryState withName(String newName) {
        return new DeliveryState(newName, address, city, phone);
    }

    /**
     * Returns a copy with a new address.
     *
     * @param newAddress the new address
     * @return a new state
     */
    public DeliveryState withAddress(String newAddress) {
        return new DeliveryState(name, newAddress, city, phone);
    }

    /**
     * Returns a copy with a new city.
     *
     * @param newCity the new city
     * @return a new state
     */
    public DeliveryState withCity(String newCity) {
        return new DeliveryState(name, address, newCity, phone);
    }

    /**
     * Returns a copy with a new phone/contact.
     *
     * @param newPhone the new phone/contact
     * @return a new state
     */
    public DeliveryState withPhone(String newPhone) {
        return new DeliveryState(name, address, city, newPhone);
    }

    /**
     * @return whether the delivery details are complete enough to place an order
     */
    public boolean isComplete() {
        return !name.isBlank() && !address.isBlank() && !city.isBlank() && !phone.isBlank();
    }
}