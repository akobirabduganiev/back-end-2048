package dev.akobir.backend2048.config;

import dev.akobir.backend2048.config.details.CustomProfileDetails;
import dev.akobir.backend2048.config.details.CustomProfileDetailsService;
import dev.akobir.backend2048.exception.AppBadRequestException;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.lang.NonNull;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Optional;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final CustomProfileDetailsService customProfileDetailsService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {
        final String authHeader = request.getHeader("Authorization");

        if (Optional.ofNullable(authHeader).isEmpty() || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        try {
            final String token = authHeader.substring(7);
            JwtDecode decode = JwtUtil.decode(token);

            String id = decode.getId();

            CustomProfileDetails details;

            details = customProfileDetailsService.loadUserByUsername(id);


            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                    details,
                    null,
                    details.getAuthorities()
            );

            authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));

            SecurityContextHolder.getContext().setAuthentication(authentication);
        } catch (ArrayIndexOutOfBoundsException | AppBadRequestException e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            return;
        }
        filterChain.doFilter(request, response);
    }
}
