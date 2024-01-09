package com.example.restaurant.controllers;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.service.RestaurantService;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/user/restaurant") // This means URL's start with /demo (after Application path)
@RequiredArgsConstructor
public class RestaurantForUserController {
    private final RestaurantService restaurantService;
    @GetMapping(path = "/index")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Page<Restaurant>> getAllUsers(int offset, int size) {
        return ResponseEntity.ok(restaurantService.findAll(offset, size));
    }
    @GetMapping(path = "/view/{id}")
    @ResponseStatus(HttpStatus.OK)
    public @ResponseBody ResponseEntity<Restaurant> getView(@PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.findView(id));
    }
}