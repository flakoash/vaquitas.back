package com.example.vaquitasback.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

/*
*
  id: "user_id", name: "User 1", photo: "https://i.pravatar.cc/50"
* */
@Data
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true) // JPA requires a non args constructor
@RequiredArgsConstructor // we still need an Args constructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private final long id;
    private final String name; // display name
    private final String username;
    @JsonProperty(access = JsonProperty.Access.WRITE_ONLY)
    private final String password;
    private final String phoneNumber;
    private final String photo;
}
