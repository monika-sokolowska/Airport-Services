package com.example.application.server.service.state;

import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;

public class TankingState extends AbstractFlightState {
    public TankingState(FlightService context) {
        super(context);
    }

    @Override
    public void tankingFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));
        this.context.changeState(new CateringState(this.context));
        this.context.sendMessageToCateringService.accept(messageStartToService);
    }
}