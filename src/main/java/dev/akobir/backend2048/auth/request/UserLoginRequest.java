package dev.akobir.backend2048.auth.request;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserLoginRequest {
    @NotBlank(message = "Username cannot be blank")
    private String username;
    @NotBlank(message = "Password cannot be blank")
    private String password;
}
