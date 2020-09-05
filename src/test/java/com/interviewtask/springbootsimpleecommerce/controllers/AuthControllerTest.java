package com.interviewtask.springbootsimpleecommerce.controllers;

import com.interviewtask.springbootsimpleecommerce.model.SimpleUser;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.data.mongodb.core.MongoTemplate;

import static org.assertj.core.api.Assertions.assertThat;

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
    public void testRegisterUser() {
        mongoTemplate.dropCollection("simpleUser");
        SimpleUser testUser = new SimpleUser("testUser2", "123");
        this.restTemplate.postForEntity("http://localhost:" + port + "/api/auth/signup", testUser, String.class)
                .getStatusCode();
    }

}
