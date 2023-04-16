package com.example.application.server.service;

import com.example.application.server.model.MessageToService;
import com.example.application.server.model.ServiceType;

public class TankingService extends MessageQueue<MessageToService> {
    private final FlightService flightService;
    private final WorkService workService;

    public TankingService(FlightService flightService, WorkService workService) {
        this.flightService = flightService;
        this.workService = workService;

        this.flightService.onSendMessageToTankingService(messageQueue::add);
    }
    public void finished(int flightNumber) {
        flightService.tankingFinished();
        workService.startWork(flightNumber, ServiceType.CATERING_SERVICE);
        workService.completeStage(flightNumber, ServiceType.TANKING_SERVICE);
    }

}
