package com.example.vaquitasback.entity;


import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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
@JsonIgnoreProperties(value = { "password" })
@NoArgsConstructor(access= AccessLevel.PROTECTED, force = true) // JPA requires a non args constructor
@RequiredArgsConstructor // we still need an Args constructor
@Entity
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private final String id;
    private final String name; // display name
    private final String username;
    private final String password;
    private final String phoneNumber;
    private final String photo;
}
