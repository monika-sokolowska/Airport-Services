package com.example.application.server.controller;

import com.example.application.server.model.MessageToService;
import com.example.application.server.service.AirportService;
import com.example.application.server.service.LuggageService;
import com.example.application.server.service.Services;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("luggage")
public class LuggageServiceController extends MessageController<MessageToService, LuggageService>{

    protected LuggageServiceController(AirportService airportService) {
        super(airportService, Services::luggageService);
    }

    @PostMapping("/finished/{flightNumber}")
    public void postMessage(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).luggageService().finished(flightNumber);
    }

    @PostMapping("/finisheddeparture/{flightNumber}")
    public void postDepartureMessage(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).luggageService().finishedDeparture(flightNumber);
    }
}
