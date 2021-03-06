package com.interviewtask.springbootsimpleecommerce.repositories;

import com.interviewtask.springbootsimpleecommerce.model.Product;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

/*
 * Product Collection.
 */
@Repository
public interface IProductRepository extends MongoRepository<Product, String> {
}
