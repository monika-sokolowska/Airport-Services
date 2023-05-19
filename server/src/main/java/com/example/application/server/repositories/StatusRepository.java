package com.example.application.server.repositories;

import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Status;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;
import java.util.Optional;

@Repository
public interface StatusRepository extends JpaRepository<Status, UUID> {
    Optional<Status> findByStatus(String status);
}
