package com.example.restaurant.models;

import jakarta.persistence.*;
import lombok.*;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "restaurant_support")
public class RestaurantSupport {
    @EmbeddedId
    RestaurantSupportKey id;
    @MapsId("supportId")
    @ManyToOne
    @JoinColumn(name="support_id")
//    @Column(name = "support_id")
    private User support;
    @MapsId("restaurantId")
    @ManyToOne
    @JoinColumn(name="restaurant_id")
//    @Column(name = "restaurant_id")
    private Restaurant restaurant;

}
