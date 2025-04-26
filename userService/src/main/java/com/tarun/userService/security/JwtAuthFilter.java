package com.tarun.userService.security;

import com.tarun.userService.entity.User;
import com.tarun.userService.repository.UserRepository;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.core.annotation.Order;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.util.AntPathMatcher;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Collections;
import java.util.List;

@Component
@Order(2)                       // keep your original order
@RequiredArgsConstructor
public class JwtAuthFilter extends OncePerRequestFilter {

    private static final AntPathMatcher matcher = new AntPathMatcher();

    /** Routes that never require a JWT */
    private static final List<String> WHITELIST = List.of(
            "/users/register",
            "/users/login",
            "/v3/api-docs",
            "/v3/api-docs/**",
            "/swagger-ui.html",
            "/swagger-ui/**",
            "/swagger-resources/**",
            "/configuration/**",
            "/webjars/**"
    );

    private final JwtUtil jwtUtil;
    private final UserRepository userRepository;

    /** Skip the filter entirely for whitelisted paths */
    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String path = request.getServletPath();
        return WHITELIST.stream().anyMatch(p -> matcher.match(p, path));
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        String authHeader = request.getHeader("Authorization");

        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);     // nothing to check
            return;
        }

        String token = authHeader.substring(7);

        try {
            String email = jwtUtil.extractEmail(token);

            User user = userRepository.findByEmail(email)
                    .orElseThrow(() -> new RuntimeException("User not found"));

            UsernamePasswordAuthenticationToken auth =
                    new UsernamePasswordAuthenticationToken(
                            email, null, Collections.emptyList());

            SecurityContextHolder.getContext().setAuthentication(auth);
        } catch (Exception e) {
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
            response.getWriter().write("Invalid JWT: " + e.getMessage());
            return;
        }

        filterChain.doFilter(request, response);
    }
}
