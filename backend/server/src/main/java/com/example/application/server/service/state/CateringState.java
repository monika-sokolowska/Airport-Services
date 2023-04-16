package com.example.application.server.service.state;

import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;

public class CateringState extends AbstractFlightState {
    public CateringState(FlightService context) {
        super(context);
    }

    @Override
    public void cateringFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));

        this.context.changeState(new LuggageDepartureState(this.context));
        this.context.sendMessageToLuggageService.accept(messageStartToService);
    }
}