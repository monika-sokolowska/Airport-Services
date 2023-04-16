package com.example.application.server.service;

import com.example.application.server.model.MessageFromGeneralManagerService;
import com.example.application.server.model.MessageToGeneralManagerService;


public class GeneralManagerService extends MessageQueue<MessageToGeneralManagerService> {

    private final FlightService flightService;


    public GeneralManagerService(FlightService flightService) {
        this.flightService = flightService;

        this.flightService.onSendMessageToGeneralManagerService(messageQueue::add);
    }
    public void sendMessage(MessageFromGeneralManagerService messageFromGeneralManagerService) {
        flightService.sendMessageFromGeneralManager(messageFromGeneralManagerService);
    }

}
