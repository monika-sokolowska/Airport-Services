package com.example.application.server.controllers;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Airplane;
import com.example.application.server.entities.Flight;
import com.example.application.server.services.FlightService2;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequestMapping("/flight")
@AllArgsConstructor
@RestController
@CrossOrigin
public class FlightController {

    private final FlightService2 flightService;

    @GetMapping("/flightsByStatus")
    public ResponseEntity<List<FlightDTO>> getFlightsByStatus(@RequestParam(defaultValue = "landed") String status) {
        List<Flight> flights = flightService.getFlightsByStatus(status);
        List<FlightDTO> flightDTOS = flights.stream().map(flightService::convertFlightToDto).toList();
        return ResponseEntity.ok(flightDTOS);
    }


}
