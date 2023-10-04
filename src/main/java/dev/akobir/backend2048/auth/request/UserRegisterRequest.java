package dev.akobir.backend2048.auth.request;


import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserRegisterRequest {
    @NotNull(message = "Username cannot be null")
    @Size(min = 3, max = 45, message = "The Username must be between 3 and 45 characters long")
    private String username;
    @NotBlank(message = "Name cannot be blank")
    @Size(min = 3, max = 45, message = "The Name must be between 3 and 45 characters long")
    private String name;
    @NotBlank(message = "Surname cannot be blank")
    @Size(min = 3, max = 45, message = "Surname must be between 3 and 45 characters long")
    private String surname;
    @NotNull(message = "Password cannot be null")
    @Size(min = 6, max = 45, message = "The Password must be between 6 and 45 characters long")
    private String password;
}
