package com.example.vaquitasback.web;

import com.example.vaquitasback.entity.User;
import com.example.vaquitasback.repository.UserRepository;
import com.example.vaquitasback.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
    @RequestMapping("/api/user")
public class UserController {
    private final UserRepository repository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final UserService userService;


    Logger logger = LoggerFactory.getLogger(UserController.class);

    public UserController(UserRepository repository,
                          BCryptPasswordEncoder bCryptPasswordEncoder, UserService userService) {
        this.repository = repository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
        this.userService = userService;
    }

    @PostMapping("/singup")
    public User signUp(@RequestBody User user) {
        logger.warn(user.getName());
        user.setPassword(bCryptPasswordEncoder.encode(user.getPassword()));
        return repository.save(user);
    }

    @PostMapping("/findByPhone")
    public Iterable<User> findByPhone(@RequestBody List<String> phones){
        return this.userService.findByPhone(phones);
    }

    @GetMapping
    public Iterable<User> getAllUsers(){
        return this.userService.getAll();
    }
}
