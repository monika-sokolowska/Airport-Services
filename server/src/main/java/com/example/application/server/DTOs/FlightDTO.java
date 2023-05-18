package com.example.application.server.DTOs;

import java.util.UUID;

public record FlightDTO(
        UUID flightId,
        UUID airplaneId,
        String airplaneNumber
) {
}
