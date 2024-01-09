package com.example.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;


@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "restaurants")
public class Restaurant {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native")
    private Integer id;
    @Column(name = "name", length = 225, nullable = false, unique = true)
    @NotBlank(message = "You must input name")
    private String name;
    @NotBlank(message = "You must input owner id")
    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JsonIgnore
    private User owner;
    @Column(name = "isActive")
    private Boolean isActive;
    @Column(name = "opening_hours")
    private String opening_hours;
    @Column(name = "city")
    private String city;
    @Column(name = "address")
    private String address;
    @Column(name = "lat")
    private Double lat;
    @Column(name = "lng")
    private Double lng;
}
