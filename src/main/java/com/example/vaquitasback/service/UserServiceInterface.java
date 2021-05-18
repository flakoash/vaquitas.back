package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.User;

import java.util.List;

public interface UserServiceInterface {
    Iterable<User> findByPhone(List<String> phones);
    Iterable<User> getAll();
}
