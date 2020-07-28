package com.jonata.api.repositories;

import java.util.List;

import com.jonata.api.model.Product;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface ProductRepository extends MongoRepository<Product, String> {

    Product findByNameIgnoreCase(String name);

    List<Product> findAllByOrderByNameAsc();
}