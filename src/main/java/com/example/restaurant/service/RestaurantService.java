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

import java.util.List;
import java.util.Objects;

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

    public List<Restaurant> getAllOwnersReastaurants(HttpServletRequest request) {
        String login = extractLoginFromBearer(request);
        User user = userRepository
                .findByLogin(login)
                .orElseThrow();
        return user.getRestaurants();
    }

    public Restaurant getViewIfOwner(HttpServletRequest request, Long id) {
        String login = extractLoginFromBearer(request);
        User user = userRepository
                .findByLogin(login)
                .orElseThrow();
        Restaurant restaurant = findView(id);

        if (restaurant.getOwner().getId().equals(user.getId())) return restaurant;
        else throw new AccessDeniedException("You are not the owner");
    }
    private String extractLoginFromBearer(HttpServletRequest request){
        String bearer = request
                .getHeader("Authorization")
                .substring(7);
        return jwtService.extractLogin(bearer);
    }
}
