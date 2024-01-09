package com.example.restaurant.controllers;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.User;
import com.example.restaurant.repos.UserRepository;
import com.example.restaurant.service.JwtService;
import com.example.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;


@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/owner/restaurant") // This means URL's start with /demo (after Application path)
@RequiredArgsConstructor
public class RestaurantForOwnerController {
    private final RestaurantService restaurantService;
    @GetMapping(path = "/index")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<List<Restaurant>> getAllOwnersRestaurants(HttpServletRequest request) {
        return ResponseEntity.ok(restaurantService.getAllOwnersReastaurants(request));
    }

    @GetMapping(path = "/view/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<Restaurant> getViewIfOwner(HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getViewIfOwner(request, id));
    }
}