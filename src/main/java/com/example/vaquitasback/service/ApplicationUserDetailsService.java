package com.example.vaquitasback.service;

import org.springframework.security.core.userdetails.User;
import com.example.vaquitasback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import static java.util.Collections.emptyList;

@Service
public class ApplicationUserDetailsService implements UserDetailsService {
    @Autowired
    private UserRepository repository;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        com.example.vaquitasback.entity.User user = repository.findByUsername(username);
        if(user == null)
            throw new UsernameNotFoundException(username);

        return new User(user.getUsername(), user.getPassword(), emptyList());
    }
}
