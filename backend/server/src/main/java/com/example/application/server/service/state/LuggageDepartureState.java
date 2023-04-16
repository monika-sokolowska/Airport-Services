package com.example.application.server.service.state;

import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;


public class LuggageDepartureState extends AbstractFlightState {
    public LuggageDepartureState(FlightService context) {
        super(context);
    }

    @Override
    public void luggageDepartureFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));

        this.context.changeState(new BoardingDepartureState(this.context));
        this.context.sendMessageToBoardingService.accept(messageStartToService);

    }
}