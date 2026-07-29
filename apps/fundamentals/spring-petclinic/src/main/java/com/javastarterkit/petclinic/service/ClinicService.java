// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.petclinic.service;

import com.javastarterkit.petclinic.model.Owner;
import com.javastarterkit.petclinic.model.Pet;
import com.javastarterkit.petclinic.model.PetType;
import com.javastarterkit.petclinic.repository.OwnerRepository;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;

@Service
public class ClinicService {

    private final OwnerRepository ownerRepository;

    public ClinicService(OwnerRepository ownerRepository) {
        this.ownerRepository = ownerRepository;
    }

    public List<Owner> findAllOwners() {
        return ownerRepository.findAllByOrderByLastNameAsc();
    }

    public Optional<Owner> findOwnerById(Long id) {
        return ownerRepository.findById(id);
    }

    public List<Owner> findOwnersByLastName(String lastName) {
        return ownerRepository.findByLastName(lastName);
    }

    public Owner saveOwner(Owner owner) {
        return ownerRepository.save(owner);
    }

    public PetType savePetType(PetType petType) {
        return null; // Would use PetTypeRepository in full implementation
    }

    public List<PetType> findAllPetTypes() {
        return null; // Would use PetTypeRepository in full implementation
    }

    public Pet savePet(Pet pet) {
        return null; // Would use PetRepository in full implementation
    }
}
