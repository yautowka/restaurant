package com.example.restaurant.repos;


import com.example.restaurant.models.Restaurant;
import com.example.restaurant.models.User;
import jakarta.validation.constraints.NotBlank;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

// This will be AUTO IMPLEMENTED by Spring into a Bean called userRepository
// CRUD refers Create, Read, Update, Delete
public interface RestaurantRepository extends JpaRepository<Restaurant, Long> {

//    @Query("select r from Restaurant r where r.id = ?1")
    Page<Restaurant> findAllByOwner(User owner, Pageable pageable);

}