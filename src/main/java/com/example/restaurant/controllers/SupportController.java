package com.example.restaurant.controllers;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.User;
import com.example.restaurant.service.AdminService;
import com.example.restaurant.service.SupportService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/support") // This means URL's start with /demo (after Application path)
//@Validated
@RequiredArgsConstructor
public class SupportController {
    private final SupportService supportService;
    @GetMapping(path = "/all")
    @PreAuthorize("hasRole('SUPPORT')")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Page<Restaurant>> getAllUsers(HttpServletRequest request, int offset, int size) {
        return ResponseEntity.ok(supportService.findAll(request, offset, size));
    }
}