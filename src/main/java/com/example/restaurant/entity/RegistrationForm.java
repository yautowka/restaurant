package com.example.restaurant.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import lombok.Data;
import com.example.restaurant.Domain.User;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegistrationForm {
    private String login;
    private CharSequence password;
}