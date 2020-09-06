package com.interviewtask.springbootsimpleecommerce.controllers;

import com.interviewtask.springbootsimpleecommerce.model.SimpleUser;
import com.interviewtask.springbootsimpleecommerce.response.JwtResponse;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT,
        properties = {
                "spring.data.mongodb.database=testdb"
        })
public class AuthControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private MongoTemplate mongoTemplate;

    @Test
    public void testRegisterAndSignUser() {
        mongoTemplate.dropCollection("simpleUser");
        SimpleUser testUser = new SimpleUser("testUser", "123");
        SimpleUser testUser2 = new SimpleUser("testUser2", "123");
        ResponseEntity<String> stringResponseEntity = this.restTemplate.postForEntity("http://localhost:" + port +
                "/api/auth/signup", testUser, String.class);
        assertEquals(HttpStatus.OK, stringResponseEntity.getStatusCode());

        testUser = new SimpleUser("testUser", "123");
        stringResponseEntity = this.restTemplate.postForEntity("http://localhost:" + port +
                "/api/auth/signup", testUser, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());

        ResponseEntity<JwtResponse> jwtResponseEntity = this.restTemplate.postForEntity("http://localhost:" + port +
                "/api/auth/signin", testUser, JwtResponse.class);
        assertEquals("testUser", Objects.requireNonNull(jwtResponseEntity.getBody()).getUsername());
        assertNotNull(Objects.requireNonNull(jwtResponseEntity.getBody()).getToken());

        stringResponseEntity = this.restTemplate.postForEntity("http://localhost:" + port +
                "/api/auth/signin", testUser2, String.class);
        assertEquals(HttpStatus.BAD_REQUEST, stringResponseEntity.getStatusCode());

    }
}
