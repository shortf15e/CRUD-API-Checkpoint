package com.example.CRUDAPICheckpoint;


import com.fasterxml.jackson.annotation.JsonView;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;

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

    @PatchMapping("/{id}")
    @JsonView(Views.PublicView.class)
    public User updateUserById (@PathVariable Long id, @RequestBody User updateUser) {

        if (this.repository.findById(id).isPresent()) {
            User currentUser = this.repository.findById(id).get();
            if (!updateUser.getEmail().isEmpty()) {
                String newEmail = updateUser.getEmail();
                currentUser.setEmail(newEmail);
            }
            if (!updateUser.getPassword().isEmpty()) {
                String newPassword = updateUser.getPassword();
                currentUser.setPassword(newPassword);
            }
            this.repository.save(currentUser);
        }

        return this.repository.findById(id).get();
    }

    @DeleteMapping("/{id}")
    public HashMap<String, Long> deleteUserById (@PathVariable Long id) {
        this.repository.deleteById(id);
        HashMap<String, Long> result = new HashMap<>();
        Long count = this.repository.count();
        result.put("count", count);
        return result;
    }

    @PostMapping("/authenticate")
    @JsonView(Views.PublicView.class)
    public HashMap<String, Object> authenticateUser (@RequestBody User checkUser) {
        HashMap<String, Object> result = new HashMap<>();
        String checkEmail = checkUser.getEmail();
        String checkPassword = checkUser.getPassword();
        User userToBeChecked = this.repository.findByEmail(checkEmail);
        boolean authenticated = userToBeChecked.getPassword().equals(checkPassword);
        result.put("authenticated", authenticated);
        if (authenticated) {result.put("user", userToBeChecked);}
        return result;
    }


}
