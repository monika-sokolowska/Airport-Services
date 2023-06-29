package com.example.application.server.DTOs;

import lombok.Builder;

import java.util.UUID;

@Builder
public record ServiceDTO(
        UUID flightId,
        String message,
        int timeToService,
        String airplaneNumber
) {
}
