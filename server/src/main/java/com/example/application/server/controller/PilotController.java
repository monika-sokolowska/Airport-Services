package com.example.application.server.controller;

import com.example.application.server.model.MessageToService;
import com.example.application.server.service.AirportService;
import com.example.application.server.service.PilotService;
import com.example.application.server.service.Services;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController()
@RequestMapping("pilot")
@Hidden
public class PilotController extends MessageController<MessageToService, PilotService> {


    protected PilotController(AirportService airportService) {
        super(airportService, Services::pilotService);
    }

    @PostMapping("/landed/{flightNumber}")
    public void postLanded(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).pilotService().landed(flightNumber);
    }

    @PostMapping("/finished/{flightNumber}")
    public void postMessage(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).pilotService().finished();
    }


}
