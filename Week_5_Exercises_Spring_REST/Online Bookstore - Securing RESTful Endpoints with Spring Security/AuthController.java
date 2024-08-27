package com.example.bookstoreapi.controller;

import com.example.bookstoreapi.security.JwtTokenProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtTokenProvider jwtTokenProvider;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(loginRequest.getUsername(), loginRequest.getPassword())
        );

        String jwt = jwtTokenProvider.generateToken(authentication);
        return ResponseEntity.ok(new JwtResponse(jwt));
    }

    @PostMapping("/register")
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {
        // Implement user registration logic here
        // You can use a UserService to save the new user in the database
        return ResponseEntity.ok("User registered successfully!");
    }
}

class LoginRequest {
    private String username;
    private String password;

    // Getters and Setters
}

class JwtResponse {
    private String token;

    public JwtResponse(String token) {
        this.token = token;
    }

    // Getter
    public String getToken() {
        return token;
    }
}

class SignUpRequest {
    private String username;
    private String password;
    private String email;

    // Getters and Setters
}
