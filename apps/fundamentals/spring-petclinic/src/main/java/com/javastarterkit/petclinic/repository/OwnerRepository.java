package com.javastarterkit.petclinic.repository;

import com.javastarterkit.petclinic.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByLastName(String lastName);

    List<Owner> findAllByOrderByLastNameAsc();
}