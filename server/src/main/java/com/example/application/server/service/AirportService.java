package com.example.application.server.service;

import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
public class AirportService {
    private final Map<Integer, Services> stateByFlightNumber;
    private final WorkService service;

    public AirportService(WorkService workService) {
        stateByFlightNumber = new HashMap<>();
        this.service = workService;
    }

    private Services services() {
        var flightService = new FlightService();

        return new Services(
                flightService,
                new BoardingService(flightService, service),
                new CateringService(flightService, service),
                new CleaningService(flightService, service),
                new GeneralManagerService(flightService),
                new LuggageService(flightService, service),
                new PilotService(flightService),
                new PushbackService(flightService, service),
                new StandManagerService(flightService, service),
                new TankingService(flightService, service)
        );
    }

    public Services getMessage(int flightNumber) {
        if(!stateByFlightNumber.containsKey(flightNumber))
            stateByFlightNumber.put(flightNumber, services());

        return stateByFlightNumber.get(flightNumber);
    }
}
