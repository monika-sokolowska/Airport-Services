package com.example.application.server.security;

import com.example.application.server.entities.Token;
import com.example.application.server.services.EmployeeService2;
import com.example.application.server.services.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.NonNull;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

import static com.example.application.server.Constants.AUTH_HEADER;
import static com.example.application.server.Constants.AUTH_HEADER_START;

@Component
@RequiredArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {

    private final EmployeeService2 employeeService;
    private final TokenService tokenService;

    @Override
    protected void doFilterInternal(
            @NonNull HttpServletRequest request,
            @NonNull HttpServletResponse response,
            @NonNull FilterChain filterChain
    ) throws ServletException, IOException {

        final String authHeader = request.getHeader(AUTH_HEADER);

        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_START)) {
            filterChain.doFilter(request, response);
            return;
        }
        final String jwt = authHeader.substring(AUTH_HEADER_START.length());
        final String email = tokenService.extractEmail(jwt);

        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails securityUserDetails = employeeService.loadUserByUsername(email);
            Token token = tokenService.findByToken(jwt).orElseGet(null);
            boolean isTokenValid = token != null && !token.isExpired();

            if (tokenService.isTokenValid(jwt, securityUserDetails) && isTokenValid) {
                UsernamePasswordAuthenticationToken authToken = new UsernamePasswordAuthenticationToken(
                        securityUserDetails,
                        null,
                        securityUserDetails.getAuthorities()
                );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        filterChain.doFilter(request, response);
    }
}
