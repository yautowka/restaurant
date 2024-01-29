package com.example.restaurant.models;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import lombok.*;

import java.io.Serializable;

@Embeddable
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@EqualsAndHashCode
public class RestaurantSupportKey implements Serializable {
    @Column(name = "supportId")
    Integer supportId;
    @Column(name = "restaurantId")
    Integer restaurantId;
}
