package com.example.application.server.repositories;

import com.example.application.server.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    List<Flight> findByStatusStatus(String status);
    List<Flight> findByAirplaneNumber(String number);

}
