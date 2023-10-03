package dev.akobir.backend2048.user.entity;

import dev.akobir.backend2048.enums.Role;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "users")
public class UserEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id", nullable = false)
    private Long id;
    private String username;
    private String password;
    private String name;
    private String surname;
    @Enumerated(EnumType.STRING)
    private Role role;

}