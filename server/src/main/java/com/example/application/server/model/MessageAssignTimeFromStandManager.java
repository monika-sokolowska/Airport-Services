package com.example.application.server.model;

public record MessageAssignTimeFromStandManager(int flightNumber, String message, int minutes, ServiceType service) {
}
