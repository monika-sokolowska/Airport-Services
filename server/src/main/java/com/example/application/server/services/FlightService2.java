package com.example.application.server.services;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Status;
import com.example.application.server.entities.Service;
import com.example.application.server.enums.StatusesEnum;
import com.example.application.server.exceptions.FlightNotFoundException;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.repositories.FlightRepository;
import com.example.application.server.repositories.StatusRepository;
import lombok.AllArgsConstructor;

import java.util.*;
import java.util.stream.Collectors;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class FlightService2 {
    private final FlightRepository flightRepository;
    private final StatusService statusService;
    private final ServicesService servicesService;

    private final StatusRepository statusRepository;

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
                flight.getTimeToService(),
                flight.getMessage(),
                flight.getArrivalTime()
        );
    }

    public void saveFlight(Flight flight) {
        flightRepository.save(flight);
    }

    public Optional<Flight> updateStatus(UUID flightId, Status status) throws NoSuchElementException {
        Optional<Flight> byId = flightRepository.findById(flightId);
        if(byId.isEmpty()){
            throw new NoSuchElementException("flight not found");
        }
        Flight flight = byId.get();
        flight.setStatus(status);
        flightRepository.save(flight);
        return byId;
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
        updatedFlight.setStandManager(standManager);
        updatedFlight.setTimeToService(timeToService);
        updatedFlight.setMessage(message);
        updatedFlight.setStatus(statusRepository.findByStatus("standManager").get());

        flightRepository.save(updatedFlight);

    }



    public List<Flight> getFlightsByStandManager(Employee standManger) {
        return flightRepository.findByStandManager(standManger);
    }

    public void finishFlightService(final Flight flight) throws StatusNotFound {
        final Status oldStatus = flight.getStatus();
        final String newStatusName = StatusesEnum.getNextStatusEnumByStatusName(oldStatus.getStatus()).getStatusName();
        final Status status = statusService.getStatusByStatusName(newStatusName);

        flight.setStatus(status);
        flightRepository.save(flight);
    }


}
