package com.example.application.server.controller;

import com.example.application.server.model.MessageToService;
import com.example.application.server.service.AirportService;
import com.example.application.server.service.CleaningService;
import com.example.application.server.service.Services;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("cleaning")
@Hidden
public class CleaningServiceController extends MessageController<MessageToService, CleaningService>{

    protected CleaningServiceController(AirportService airportService) {
        super(airportService, Services::cleaningService);
    }

    @PostMapping("/finished/{flightNumber}")
    public void postMessage(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).cleaningService().finished(flightNumber);
    }
}
