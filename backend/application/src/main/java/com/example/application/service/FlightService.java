package com.example.application.service;

import com.example.application.model.Flight;
import com.example.application.storage.FlightDataBase;

import java.util.UUID;

public class FlightService {

    private final FlightDataBase flightDataBase;

    public FlightService() {
        this.flightDataBase = new FlightDataBase();
    }

    public void saveFlight(Flight flight){
        flightDataBase.save(flight);
    }

    public Flight findFlightByFlightNumber(UUID flightNumber) {
        return flightDataBase.findByNumber(flightNumber);
    }

}
