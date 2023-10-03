package dev.akobir.backend2048.auth.request;

import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @Min(value = 3, message = "Username must be at least three characters long")
    @NotNull(message = "Username cannot be null")
    private String username;
    @NotNull(message = "Name cannot be null")
    private String name;
    @NotNull(message = "Surname cannot be null")
    private String surname;
    @Min(value = 8, message = "Password must be at least eight characters long")
    private String password;
}
