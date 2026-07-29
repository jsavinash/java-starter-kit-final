// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.cassandra.repository;

import com.javastarterkit.cassandra.entity.Product;
import java.util.List;
import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {

    List<Product> findByCategory(String category);
}
