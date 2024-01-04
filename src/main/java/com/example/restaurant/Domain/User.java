package com.example.restaurant.Domain;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.util.Collection;
import java.util.List;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
//@Validated
public class User implements UserDetails {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Integer id;
    @Column(name = "login", length = 225, nullable = false, unique = true)
    @NotBlank(message = "You must input login")
    private String login;
    @Column(name = "password_hash", length = 225, nullable = false)
    @NotBlank(message = "You must input password")
    private String password;
    @Column(name = "created_at")
    private long created_at;
    @Column(name = "updated_at")
    private long updated_at;
    @Column(name = "last_login")
    private long last_login;
    @Column(name = "role")
    @Enumerated(EnumType.STRING)
    private Role role;

    public User(String login, String password_hash) {
        this.login = login;
        this.password = password_hash;
        this.created_at = System.currentTimeMillis();
        role = Role.USER;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority(role.name()));
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public String getUsername() {
        return login;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}

