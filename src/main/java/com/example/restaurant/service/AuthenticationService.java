package com.example.restaurant.service;

import com.example.restaurant.Domain.Role;
import com.example.restaurant.entity.AuthenticationResponse;
import com.example.restaurant.entity.LoginForm;
import com.example.restaurant.Domain.User;
import com.example.restaurant.entity.RegistrationForm;
import com.example.restaurant.repos.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.net.URI;
@Service
@RequiredArgsConstructor
public class AuthenticationService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;
    public AuthenticationResponse signup(RegistrationForm form) {
        var user = User.builder()
                .login(form.getLogin())
                .password(passwordEncoder.encode(form.getPassword()))
                .role(Role.USER)
                .build();
        userRepository.save(user);
        var jwtToken = jwtService.generateToken(user);
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();

    }

    public AuthenticationResponse login(LoginForm form) {
        System.out.println("da");
//        authenticationManager.authenticate(
//                new UsernamePasswordAuthenticationToken(
//                        form.getLogin(),
//                        form.getPassword()));
        System.out.println("hi1");
        var user = userRepository.findByLogin(form.getLogin())
                .orElseThrow();
        System.out.println("hi2");
        var jwtToken = jwtService.generateToken(user);
        System.out.println("hi3");
        return AuthenticationResponse.builder()
                .token(jwtToken)
                .build();
    }
}
