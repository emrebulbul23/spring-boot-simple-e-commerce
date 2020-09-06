package com.interviewtask.springbootsimpleecommerce.controllers;

import com.interviewtask.springbootsimpleecommerce.model.Product;
import com.interviewtask.springbootsimpleecommerce.repositories.IProductRepository;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
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

    /**
     * Returns products with given category.
     * @param cat category
     * @return List<Product>
     */
    @Operation(summary = "Get products in given category", security = @SecurityRequirement(name = "bearerAuth"))
    @GetMapping(value = "")
    public List<Product> getProductsWithCategory(String cat) {
        logger.info(MessageFormat.format("Getting products with category {0}", cat));
        return productRepository.findAll().stream().filter(product -> product.getCategory().equals(cat))
                .collect(Collectors.toList());
    }

    /**
     * Insert new product to db.
     * @param p Product
     * @return Inserted Product
     */
    @Operation(summary = "Put new product", security = @SecurityRequirement(name = "bearerAuth"))
    @PutMapping(value = "")
    public Product putProduct(@RequestBody Product p) {
        //check if same product already exists
        List<Product> collect = productRepository.findAll().stream().filter(product -> product.getCategory().equals(p.getCategory()) &&
                product.getName().equals(p.getName()) &&
                product.getBrand().equals(p.getBrand()))
                .collect(Collectors.toList());
        if (!collect.isEmpty()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product already in db.");
        }
        logger.info(MessageFormat.format("Putting product {0}", p.toString()));
        return productRepository.save(p);
    }

    /**
     * Updates given product. If the product does not exist, throws an exception.
     * @param p Product
     * @return Updated Product
     */
    @Operation(summary = "Update the first product with given properties.",
            security = @SecurityRequirement(name = "bearerAuth"))
    @PostMapping(value = "")
    public Product updateProduct(@RequestBody Product p) {
        Optional<Product> byId = productRepository.findById(p.getId());
        if (!byId.isPresent()) {
            throw new ResponseStatusException(HttpStatus.NOT_FOUND, "Product to be updated is not found");
        }
        Product pOld = byId.get();
        p.setId(pOld.getId());
        return productRepository.save(p);
    }

    /**
     * Deletes the product with the given id from the collection.
     * Throws an exception if product with given id is not found.
     * @param productId productId
     * @return productId of deleted Product
     */
    @Operation(summary = "Delete the product with given id.", security = @SecurityRequirement(name = "bearerAuth"))
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
