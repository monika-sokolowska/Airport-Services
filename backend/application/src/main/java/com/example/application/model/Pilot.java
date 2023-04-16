package com.example.application.model;

import com.example.application.service.WorkService;

public class Pilot extends Employee {
    public Pilot(WorkService workService) {
        super(Task.ENGINE_OFF, workService);
    }

    public void sendFlightInformation(Flight flight, PositionManager positionManager) {
        positionManager.assignWork(flight.getFlightNumber(), getResponsibility());
    }
}
