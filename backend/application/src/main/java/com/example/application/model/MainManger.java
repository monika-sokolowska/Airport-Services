package com.example.application.model;

import com.example.application.service.FlightService;

import java.util.List;

public class MainManger {

    FlightService flightService;

    public MainManger(FlightService flightService) {
        this.flightService = flightService;
    }

    public void updateFlightInformation(List<Flight> flights){
        flights.forEach(flight -> flightService.saveFlight(flight));
    }
}
