package com.example.restaurant.controllers;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.service.RestaurantService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;


@RestController // This means that this class is a Controller
@RequestMapping(path = "/api/v1/owner/restaurant") // This means URL's start with /demo (after Application path)
@RequiredArgsConstructor
public class RestaurantForOwnerController {
    private final RestaurantService restaurantService;

    @GetMapping(path = "/index")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<Page<Restaurant>> getAllOwnersRestaurants(HttpServletRequest request, int offset, int size) {
        return ResponseEntity.ok(restaurantService.getAllOwnersRestaurants(request, offset, size));
    }

    @GetMapping(path = "/view/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<Restaurant> getViewIfOwner(HttpServletRequest request, @PathVariable Long id) {
        return ResponseEntity.ok(restaurantService.getViewIfOwner(request, id));
    }

    @PostMapping(path = "/create")
    @ResponseStatus(HttpStatus.CREATED)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<String> createRestaurant(HttpServletRequest request, RestaurantDto newData) {
        return ResponseEntity.ok(restaurantService.createRestaurant(request, newData));
    }

    @PostMapping(path = "/update/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<String> updateRestaurant(HttpServletRequest request, Restaurant restaurantUpdated) {
//        return ResponseEntity.ok("da");
        return ResponseEntity.ok(restaurantService.updateRestaurant(request, restaurantUpdated));
    }

    @DeleteMapping(path = "/delete/{id}")
    @ResponseStatus(HttpStatus.OK)
    @PreAuthorize("hasRole('OWNER')")
    public @ResponseBody ResponseEntity<String> deleteRestaurant(HttpServletRequest request, @PathVariable int id) {
        return ResponseEntity.ok(restaurantService.deleteRestaurant(request, id));
    }
}