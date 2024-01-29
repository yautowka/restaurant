package com.example.restaurant.service;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.RestaurantSupport;
import com.example.restaurant.models.User;
import com.example.restaurant.repos.SupportRepository;
import com.example.restaurant.repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SupportService {
    private final SupportRepository supportRepository;
    private final UserRepository userRepository;
    private final JwtService jwtService;

    public Page<Restaurant> findAll(HttpServletRequest request, int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        User support = getSupport(request);
        Page<RestaurantSupport> restaurantSupports = supportRepository.findAllBySupport(support, pageable);
        return extractRestaurants(restaurantSupports);
    }

    private Page<Restaurant> extractRestaurants(Page<RestaurantSupport> restaurantSupports) {
        List<Restaurant> restaurantList = restaurantSupports
                .stream()
                .map(RestaurantSupport::getRestaurant)
                .toList();
        Pageable pageRequest = restaurantSupports.getPageable();
        return new PageImpl<>(restaurantList, pageRequest, restaurantList.size());
    }

    private User getSupport(HttpServletRequest request) {
        String login = extractLoginFromBearer(request);
        return userRepository
                .findByLogin(login)
                .orElseThrow();
    }

    private String extractLoginFromBearer(HttpServletRequest request) {
        String bearer = request
                .getHeader("Authorization")
                .substring(7);
        return jwtService.extractLogin(bearer);
    }
}
