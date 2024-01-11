package com.example.restaurant.service;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.User;
import com.example.restaurant.repos.RestaurantRepository;
import com.example.restaurant.repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Page<Restaurant> findAll(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        return restaurantRepository.findAll(pageable);

    }

    public Restaurant findView(Long id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    public Page<Restaurant> getAllOwnersRestaurants(HttpServletRequest request, int offset, int size) {
        User user = getUser(request);
        Pageable pageable = PageRequest.of(offset, size);
        return restaurantRepository.findAllByOwner(user, pageable);
    }

    public Restaurant getViewIfOwner(HttpServletRequest request, Long id) {
        User user = getUser(request);
        Restaurant restaurant = findView(id);

        if (restaurant.getOwner().getId().equals(user.getId())) return restaurant;
        else throw new AccessDeniedException("You are not the owner");
    }

    private String extractLoginFromBearer(HttpServletRequest request) {
        String bearer = request
                .getHeader("Authorization")
                .substring(7);
        return jwtService.extractLogin(bearer);
    }

    private User getUser(HttpServletRequest request) {
        String login = extractLoginFromBearer(request);
        return userRepository
                .findByLogin(login)
                .orElseThrow();
    }

    public String createRestaurant(HttpServletRequest request, RestaurantDto newRestaurantData) {
        User user = getUser(request);
        Restaurant newRestaurant = Restaurant
                .builder()
                .name(newRestaurantData.getName())
                .isActive(newRestaurantData.getIsActive())
                .openingHours(newRestaurantData.getOpeningHours())
                .city(newRestaurantData.getCity())
                .address(newRestaurantData.getAddress())
                .lat(newRestaurantData.getLat())
                .lng(newRestaurantData.getLng())
                .build();
        newRestaurant.setOwner(user);
        restaurantRepository.save(newRestaurant);
        return "New restaurant created";
    }

    public String deleteRestaurant(HttpServletRequest request, int id) {
        User user = getUser(request);
        Restaurant restaurant = restaurantRepository
                .findById((long) id)
                .orElseThrow();
        if (restaurant.getOwner().equals(user)) {
            restaurantRepository.delete(restaurant);
            return "deleted";
        } else throw new AccessDeniedException("You are not the owner");
    }

    public String updateRestaurant(HttpServletRequest request, Restaurant restaurantUpdated) {
        User user = getUser(request);
        Restaurant restaurant = restaurantRepository
                .findById(Long.valueOf(restaurantUpdated.getId()))
                .orElseThrow();
        if (restaurant.getOwner().equals(user)) {
            restaurantUpdated.setOwner(user);
            restaurantRepository.save(restaurantUpdated);
            return "updated";
        } else throw new AccessDeniedException("You are not the owner");
    }
}
