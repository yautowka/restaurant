package com.example.restaurant.controllers;

import com.example.restaurant.models.User;
import com.example.restaurant.entity.AuthenticationResponse;
import com.example.restaurant.entity.LoginForm;
import com.example.restaurant.entity.RegistrationForm;
import com.example.restaurant.entity.SimpleResponse;
import com.example.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;



@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/auth") // This means URL's start with /demo (after Application path)
//@Validated
@RequiredArgsConstructor
public class UserController {
    private final AuthenticationService authService;
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
    public @ResponseBody ResponseEntity<Page<User>> getAllUsers(int offset, int size) {
        return ResponseEntity.ok(authService.findAll(offset, size));
    }
}