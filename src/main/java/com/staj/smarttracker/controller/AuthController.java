package com.staj.smarttracker.controller;

import com.staj.smarttracker.config.JwtUtils;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/auth")
@RequiredArgsConstructor
public class AuthController {

    private final JwtUtils jwtUtils;

    @PostMapping("/login")
    public ResponseEntity<AuthResponseDto> login(@RequestBody LoginRequestDto request) {
        String token = jwtUtils.generateToken(request.getEmail());
        return ResponseEntity.ok(new AuthResponseDto(token));
    }
}

@Data
class LoginRequestDto {
    private String email;
    private String password;
}

@Data
@AllArgsConstructor
class AuthResponseDto {
    private String token;
}