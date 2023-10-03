package dev.akobir.backend2048.config.details;

import dev.akobir.backend2048.exception.ItemNotFoundException;
import dev.akobir.backend2048.user.entity.UserEntity;
import dev.akobir.backend2048.user.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CustomProfileDetailsService implements UserDetailsService {
    private final UserRepository userRepository;

    @Override
    public CustomProfileDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserEntity entity = userRepository
                .findById(Long.valueOf(username))
                .orElseThrow(() -> new ItemNotFoundException("User isn't found"));

        return new CustomProfileDetails(
                entity.getId(),
                entity.getUsername(),
                entity.getPassword(),
                entity.getRole()
        );
    }
}
