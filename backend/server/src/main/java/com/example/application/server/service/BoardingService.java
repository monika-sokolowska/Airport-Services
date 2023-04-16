package com.example.application.server.service;

import com.example.application.server.model.MessageToService;
import com.example.application.server.model.ServiceType;

public class BoardingService extends MessageQueue<MessageToService> {
    private final FlightService flightService;
    private final WorkService workService;

    public BoardingService(FlightService flightService, WorkService workService) {
        this.flightService = flightService;
        this.workService = workService;

        this.flightService.onSendMessageToBoardingService(messageQueue::add);
    }

    public void finished(int flightNumber) {
        flightService.boardingFinished();
        workService.startWork(flightNumber, ServiceType.CLEANING_SERVICE);
        workService.completeStage(flightNumber, ServiceType.BOARDING_SERVICE);
    }
    public void finishedDeparture(int flightNumber) {
        flightService.boardingDepartureFinished();
        workService.startWork(flightNumber, ServiceType.LUGGAGE_SERVICE, true);
        workService.completeStage(flightNumber, ServiceType.BOARDING_SERVICE, true);
    }


}
