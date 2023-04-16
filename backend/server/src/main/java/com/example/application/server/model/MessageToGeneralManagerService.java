package com.example.application.server.model;
public class MessageToGeneralManagerService {
    private final String message;
    private final int flightNumber;

    public MessageToGeneralManagerService(int flightNumber) {
        message = "landed";
        this.flightNumber = flightNumber;
    }

    public String getMessage() {
        return message;
    }

    public int getFlightNumber() {
        return flightNumber;
    }
}
