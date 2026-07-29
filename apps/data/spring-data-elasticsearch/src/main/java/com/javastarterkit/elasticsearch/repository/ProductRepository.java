// Copyright © 2026 Java Starter Kit. All rights reserved.
package com.javastarterkit.elasticsearch.repository;

import com.javastarterkit.elasticsearch.entity.ProductDocument;
import java.util.List;
import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends ElasticsearchRepository<ProductDocument, String> {

    List<ProductDocument> findByCategory(String category);

    List<ProductDocument> findByNameContaining(String name);
}
