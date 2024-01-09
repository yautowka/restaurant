package com.example.restaurant.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.hibernate.annotations.GenericGenerator;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@ToString
@Table(name = "users")
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO, generator = "native")
    @GenericGenerator(name="native")
    private Integer id;
    @Column(name = "login", length = 225, nullable = false, unique = true)
    @NotBlank(message = "You must input login")
    private String login;
    @Column(name = "password_hash", length = 225, nullable = false)
    @NotBlank(message = "You must input password")
    private String password;
    @Column(name = "created_at")
    private LocalDateTime created_at;
    @Column(name = "updated_at")
    private LocalDateTime updated_at;
    @Column(name = "last_login")
    private LocalDateTime last_login;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;
    //    @Column(name = "restaurants")
//    @ElementCollection
//    @JsonInclude(value = JsonInclude.Include.CUSTOM,
//            valueFilter = JsonIncludeRestaurantsFilter.class)
    @OneToMany(mappedBy = "owner",cascade = CascadeType.ALL, orphanRemoval = true)
    private List<Restaurant> restaurants = new ArrayList<>();

    public User(String login, String password_hash) {
        this.login = login;
        this.password = password_hash;
        this.created_at = LocalDateTime.now();
        role = Role.ROLE_USER;
    }

    public void addRestaurant(Restaurant restaurant) {
        restaurants.add(restaurant);
        restaurant.setOwner(this);
    }
    public void removeRestaurant(Restaurant restaurant) {
        restaurants.remove(restaurant);
        restaurant.setOwner(null);
    }
    @Override
    @JsonIgnore
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    @JsonIgnore
    public String getPassword() {
        return password;
    }

    @Override
    @JsonIgnore
    public String getUsername() {
        return login;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    @JsonIgnore
    public boolean isEnabled() {
        return true;
    }
}

