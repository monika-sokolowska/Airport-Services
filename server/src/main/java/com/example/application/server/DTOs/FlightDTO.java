package com.example.application.server.DTOs;

import java.util.UUID;

public record FlightDTO(
        UUID flightId,
        String airplaneNumber,
        Integer timeToService
) {
}
