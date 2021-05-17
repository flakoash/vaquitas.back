package com.example.vaquitasback.repository;

import com.example.vaquitasback.entity.User;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RestResource;

import java.util.List;

@RestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long>  {
    User findByUsername(String username);
    @Query(value = "SELECT u FROM User u WHERE REPLACE(REPLACE(REPLACE(REPLACE(u.phoneNumber, '(', ''), ')', ''), ' ', ''), '-', '') in :phoneNumbers")
    Iterable<User> findAllByPhoneNumberIn(@Param("phoneNumbers") List<String> phoneNumbers);
}
