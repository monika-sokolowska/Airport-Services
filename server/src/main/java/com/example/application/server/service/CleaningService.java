package com.example.application.server.service;

import com.example.application.server.model.MessageToService;
import com.example.application.server.model.ServiceType;

public class CleaningService extends MessageQueue<MessageToService> {
    private final FlightService airportService;
    private final WorkService workService;

    public CleaningService(FlightService airportService, WorkService workService) {
        this.airportService = airportService;
        this.workService = workService;

        this.airportService.onSendMessageToCleaningService(messageQueue::add);
    }
    public void finished(int flightNumber) {
        airportService.cleaningFinished();
        workService.startWork(flightNumber, ServiceType.TANKING_SERVICE);
        workService.completeStage(flightNumber, ServiceType.CLEANING_SERVICE);
    }

}
