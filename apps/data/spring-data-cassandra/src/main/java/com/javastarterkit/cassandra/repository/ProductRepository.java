package com.javastarterkit.cassandra.repository;

import java.util.List;

import org.springframework.data.cassandra.repository.CassandraRepository;
import org.springframework.stereotype.Repository;

import com.javastarterkit.cassandra.entity.Product;

@Repository
public interface ProductRepository extends CassandraRepository<Product, Long> {

    List<Product> findByCategory(String category);
}