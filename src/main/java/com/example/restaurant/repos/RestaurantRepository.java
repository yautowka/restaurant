package com.example.restaurant.repos;


import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

}