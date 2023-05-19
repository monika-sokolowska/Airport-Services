package com.example.application.server.controllers;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Airplane;
import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Status;
import com.example.application.server.exceptions.AirplaneNotFound;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.services.AirplaneService;
import com.example.application.server.services.FlightService2;
import com.example.application.server.services.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;
import java.util.logging.Logger;

@RequestMapping("/flight")
@AllArgsConstructor
@RestController
@CrossOrigin
public class FlightController {

    private final String DEPARTURE = "departure"; // TODO export to some class, enum
    private final String LANDED = "landed"; // TODO export to some class, enum

    private final FlightService2 flightService;
    private final AirplaneService airplaneService;
    private final StatusService statusService;

    @GetMapping("/flightsByStatus")
    public ResponseEntity<List<FlightDTO>> getFlightsByStatus(@RequestParam(defaultValue = "landed") String status) {
        List<Flight> flights = flightService.getFlightsByStatus(status);
        List<FlightDTO> flightDTOS = flights.stream().map(flightService::convertFlightToDto).toList();
        return ResponseEntity.ok(flightDTOS);
    }

    /**
     * @param number number of the airplane
     * @return if airplane not found - NOT_FOUND
     *         if found and in the database, but already in service - BAD_REQUEST
     *         on success - OK
     */
    @PostMapping("/navigator/landed")
    public ResponseEntity<FlightDTO> landAirplane(@RequestParam String number) {
        Airplane airplane;
        try {
            airplane = airplaneService.getByNumber(number);
        } catch (AirplaneNotFound a) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // the following checks if the airplane is already serviced
        List<Flight> flights = flightService.getFlightsByAirplaneNumber(number);
        if (!flights.isEmpty()) {
            long isAlreadyServiced = flights.stream()
                    .map(Flight::getStatus)
                    .filter(status -> !status.getStatus().equals(DEPARTURE))
                    .count();
            if(isAlreadyServiced > 0)
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
        }

        // TODO check if works
        Status status;
        try {
            status = statusService.getStatusByStatus(LANDED);
        } catch (StatusNotFound e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Flight flight = new Flight(
                UUID.randomUUID(),
                airplane,
                status,
                null,
                LocalDateTime.now(),
                null,
                null
        );
        flightService.saveFlight(flight);

        return ResponseEntity.ok(flightService.convertFlightToDto(flight));

    }


}
