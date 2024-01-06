package com.example.restaurant.controllers;

import com.example.restaurant.models.User;
import com.example.restaurant.entity.AuthenticationResponse;
import com.example.restaurant.entity.LoginForm;
import com.example.restaurant.entity.RegistrationForm;
import com.example.restaurant.entity.SimpleResponse;
import com.example.restaurant.repos.UserRepository;
import com.example.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/vi/auth") // This means URL's start with /demo (after Application path)
//@Validated
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authService;
    @Autowired
    private UserRepository userRepository;

    @PostMapping(path = "/signup") // Map ONLY POST Requests
    @ResponseStatus(HttpStatus.CREATED)
    public @ResponseBody ResponseEntity<SimpleResponse> addNewUser(RegistrationForm form) {
        return ResponseEntity.ok(authService.signup(form));
    }

    @GetMapping(path = "/login")
    public @ResponseBody ResponseEntity<AuthenticationResponse> login(LoginForm form) {
        return ResponseEntity.ok(authService.login(form));
    }

    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody Iterable<User> getAllUsers() {
        return userRepository.findAll();
    }
}