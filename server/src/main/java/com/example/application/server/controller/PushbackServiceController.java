package com.example.application.server.controller;

import com.example.application.server.model.MessageToService;
import com.example.application.server.service.AirportService;
import com.example.application.server.service.PushbackService;
import com.example.application.server.service.Services;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController()
@RequestMapping("pushback")
@Hidden
public class PushbackServiceController extends MessageController<MessageToService, PushbackService> {

    protected PushbackServiceController(AirportService airportService) {
        super(airportService, Services::pushbackService);
    }

    @PostMapping("/finished/{flightNumber}")
    public void postMessage(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).pushbackService().finished(flightNumber);
    }
}
