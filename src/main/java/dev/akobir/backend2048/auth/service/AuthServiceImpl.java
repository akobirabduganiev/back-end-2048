package dev.akobir.backend2048.auth.service;

import dev.akobir.backend2048.auth.JwtToken;
import dev.akobir.backend2048.auth.request.UserLoginRequest;
import dev.akobir.backend2048.auth.request.UserRegisterRequest;
import dev.akobir.backend2048.config.JwtUtil;
import dev.akobir.backend2048.enums.Role;
import dev.akobir.backend2048.exception.UserBadRequestException;
import dev.akobir.backend2048.user.entity.UserEntity;
import dev.akobir.backend2048.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service("authService")
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public JwtToken login(UserLoginRequest request) {
        var userEntity = userRepository.findByUsername(request.getUsername())
                .orElseThrow(() -> new UserBadRequestException("User isn't found"));

        if (!passwordEncoder.matches(request.getPassword(), userEntity.getPassword())) {
            throw new UserBadRequestException("Login or Password incorrect");
        }

        return new JwtToken(generateTokenForUser(userEntity));
    }

    @Override
    public JwtToken register(UserRegisterRequest request) {
        userRepository.findByUsername(request.getUsername())
                .ifPresent(userEntity -> {
                    throw new UserBadRequestException("User already exists");
                });
        var entity = new UserEntity();
        entity.setUsername(request.getUsername());
        entity.setName(request.getName());
        entity.setSurname(request.getSurname());
        entity.setRole(Role.ROLE_USER);
        entity.setPassword(passwordEncoder.encode(request.getPassword()));
        userRepository.save(entity);

        return new JwtToken(generateTokenForUser(entity));
    }

    private String generateTokenForUser(UserEntity userEntity) {
        return JwtUtil.encode(userEntity.getId(), userEntity.getRole());
    }
}
