package com.example.application.server.services;

import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Token;
import com.example.application.server.repositories.TokenRepository;
import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import io.jsonwebtoken.io.Decoders;
import io.jsonwebtoken.security.Keys;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.security.Key;
import java.util.*;
import java.util.function.Function;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import static com.example.application.server.Constants.AUTH_HEADER;
import static com.example.application.server.Constants.AUTH_HEADER_START;

@Service
@AllArgsConstructor
public class TokenService implements LogoutHandler {

    private final TokenRepository tokenRepository;
    private final long KEY_LIFESPAN = 1000L * 60L * 60L * 24L; // 24h
    private final String KEY = "15CEE28DF87F160729A37E85D253FCD687EBB841DFF8C795EC57D4D53714FA60";


    public Token saveToken(Employee employee, String jwtToken) {
        expireAllUserTokens(employee.getId());
        Token token = new Token(UUID.randomUUID(), jwtToken, false, employee);
        return tokenRepository.save(token);
    }

    public void expireAllUserTokens(UUID userId) {
        List<Token> tokens = tokenRepository.findAllByEmployeeIdAndExpiredIsFalse(userId);

        tokens.forEach(t -> t.setExpired(true));
//        tokenRepository.deleteAll(tokens);
        tokenRepository.saveAll(tokens);
    }

    public Optional<Token> findByToken(String token) {
        return tokenRepository.findByToken(token);
    }


    ////////////////////////////////////////////////////////////////////////////////

    public Token saveToken(Token token) {
        return tokenRepository.save(token);
    }

    public String extractEmail(String token) {
        return  extractClaim(token, Claims::getSubject);
    }

    public boolean isTokenValid(String token, UserDetails securityUserDetails) {
        final String email = extractEmail(token);
        return email.equals(securityUserDetails.getUsername()) && !isTokenExpired(token);

    }

    private boolean isTokenExpired(String token) {
        return extractExpiration(token).before(new Date());
    }

    private Date extractExpiration(String token) {
        return extractClaim(token, Claims::getExpiration);
    }

    public <T> T extractClaim(String token, Function<Claims, T> claimsResolver) {
        final Claims claims = extractAllClaims(token);
        return claimsResolver.apply(claims);
    }


    public String createToken(Employee employee) {

        HashMap<String, Object> map = new HashMap<>();
        map.put("userId", employee.getId());
        map.put("email", employee.getEmail());
        map.put("fullName", employee.getEmployeeDetails().getFullName());

        return createToken(map, employee);
    }

    public String createToken(
            Map<String, Object> extraClaims,
            UserDetails securityUserDetails
    ) {
        return Jwts
                .builder()
                .setClaims(extraClaims)
                .setSubject(securityUserDetails.getUsername())
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + KEY_LIFESPAN))
                .signWith(getSignInKey(), SignatureAlgorithm.HS256)
                .compact();
    }

    private Claims extractAllClaims(String token) {
        return Jwts
                .parserBuilder()
                .setSigningKey(getSignInKey())
                .build()
                .parseClaimsJws(token)
                .getBody();
    }

    private Key getSignInKey() {
        byte[] keyBytes = Decoders.BASE64.decode(KEY);
        return Keys.hmacShaKeyFor(keyBytes);
    }


    @Override
    public void logout(HttpServletRequest request, HttpServletResponse response, Authentication authentication) {
        final String authHeader = request.getHeader(AUTH_HEADER);
        if (authHeader == null || !authHeader.startsWith(AUTH_HEADER_START)) {
            return;
        }
        final String jwt = authHeader.substring(AUTH_HEADER_START.length());
        Token token = tokenRepository.findByToken(jwt).orElse(null);
        if (token != null) {
            token.setExpired(true);
            tokenRepository.save(token);
        }
    }
}

