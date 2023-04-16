package com.example.application.server.service.state;

import com.example.application.server.model.MessageAssignTimeFromStandManager;
import com.example.application.server.model.MessageFromGeneralManagerService;
import com.example.application.server.service.FlightService;

public class AbstractFlightState implements FlightState {
    protected final FlightService context;

    public AbstractFlightState(FlightService context) {
        this.context = context;
    }

    @Override
    public void pilotLanded(int flightNumber) {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: ReadyState actual: " + thisClassName);
    }

    @Override
    public void sendMessageFromGeneralManager(MessageFromGeneralManagerService messageFromGeneralManagerService) {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: GeneralManagerState actual: " + thisClassName);
    }

    @Override
    public void sendMessageFromStandManager(MessageAssignTimeFromStandManager messageAssignTimeFromStandManagerService) {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: StandManagerState actual: " + thisClassName);
    }

    @Override
    public void standManagerFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: StandManagerState actual: " + thisClassName);
    }

    @Override
    public void luggageFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: LuggageArrivalState actual: " + thisClassName);
    }

    @Override
    public void boardingFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: BoardingArrivalState actual: " + thisClassName);
    }

    @Override
    public void cleaningFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: CleaningState actual: " + thisClassName);
    }

    @Override
    public void tankingFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: TankingState actual: " + thisClassName);
    }

    @Override
    public void cateringFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: CateringState actual: " + thisClassName);
    }

    @Override
    public void luggageDepartureFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: LuggageDepartureState actual: " + thisClassName);
    }

    @Override
    public void boardingDepartureFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: BoardingDepartureState actual: " + thisClassName);
    }

    @Override
    public void pilotFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: PilotToPushbackState actual: " + thisClassName);
    }

    @Override
    public void pushbackFinished() {
        var thisClassName = this.getClass().getName();
        throw new RuntimeException("bad state, expected: PushbackState actual: " + thisClassName);
    }
}
