package com.example.application.server.service.state;

import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;


public class LuggageArrivalState extends AbstractFlightState {
    public LuggageArrivalState(FlightService context) {
        super(context);
    }

    @Override
    public void luggageFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));

        this.context.changeState(new BoardingArrivalState(this.context));
        this.context.sendMessageToBoardingService.accept(messageStartToService);
    }
}
