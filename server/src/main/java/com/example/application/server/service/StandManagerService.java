package com.example.application.server.service;

import com.example.application.server.model.MessageAssignTimeFromStandManager;
import com.example.application.server.model.MessageToStandManagerService;
import com.example.application.server.model.ServiceType;

public class StandManagerService extends MessageQueue<MessageToStandManagerService> {
    private final FlightService flightService;
    private final WorkService workService;

    public StandManagerService(FlightService flightService, WorkService workService) {
        this.flightService = flightService;
        this.workService = workService;

        this.flightService.onSendMessageToStandManagerService(messageQueue::add);
    }

    public void sendMessage(MessageAssignTimeFromStandManager messageAssignTimeFromStandManagerService) {
        workService.createAndAssignWork(messageAssignTimeFromStandManagerService);
        flightService.sendMessageFromStandManager(messageAssignTimeFromStandManagerService);
    }
    public void finished(int flightNumber) {
        workService.startWork(flightNumber, ServiceType.LUGGAGE_SERVICE);
        flightService.standManagerFinished();
    }

}