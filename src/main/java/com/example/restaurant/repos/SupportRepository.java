package com.example.restaurant.repos;


import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.RestaurantSupport;
import com.example.restaurant.models.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface SupportRepository extends JpaRepository<RestaurantSupport, Long> {
    Page<RestaurantSupport> findAllBySupport(User support, Pageable pageable);
}