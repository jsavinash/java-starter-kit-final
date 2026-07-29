// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.petclinic.model;

import jakarta.persistence.*;
import java.util.Set;

@Entity
@Table(name = "pet_types")
public class PetType {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name")
    private String name;

    @OneToMany(mappedBy = "type")
    private Set<Pet> pets;

    // Constructors
    public PetType() {}

    public PetType(String name) {
        this.name = name;
    }

    // Getters and Setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
