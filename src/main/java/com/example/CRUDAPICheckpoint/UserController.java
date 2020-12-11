package com.example.CRUDAPICheckpoint;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/users")
public class UserController {

    private final UserRepository repository;

    public UserController(UserRepository repository) { this.repository = repository; }

    @GetMapping("")
    @JsonView(Views.PublicView.class)
    public Iterable<User> all() { return this.repository.findAll(); }

    @GetMapping("/{id}")
    @JsonView(Views.PublicView.class)
    public User getUserById (@PathVariable Long id) {
        return this.repository.findById(id).get();
    }

    @PostMapping("")
    @JsonView(Views.PublicView.class)
    public User postNewUser (@RequestBody User newUser) {
        this.repository.save(newUser);
        return this.repository.findById(newUser.getId()).get();

    }
}
