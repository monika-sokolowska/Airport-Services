package com.example.application.server.service;

public record Services(
        FlightService flightService,
        BoardingService boardingService,
        CateringService cateringService,
        CleaningService cleaningService,
        GeneralManagerService generalManagerService,
        LuggageService luggageService,
        PilotService pilotService,
        PushbackService pushbackService,
        StandManagerService standManagerService,
        TankingService tankingService
) {
}
