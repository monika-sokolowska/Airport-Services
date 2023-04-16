package com.example.application.server.service.state;

import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;


public class PilotToPushbackState extends AbstractFlightState {
    public PilotToPushbackState(FlightService context) {
        super(context);
    }

    @Override
    public void pilotFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));

        this.context.changeState(new PushbackState(this.context));
        this.context.sendMessageToPushbackService.accept(messageStartToService);
    }
}