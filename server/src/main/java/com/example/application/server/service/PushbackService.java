package com.example.application.server.service;

import com.example.application.server.model.MessageToService;
import com.example.application.server.model.ServiceType;

public class PushbackService extends MessageQueue<MessageToService> {
    private final FlightService flightService;
    private final WorkService workService;

    public PushbackService(FlightService flightService, WorkService workService) {
        this.flightService = flightService;
        this.workService = workService;

        this.flightService.onSendMessageToPushbackService(messageQueue::add);
    }

    public void finished(int flightNumber) {
        flightService.pushbackFinished();
        workService.completeStage(flightNumber, ServiceType.PUSHBACK_SERVICE);
    }
}
