package com.example.application.server.controllers;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Airplane;
import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Status;
import com.example.application.server.enums.StatusesEnum;
import com.example.application.server.exceptions.AirplaneNotFound;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.exceptions.FlightNotFoundException;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.services.*;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

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
    private final EmployeeService2 employeeService;

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

        Status status;
        try {
            status = statusService.getStatusByStatusName(LANDED);
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



    /*
    *
        * Sprawdzamy jaki jest status dla numeru flightid.
        * Zmieniamy status tego lotu na kolejny

    * */

    @Transactional
    @PostMapping("/finished/{employeeId}/{flightId}")
    public ResponseEntity<String> serviceFinished(@PathVariable final UUID employeeId, @PathVariable final UUID flightId) {
        String response = "";
        try {
            Flight flight = flightService.getFlightById(flightId)
                    .orElseThrow(() -> new FlightNotFoundException("Flight with given id not found: " + flightId));
            Status status = flight.getStatus();

            final StatusesEnum currentStatus = StatusesEnum.getStatusEnumByStatusName(status.getStatus());
            final String employeeDepartment = employeeService.getEmployeeDepartment(employeeId);

            if(!StatusesEnum.isPushPossible(currentStatus.getStatusName(), employeeDepartment)) {
                // TODO ADD CUSTOM EXCEPTION
                throw new Exception("This user cannot finish service current Service." +
                        "\tUser department: " + employeeDepartment +
                        "\tFlight status: " + currentStatus.getStatusName());
            }

            flightService.finishFlightService(flight);

        } catch (FlightNotFoundException | StatusNotFound | EmployeeNotFoundException e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Flight status has been updated.");
    }


}
