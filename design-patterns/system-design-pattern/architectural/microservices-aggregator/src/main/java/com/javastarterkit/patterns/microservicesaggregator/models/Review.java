package com.javastarterkit.patterns.microservicesaggregator.models;

import java.util.Objects;

/**
 * Immutable review data returned by the review microservice.
 *
 * <p>This record is inherently thread-safe and represents a single customer
 * review: author, rating (1-5), and comment.
 *
 * @param author  the reviewer's name
 * @param rating  the rating from 1 to 5
 * @param comment the review comment
 * @author Java Starter Kit
 * @version 1.0.0
 */
public record Review(String author, int rating, String comment) {

    /**
     * Compact constructor validating all fields and the rating range.
     */
    public Review {
        Objects.requireNonNull(author, "Author must not be null");
        Objects.requireNonNull(comment, "Comment must not be null");
        if (rating < 1 || rating > 5) {
            throw new IllegalArgumentException("Rating must be between 1 and 5");
        }
    }
}