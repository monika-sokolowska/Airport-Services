package com.example.application.server.service;

import com.example.application.server.model.*;
import com.example.application.server.service.state.*;


import java.util.function.Consumer;

public class FlightService {
    public FlightState state;

    public Consumer<MessageToGeneralManagerService> sendMessageToGeneralManagerService;
    public Consumer<MessageToStandManagerService> sendMessageToStandManagerService;
    public Consumer<MessageToService> sendMessageToPilotService;
    public Consumer<MessageToService> sendMessageToLuggageService;
    public Consumer<MessageToService> sendMessageToBoardingService;
    public Consumer<MessageToService> sendMessageToCleaningService;
    public Consumer<MessageToService> sendMessageToCateringService;
    public Consumer<MessageToService> sendMessageToPushbackService;
    public Consumer<MessageToService> sendMessageToTankingService;

    public FlightService() {
        this.state = new ReadyState(this);
    }
    public void changeState(FlightState state) {
        this.state = state;
    }
    public void onSendMessageToPilotService(Consumer<MessageToService> action) {
        sendMessageToPilotService = action;
    }
    public void onSendMessageToGeneralManagerService(Consumer<MessageToGeneralManagerService> action) {
        sendMessageToGeneralManagerService = action;
    }
    public void onSendMessageToStandManagerService(Consumer<MessageToStandManagerService> action) {
        sendMessageToStandManagerService = action;
    }
    public void onSendMessageToLuggageService(Consumer<MessageToService> action) {
        sendMessageToLuggageService = action;
    }
    public void onSendMessageToBoardingService(Consumer<MessageToService> action) {
        sendMessageToBoardingService = action;
    }
    public void onSendMessageToCleaningService(Consumer<MessageToService> action) {
        sendMessageToCleaningService = action;
    }
    public void onSendMessageToCateringService(Consumer<MessageToService> action) {
        sendMessageToCateringService = action;
    }
    public void onSendMessageToTankingService(Consumer<MessageToService> action) {
        sendMessageToTankingService = action;
    }
    public void onSendMessageToPushbackService(Consumer<MessageToService> action) {
        sendMessageToPushbackService = action;
    }
    public void pilotLanded(int flightNumber) {
        this.state.pilotLanded(flightNumber);
    }
    public void sendMessageFromGeneralManager(MessageFromGeneralManagerService messageFromGeneralManagerService) {
        this.state.sendMessageFromGeneralManager(messageFromGeneralManagerService);
    }

    public void sendMessageFromStandManager(MessageAssignTimeFromStandManager messageAssignTimeFromStandManagerService) {
        this.state.sendMessageFromStandManager(messageAssignTimeFromStandManagerService);
    }

    public void standManagerFinished() {
        this.state.standManagerFinished();
    }

    public void luggageFinished() {
        this.state.luggageFinished();

    }

    public void boardingFinished() {
        this.state.boardingFinished();
    }

    public void cleaningFinished() {
        this.state.cleaningFinished();
    }

    public void tankingFinished() {
        this.state.tankingFinished();
    }

    public void cateringFinished() {
        this.state.cateringFinished();
    }

    public void luggageDepartureFinished() {
        this.state.luggageDepartureFinished();
    }

    public void boardingDepartureFinished() {
        this.state.boardingDepartureFinished();
    }

    public void pilotFinished() {
        this.state.pilotFinished();
    }

    public void pushbackFinished() {
        this.state.pushbackFinished();
    }



}
