package com.example.application.server.DTOs;

import java.time.LocalDateTime;
import java.util.UUID;

public record FlightDTO(
        UUID flightId,
        String airplaneNumber,
        Integer timeToService,
        String message,
        LocalDateTime time
) {
}
