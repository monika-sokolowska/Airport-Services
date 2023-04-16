package com.example.application.storage;

import com.example.application.model.Flight;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class FlightDataBase {
    Map<UUID, Flight> flights;

    public FlightDataBase() {
        this.flights = new HashMap<>();
    }


    public void save(Flight flight) {
        flights.put(flight.getFlightNumber(), flight);
    }

    public Flight findByNumber(UUID flightNumber) {
        return flights.get(flightNumber);
    }
}
