package com.example.restaurant.controllers;

import com.example.restaurant.entity.AuthenticationResponse;
import com.example.restaurant.entity.LoginForm;
import com.example.restaurant.entity.RegistrationForm;
import com.example.restaurant.entity.SimpleResponse;
import com.example.restaurant.models.User;
import com.example.restaurant.service.AdminService;
import com.example.restaurant.service.AuthenticationService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/admin") // This means URL's start with /demo (after Application path)
//@Validated
@RequiredArgsConstructor
public class AdminController {
    private final AdminService adminService;

    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('ADMIN')")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Page<User>> getAllUsers(int offset, int size) {
        return ResponseEntity.ok(adminService.findAll(offset, size));
    }
}