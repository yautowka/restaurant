package com.example.restaurant.service;

import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.RestaurantSupport;
import com.example.restaurant.models.Role;
import com.example.restaurant.models.User;
import com.example.restaurant.repos.RestaurantRepository;
import com.example.restaurant.repos.SupportRepository;
import com.example.restaurant.repos.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class RestaurantService {
    private final RestaurantRepository restaurantRepository;
    private final SupportRepository supportRepository;
    private final JwtService jwtService;
    private final UserRepository userRepository;

    public Page<Restaurant> findAll(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        return restaurantRepository.findAll(pageable);

    }

    public Restaurant findView(Long id) {
        return restaurantRepository.findById(id).orElseThrow();
    }

    public Page<Restaurant> getAllRestaurants(HttpServletRequest request, int offset, int size) {
        User user = getUser(request);
        Pageable pageable = PageRequest.of(offset, size);
        return getAllRestaurantsFromUser(user, pageable);
    }

    private Page<Restaurant> getAllRestaurantsFromUser(User user, Pageable pageable) {
        if (user.getRole().equals(Role.ROLE_OWNER)) return restaurantRepository.findAllByOwner(user, pageable);
        Page<RestaurantSupport> restaurantSupports = supportRepository.findAllBySupport(user, pageable);
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

    public Restaurant getView(HttpServletRequest request, Long id) {
        User user = getUser(request);
        Restaurant restaurant = findView(id);

        if (restaurant.getOwner().getId().equals(user.getId())
                || isSupportOfRestaurant(user, restaurant)) return restaurant;
        else throw new AccessDeniedException("You are not the owner or support");
    }

    private boolean isSupportOfRestaurant(User user, Restaurant restaurant) {
        return supportRepository.findBySupportAndRestaurant(user, restaurant) != null;
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

    public String createRestaurant(HttpServletRequest request, Restaurant newRestaurant) {
        User user = getUser(request);
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
        if (restaurant.getOwner().equals(user) || isSupportOfRestaurant(user, restaurant)) {
            restaurantUpdated.setOwner(user);
            restaurantRepository.save(restaurantUpdated);
            return "updated";
        } else throw new AccessDeniedException("You are not the owner");
    }
}
