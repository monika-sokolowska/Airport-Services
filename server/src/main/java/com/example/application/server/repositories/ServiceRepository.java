package com.example.application.server.repositories;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.application.server.entities.Service;

import java.util.Set;
import java.util.UUID;

@Repository
public interface ServiceRepository  extends JpaRepository<Service, UUID> {
    Set<Service> findAllByFlightId(UUID flightId);
}
