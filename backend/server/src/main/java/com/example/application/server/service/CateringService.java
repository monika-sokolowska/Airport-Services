package com.example.application.server.service;

import com.example.application.server.model.MessageToService;
import com.example.application.server.model.ServiceType;

public class CateringService extends MessageQueue<MessageToService> {
    private final FlightService flightService;
    private final WorkService workService;

    public CateringService(FlightService flightService, WorkService workService) {
        this.flightService = flightService;
        this.workService = workService;

        this.flightService.onSendMessageToCateringService(messageQueue::add);
    }

    public void finished(int flightNumber) {
        flightService.cateringFinished();
        workService.startWork(flightNumber, ServiceType.BOARDING_SERVICE, true);
        workService.completeStage(flightNumber, ServiceType.CATERING_SERVICE);
    }
}
