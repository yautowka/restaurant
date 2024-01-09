package com.example.restaurant.service;

import com.example.restaurant.models.Role;
import com.example.restaurant.entity.AuthenticationResponse;
import com.example.restaurant.entity.LoginForm;
import com.example.restaurant.models.User;
import com.example.restaurant.entity.RegistrationForm;
import com.example.restaurant.entity.SimpleResponse;
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
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public SimpleResponse signup(RegistrationForm form) {
        var user = User.builder()
                .login(form.getLogin())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(Role.ROLE_USER)
                .created_at(LocalDateTime.now())
                .build();
        userRepository.save(user);
        return SimpleResponse.builder()
                .message("User created")
                .build();

    }

    public AuthenticationResponse login(LoginForm form) {
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        form.getLogin(),
                        form.getPassword()));
        var user = userRepository.findByLogin(form.getLogin())
                .orElseThrow();
        var jwtToken = jwtService.generateToken(user);
        user.setLast_login(LocalDateTime.now());
        userRepository.save(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
