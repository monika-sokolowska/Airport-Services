package com.example.application.server.services;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Flight;
import com.example.application.server.repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class FlightService2 {
    private final FlightRepository flightRepository;

    public List<Flight> getFlightsByStatus(String status) {
        return flightRepository.findByStatusStatus(status);
    }

    public FlightDTO convertFlightToDto(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getAirplane().getNumber()
        );
    }
}
