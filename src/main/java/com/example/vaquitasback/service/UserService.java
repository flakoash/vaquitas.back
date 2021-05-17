package com.example.vaquitasback.service;

import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class UserService implements UserServiceInterface{

    private final UserRepository repository;

    public UserService(UserRepository repository){
        this.repository=repository;
    }

    @Override
    public Iterable<User> findByPhone(List<String> phones) {
        List<String> cleanedPhones = phones.stream().map(phone->phone.replace("(", "")
                .replace(")", "")
                .replace("-", "")
                .replace(" ", "")
                .replace("+", "")).collect(Collectors.toList());
        return repository.findAllByPhoneNumberIn(cleanedPhones);
    }
}
