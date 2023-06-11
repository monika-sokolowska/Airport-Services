package com.example.application.server.repositories;

import com.example.application.server.entities.Token;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface TokenRepository extends JpaRepository<Token, UUID> {
    List<Token> findAllByEmployeeId(UUID employeeId);
    List<Token> findAllByEmployeeIdAndExpiredIsFalse(UUID employeeId); // this
    List<Token> findAllByEmployeeIdAndExpiredIsTrue(UUID employeeId);
    Optional<Token> findByToken(String token);
}
