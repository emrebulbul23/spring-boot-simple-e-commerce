package com.interviewtask.springbootsimpleecommerce.services;

import com.interviewtask.springbootsimpleecommerce.model.SimpleUser;
import com.interviewtask.springbootsimpleecommerce.repositories.IUserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;

@Component
public class MongoUserDetailsService implements UserDetailsService {
    /**
     * Autowires the collection to db.
     */
    @Autowired
    private IUserRepository repository;

    /**
     * Checks if User is present in the db.
     * Returns a Spring User object with a Role.
     *
     * @param username Username
     * @return UserDetails
     * @throws UsernameNotFoundException exception
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        SimpleUser user = repository.findByUsername(username);
        if(user == null) {
            throw new UsernameNotFoundException("User not found");
        }
        List<SimpleGrantedAuthority> authorities = Arrays.asList(new SimpleGrantedAuthority("user"));
        return new User(user.getUsername(), user.getPassword(),authorities);
    }
}
