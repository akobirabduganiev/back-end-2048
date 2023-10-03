package dev.akobir.backend2048.config;

import dev.akobir.backend2048.enums.Role;
import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class JwtDecode {
    private String id;
    private Role role;
}
