package com.interviewtask.springbootsimpleecommerce.controllers;

import com.interviewtask.springbootsimpleecommerce.model.Product;
import com.interviewtask.springbootsimpleecommerce.repositories.IProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.text.MessageFormat;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@RestController
@RequestMapping("product")
public class ProductController {
    private final Logger logger = LoggerFactory.getLogger(getClass());

    private final IProductRepository productRepository;

    public ProductController(IProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Operation(summary = "Get products in given category")
    @GetMapping(value = "")
    public List<Product> getProductsWithCategory(String cat) {
        logger.info(MessageFormat.format("Getting products with category {0}", cat));
        return productRepository.findAll().stream().filter(product -> product.getCategory().equals(cat))
                .collect(Collectors.toList());
    }

    @Operation(summary = "Put new product")
    @PutMapping(value = "")
    public Product putProduct(@RequestBody Product p) {
        //check if same product already exists
        logger.info(MessageFormat.format("Putting product {0}", p.toString()));
        return productRepository.save(p);
    }

    @Operation(summary = "Update the first product with given properties.")
    @PostMapping(value = "")
    public Product updateProduct(@RequestBody Product p) {
        List<Product> collect = productRepository.findAll().stream().filter(product -> product.getCategory().equals(p.getCategory()) &&
                product.getName().equals(p.getName()) &&
                product.getBrand().equals(p.getBrand()))
                .collect(Collectors.toList());
        if (collect.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product to be updated is not found");
        }
        p.setId(collect.get(0).getId());
        return productRepository.save(p);
    }


    @Operation(summary = "Delete the product with given id.")
    @DeleteMapping(value = "{productId}")
    public String deleteProduct(@PathVariable String productId) {
        Optional<Product> byId = productRepository.findById(productId);
        if (byId.isPresent()) {
            productRepository.delete(byId.get());
            return productId;
        }
        throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product to be deleted is not found");
    }
}
