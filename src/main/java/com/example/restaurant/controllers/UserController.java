package com.example.restaurant.controllers;

import com.example.restaurant.Domain.User;
import com.example.restaurant.repos.UserRepository;
import jakarta.validation.constraints.NotBlank;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/vi/auth") // This means URL's start with /demo (after Application path)
@Validated
public class UserController {
    @Autowired // This means to get the bean called userRepository
    // Which is auto-generated by Spring, we will use it to handle the data
    private UserRepository userRepository;

    @PostMapping(path = "/signup") // Map ONLY POST Requests
    public @ResponseBody String addNewUser(@RequestParam @NotBlank(message = "You must input login") String login
            , @RequestParam @NotBlank(message = "You must input password") String password) {
        // @ResponseBody means the returned String is the response, not a view name
        // @RequestParam means it is a parameter from the GET or POST request
        User user_from_db = userRepository.findByLogin(login);
        if (user_from_db != null) return "Уже есть такой логин";
        User n = new User(login, password, System.currentTimeMillis());
        userRepository.save(n);
        return "Saved";
    }
    @GetMapping(path = "/login")
    public @ResponseBody Iterable<User> login() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }

    @GetMapping(path = "/all")
    public @ResponseBody Iterable<User> getAllUsers() {
        // This returns a JSON or XML with the users
        return userRepository.findAll();
    }
}