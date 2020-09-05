package com.interviewtask.springbootsimpleecommerce.repositories;

import com.interviewtask.springbootsimpleecommerce.model.SimpleUser;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface IUserRepository extends MongoRepository<SimpleUser, String> {
    /**
     * Gets the user by username from MongoDB instance.
     * @param username name of SimpleUser
     * @return SimpleUser object
     */
    SimpleUser findByUsername(String username);
}
