package com.example.application.server.service.state;

import com.example.application.server.model.MessageToGeneralManagerService;
import com.example.application.server.service.FlightService;

public class ReadyState extends AbstractFlightState {
    public ReadyState(FlightService context) {
        super(context);
    }


    @Override
    public void pilotLanded(int flightNumber) {
        this.context.changeState(new GeneralManagerState(this.context));
        this.context.sendMessageToGeneralManagerService.accept(new MessageToGeneralManagerService(flightNumber));    }
}