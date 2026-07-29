// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.petclinic.repository;

import com.javastarterkit.petclinic.model.Owner;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface OwnerRepository extends JpaRepository<Owner, Long> {

    List<Owner> findByLastName(String lastName);

    List<Owner> findAllByOrderByLastNameAsc();
}
