package dev.akobir.backend2048.auth.service;

import dev.akobir.backend2048.auth.JwtToken;
import dev.akobir.backend2048.auth.request.UserLoginRequest;
import dev.akobir.backend2048.auth.request.UserRegisterRequest;

public interface AuthService {
    JwtToken login(UserLoginRequest request);

    JwtToken register(UserRegisterRequest request);
}
