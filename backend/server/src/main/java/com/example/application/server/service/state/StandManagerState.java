package com.example.application.server.service.state;

import com.example.application.server.model.MessageAssignTimeFromStandManager;
import com.example.application.server.model.MessageAssignTimeToService;
import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;

public class StandManagerState extends AbstractFlightState {
    public StandManagerState(FlightService context) {
        super(context);
    }

    @Override
    public void standManagerFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));
        this.context.changeState(new LuggageArrivalState(this.context));
        this.context.sendMessageToLuggageService.accept(messageStartToService);
    }

    @Override
    public void sendMessageFromStandManager(MessageAssignTimeFromStandManager messageAssignTimeFromStandManagerService) {

        var messageToService = new MessageToService(new MessageAssignTimeToService(messageAssignTimeFromStandManagerService.message(),
                messageAssignTimeFromStandManagerService.minutes()), null);

        switch (messageAssignTimeFromStandManagerService.service()) {
            case LUGGAGE_SERVICE -> this.context.sendMessageToLuggageService.accept(messageToService);
            case BOARDING_SERVICE -> this.context.sendMessageToBoardingService.accept(messageToService);
            case CLEANING_SERVICE -> this.context.sendMessageToCleaningService.accept(messageToService);
            case CATERING_SERVICE -> this.context.sendMessageToCateringService.accept(messageToService);
            case TANKING_SERVICE -> this.context.sendMessageToTankingService.accept(messageToService);
            case PUSHBACK_SERVICE -> this.context.sendMessageToPushbackService.accept(messageToService);
        }
    }
}
