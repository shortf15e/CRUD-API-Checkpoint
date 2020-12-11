package com.example.CRUDAPICheckpoint;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) { this.repository = repository; }

    @GetMapping("")
    public Iterable<User> all() { return this.repository.findAll(); }
}
