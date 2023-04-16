package com.example.application.server.service.state;

import com.example.application.server.model.MessageStartToService;
import com.example.application.server.model.MessageToService;
import com.example.application.server.service.FlightService;

public class BoardingArrivalState extends AbstractFlightState {
    public BoardingArrivalState(FlightService context) {
        super(context);
    }

    @Override
    public void boardingFinished() {
        var messageStartToService = new MessageToService(null, new MessageStartToService("START"));

        this.context.changeState(new CleaningState(this.context));
        this.context.sendMessageToCleaningService.accept(messageStartToService);
    }
}
