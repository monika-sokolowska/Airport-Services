package com.example.application.model;

import java.time.LocalDate;
import java.util.UUID;

public class Flight {
    private UUID flightNumber;
    private String flightFrom;
    private String flightTo;
    private LocalDate departureTime;
    private int gateNumber;
    private long planningTravelTimeInMinutes;

    public Flight(UUID flightNumber) {
        this.flightNumber = flightNumber;
    }

    public Flight(UUID flightNumber, String flightFrom, String flightTo, LocalDate departureTime, int gateNumber, long planningTravelTimeInMinutes) {
        this.flightNumber = flightNumber;
        this.flightFrom = flightFrom;
        this.flightTo = flightTo;
        this.departureTime = departureTime;
        this.gateNumber = gateNumber;
        this.planningTravelTimeInMinutes = planningTravelTimeInMinutes;
    }

    public UUID getFlightNumber() {
        return flightNumber;
    }

    public void setFlightNumber(UUID flightNumber) {
        this.flightNumber = flightNumber;
    }

    public String getFlightFrom() {
        return flightFrom;
    }

    public void setFlightFrom(String flightFrom) {
        this.flightFrom = flightFrom;
    }

    public String getFlightTo() {
        return flightTo;
    }

    public void setFlightTo(String flightTo) {
        this.flightTo = flightTo;
    }

    public LocalDate getDepartureTime() {
        return departureTime;
    }

    public void setDepartureTime(LocalDate departureTime) {
        this.departureTime = departureTime;
    }

    public int getGateNumber() {
        return gateNumber;
    }

    public void setGateNumber(int gateNumber) {
        this.gateNumber = gateNumber;
    }

    public long getPlanningTravelTimeInMinutes() {
        return planningTravelTimeInMinutes;
    }

    public void setPlanningTravelTimeInMinutes(long planningTravelTimeInMinutes) {
        this.planningTravelTimeInMinutes = planningTravelTimeInMinutes;
    }
}
