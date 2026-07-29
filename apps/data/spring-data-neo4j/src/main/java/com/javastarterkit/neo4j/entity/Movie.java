// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.neo4j.entity;

import org.springframework.data.neo4j.core.schema.Id;
import org.springframework.data.neo4j.core.schema.Node;
import org.springframework.data.neo4j.core.schema.Property;

@Node("Movie")
public class Movie {

    @Id
    private String title;

    @Property("tagline")
    private String description;

    @Property("releaseYear")
    private int year;

    public Movie() {}

    public Movie(String title, String description, int year) {
        this.title = title;
        this.description = description;
        this.year = year;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public int getYear() {
        return year;
    }

    public void setYear(int year) {
        this.year = year;
    }
}
