package com.example.application.server.controller;

import com.example.application.server.service.AirportService;
import com.example.application.server.service.MessageQueue;
import com.example.application.server.service.Services;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.function.Function;

public abstract class MessageController<TMessage, TService extends MessageQueue<TMessage>> {

    protected final AirportService airportService;
    private final Function<Services, TService> serviceSelector;

    protected MessageController(AirportService airportService, Function<Services, TService> serviceSelector) {
        this.airportService = airportService;
        this.serviceSelector = serviceSelector;
    }

    @GetMapping("/getmessage")
    public TMessage getMessage(@RequestParam int flightNumber) {
        var get = airportService.getMessage(flightNumber);
        var service = serviceSelector.apply(get);
        return service.getMessage();
    }


}
