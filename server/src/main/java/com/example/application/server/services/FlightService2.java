package com.example.application.server.services;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import com.example.application.server.exceptions.FlightNotFoundException;
import com.example.application.server.repositories.FlightRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;

@Service
@AllArgsConstructor
public class FlightService2 {
    private final FlightRepository flightRepository;

    public List<Flight> getFlightsByStatus(String status) {
        return flightRepository.findByStatusStatus(status);
    }

    public List<Flight> getFlightsByAirplaneNumber(String number) {
        return flightRepository.findByAirplaneNumber(number);
    }

    public FlightDTO convertFlightToDto(Flight flight) {
        return new FlightDTO(
                flight.getId(),
                flight.getAirplane().getNumber(),
                flight.getTimeToService()
        );
    }

    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
    }

    public Optional<Flight> updateStatus(UUID flightId, String status) throws NoSuchElementException {
        flightRepository.saveOrUpdate(flightId, status);
        return flightRepository.findById(flightId);
    }

    public Optional<Flight> getFlightById(UUID flightId) {
        return flightRepository.findById(flightId);
    }

    public void updateFlightInfo(UUID flightId, Employee standManager, String message, Integer timeToService) throws FlightNotFoundException {
        Optional<Flight> flightById = this.getFlightById(flightId);
        if(flightById.isEmpty()) {
            throw new FlightNotFoundException(String.format("Flight with id %s not found", flightId));
        }
        Flight updatedFlight = flightById.get();
        updatedFlight.setStand_manager(standManager);
        updatedFlight.setTimeToService(timeToService);
        //TODO po co ta message ?
        // Czy zmieniamy tu status lotu na standmanager
        flightRepository.save(updatedFlight);

    }

    public List<Flight> getFlightsByStandManager(Employee standManger) {
        return flightRepository.findByStand_manager(standManger);
    }
}
