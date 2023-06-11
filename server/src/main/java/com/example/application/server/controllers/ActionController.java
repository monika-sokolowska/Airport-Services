package com.example.application.server.controllers;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Status;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.services.EmployeeService2;
import com.example.application.server.services.FlightService2;
import com.example.application.server.services.StatusService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;
import java.util.UUID;

@RequestMapping("/action")
@RestController
@AllArgsConstructor
public class ActionController {

    private FlightService2 flightService;
    private EmployeeService2 employeeService;
    private StatusService statusService;

    @PostMapping("{flightId}/startService")
    public ResponseEntity<FlightDTO> startService(@PathVariable UUID flightId) {
        //todo tutaj ma wystartowac laugg
        employeeService.assignLuggageArrivalService(flightId);
        Status luggageArrival1;

        try {
            luggageArrival1 = statusService.getStatusByStatusName("luggageArrival");
        } catch (StatusNotFound e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        Optional<Flight> luggageArrival = flightService.updateStatus(flightId, luggageArrival1);
        return luggageArrival
                .map(flight -> ResponseEntity.ok(flightService.convertFlightToDto(flight)))
                .orElse(ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build());
    }
}
