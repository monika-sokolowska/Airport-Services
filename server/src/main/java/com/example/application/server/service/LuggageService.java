package com.example.application.server.service;

import com.example.application.server.model.MessageToService;
import com.example.application.server.model.ServiceType;

public class LuggageService extends MessageQueue<MessageToService> {
    private final FlightService flightService;
    private final WorkService workService;

    public LuggageService(FlightService flightService, WorkService workService) {
        this.flightService = flightService;
        this.workService = workService;

        this.flightService.onSendMessageToLuggageService(messageQueue::add);
    }

    public void finished(int flightNumber) {
        flightService.luggageFinished();
        workService.startWork(flightNumber, ServiceType.BOARDING_SERVICE);
        workService.completeStage(flightNumber, ServiceType.LUGGAGE_SERVICE);
    }

    public void finishedDeparture(int flightNumber) {
        flightService.luggageDepartureFinished();
        workService.startWork(flightNumber, ServiceType.PUSHBACK_SERVICE);
        workService.completeStage(flightNumber, ServiceType.LUGGAGE_SERVICE, true);
    }
}
