package com.example.application.server.security;

import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import org.springframework.security.web.authentication.logout.LogoutHandler;

@Configuration
@EnableWebSecurity
@RequiredArgsConstructor
public class SecurityConfig {


    private final JwtAuthenticationFilter jwtAuthenticationFilter;
    private final AuthenticationProvider authenticationProvider;
    private final LogoutHandler logoutHandler;

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {

        final String ROLE_EMPLOYEE = "employee";
        final String ROLE_MANAGER_STAND = "stand manager";
        final String ROLE_MANAGER_GENERAL = "general manager";
        final String ROLE_ADMIN = "admin";

        http
                .csrf().disable()
                .authorizeHttpRequests()
                .requestMatchers(HttpMethod.OPTIONS).permitAll()
                .requestMatchers(HttpMethod.OPTIONS, "/**").permitAll()
                .requestMatchers("/api/v1/auth/**",
                        "/v2/api-docs",
                        "/v3/api-docs",
                        "/v3/api-docs/**",
                        "/swagger-resources",
                        "/swagger-resources/**",
                        "/configuration/ui",
                        "/configuration/security",
                        "/swagger-ui/**",
                        "/webjars/**",
                        "/swagger-ui.html"
                ).permitAll()
//                .requestMatchers("/auth").permitAll()
//                .requestMatchers("/auth/**").permitAll()
//                .requestMatchers("/user/delete").hasRole(ROLE_CLIENT)
//                .requestMatchers("/user/**").hasRole(ROLE_ADMIN)
//                .requestMatchers("/user").hasRole(ROLE_ADMIN)
//                .requestMatchers("/user_details/**").hasRole(ROLE_ADMIN)
//                .requestMatchers("/user_details").hasRole(ROLE_ADMIN)
//                .requestMatchers("/test").permitAll()
//                .requestMatchers("/test/logged").authenticated()
//                .requestMatchers("/test/client").hasRole(ROLE_CLIENT)
//                .requestMatchers("/test/employee").hasRole(ROLE_EMPLOYEE)
//                .requestMatchers("/test/isNotAdmin").hasAnyRole(ROLE_CLIENT, ROLE_EMPLOYEE) // ?
//                .anyRequest().hasRole(ROLE_ADMIN)
                .anyRequest().permitAll()
                .and()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and()
                .authenticationProvider(authenticationProvider)
                .addFilterBefore(jwtAuthenticationFilter, UsernamePasswordAuthenticationFilter.class)
                .logout()
//                .logoutUrl("/auth/logout")
                .addLogoutHandler(logoutHandler)
                .logoutSuccessHandler(((request, response, authentication) -> SecurityContextHolder.clearContext()))
        ;


        return http.build();
    }
}
