package com.staj.smarttracker.controller;

import com.staj.smarttracker.dto.LoginRequestDto;
import com.staj.smarttracker.dto.AuthResponseDto;
import com.staj.smarttracker.dto.UserRegisterRequestDto;
import com.staj.smarttracker.entity.User;
import com.staj.smarttracker.repository.UserRepository;
import com.staj.smarttracker.service.UserService;
import com.staj.smarttracker.config.JwtUtils;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private JwtUtils jwtUtils;

    @Autowired
    private UserService userService;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody LoginRequestDto request) {

        User user = userRepository.findByEmail(request.getEmail()).orElse(null);


        if (user == null || !passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("E-posta veya şifre hatalı!");
        }


        String token = jwtUtils.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@Valid @RequestBody UserRegisterRequestDto request) {
        try {
            User newUser = userService.registerUser(request);
            return ResponseEntity.ok(newUser);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}