package com.example.restaurant.service;

import com.example.restaurant.entity.AuthenticationResponse;
import com.example.restaurant.entity.LoginForm;
import com.example.restaurant.entity.RegistrationForm;
import com.example.restaurant.entity.SimpleResponse;
import com.example.restaurant.models.Role;
import com.example.restaurant.models.User;
import com.example.restaurant.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
@RequiredArgsConstructor
public class AdminService {
    private final UserRepository userRepository;
    public Page<User> findAll(int offset, int size) {
        Pageable pageable = PageRequest.of(offset, size);
        return userRepository.findAll(pageable);
    }
}
