package dev.akobir.backend2048.auth.controller;

import dev.akobir.backend2048.auth.JwtToken;
import dev.akobir.backend2048.auth.request.UserLoginRequest;
import dev.akobir.backend2048.auth.request.UserRegisterRequest;
import dev.akobir.backend2048.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping("/api/v1/auth")
public class AuthController {
    private final AuthService authService;

    @PostMapping("/register")
    public ResponseEntity<JwtToken> registration(@Valid @RequestBody UserRegisterRequest userRegisterRequest) {
        return ResponseEntity.ok(authService.register(userRegisterRequest));
    }

    @PostMapping("/login")
    public ResponseEntity<JwtToken> login(@Valid @RequestBody UserLoginRequest userLoginRequest) {
        return ResponseEntity.ok(authService.login(userLoginRequest));
    }
}
