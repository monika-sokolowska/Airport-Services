package com.example.application.server.service.state;

import com.example.application.server.model.MessageAssignTimeFromStandManager;
import com.example.application.server.model.MessageFromGeneralManagerService;

public interface FlightState {
    void pilotLanded(int flightNumber);
    void sendMessageFromGeneralManager(MessageFromGeneralManagerService messageFromGeneralManagerService);
    void sendMessageFromStandManager(MessageAssignTimeFromStandManager messageAssignTimeFromStandManagerService);
    void standManagerFinished();

    void luggageFinished();

    void boardingFinished();

    void cleaningFinished();

    void tankingFinished();

    void cateringFinished();

    void luggageDepartureFinished();

    void boardingDepartureFinished();

    void pilotFinished();

    void pushbackFinished();
}
