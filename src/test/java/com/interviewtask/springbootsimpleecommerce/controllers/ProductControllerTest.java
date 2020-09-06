package com.interviewtask.springbootsimpleecommerce.controllers;

import com.interviewtask.springbootsimpleecommerce.model.Product;
import com.interviewtask.springbootsimpleecommerce.model.SimpleUser;
import com.interviewtask.springbootsimpleecommerce.response.JwtResponse;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityAutoConfiguration;
import org.springframework.boot.autoconfigure.security.servlet.SecurityFilterAutoConfiguration;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.*;

import java.util.List;
import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.data.mongodb.database=testdb"
        })
public class ProductControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    private HttpHeaders headers = new HttpHeaders();

    @BeforeEach
    void setUp() {
        mongoTemplate.dropCollection("simpleUser");
        SimpleUser testUser = new SimpleUser("testUser", "123");
        SimpleUser testUser2 = new SimpleUser("testUser2", "123");
        this.restTemplate.postForEntity("http://localhost:" + port +
                "/api/auth/signup", testUser, String.class);

        ResponseEntity<JwtResponse> jwtResponseEntity = this.restTemplate.postForEntity("http://localhost:" + port +
                "/api/auth/signin", testUser, JwtResponse.class);
        headers.add("Authorization", "Bearer " +
                Objects.requireNonNull(jwtResponseEntity.getBody()).getToken());
    }

    @AfterEach
    void tearDown() {
        mongoTemplate.dropCollection("product");
    }

    @Test
    public void testGetProduct() {
        addProduct("p1", "c1");
        addProduct("p2", "c1");
        addProduct("p3", "c2");

        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<List<Product>> resp = this.restTemplate.exchange(
                "http://localhost:" + port + "/product?cat=c1",
                HttpMethod.GET,
                request,
                new ParameterizedTypeReference<List<Product>>() {
                });
        List<Product> body = resp.getBody();
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("c1", Objects.requireNonNull(body).get(0).getCategory());
    }

    @Test
    public void testDeleteProduct() {
        String id = addProduct("p1", "c1");

        HttpEntity<String> request = new HttpEntity<>(null, headers);
        ResponseEntity<String> resp = this.restTemplate.exchange(
                "http://localhost:" + port + "/product/" + id,
                HttpMethod.DELETE,
                request,
                String.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals(id, resp.getBody());
    }

    @Test
    public void testUpdateProduct() {
        String id = addProduct("p1", "c1");

        Product product = new Product("p12", "c1", 20, "b", "seller", "s");
        product.setId(id);
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        ResponseEntity<Product> resp = this.restTemplate.exchange(
                "http://localhost:" + port + "/product",
                HttpMethod.POST,
                request,
                Product.class);
        assertEquals(HttpStatus.OK, resp.getStatusCode());
        assertEquals("p12", Objects.requireNonNull(resp.getBody()).getName());
        assertEquals("c1", Objects.requireNonNull(resp.getBody()).getCategory());
        assertEquals("b", Objects.requireNonNull(resp.getBody()).getBrand());
        assertEquals("seller", Objects.requireNonNull(resp.getBody()).getSeller());
        assertEquals("s", Objects.requireNonNull(resp.getBody()).getSize());
        assertEquals(20, Objects.requireNonNull(resp.getBody()).getPrice());
    }

    private String addProduct(String productName, String productCategory) {
        Product product = new Product(productName, productCategory, 20, "b", "seller", "s");
        HttpEntity<Product> request = new HttpEntity<>(product, headers);
        ResponseEntity<Product> exchange = this.restTemplate.exchange("http://localhost:" + port + "/product",
                HttpMethod.PUT, request, Product.class);
        return Objects.requireNonNull(exchange.getBody()).getId();
    }
}
