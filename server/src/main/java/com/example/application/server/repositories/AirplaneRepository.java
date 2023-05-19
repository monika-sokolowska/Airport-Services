package com.example.application.server.repositories;

import com.example.application.server.entities.Airplane;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface AirplaneRepository extends JpaRepository<Airplane, UUID> {

    Optional<Airplane> findByNumber(String number);

}
