package com.example.application.server.controller;

import com.example.application.server.model.MessageFromGeneralManagerService;
import com.example.application.server.model.MessageToGeneralManagerService;
import com.example.application.server.service.AirportService;
import com.example.application.server.service.GeneralManagerService;
import com.example.application.server.service.Services;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("generalmanager")
@Hidden
public class GeneralManagerController extends MessageController<MessageToGeneralManagerService, GeneralManagerService>{


    protected GeneralManagerController(AirportService airportService) {
        super(airportService, Services::generalManagerService);
    }

    @PostMapping("/finished")
    public void postMessage(@RequestBody MessageFromGeneralManagerService messageFromGeneralManagerService) {
        airportService.getMessage(messageFromGeneralManagerService.flightNumber()).generalManagerService().sendMessage(messageFromGeneralManagerService);
    }

}
