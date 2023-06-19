package com.example.application.server.controller;

import com.example.application.server.model.MessageAssignTimeFromStandManager;
import com.example.application.server.model.MessageToStandManagerService;
import com.example.application.server.service.AirportService;
import com.example.application.server.service.Services;
import com.example.application.server.service.StandManagerService;
import io.swagger.v3.oas.annotations.Hidden;
import org.springframework.web.bind.annotation.*;

@RestController()
@RequestMapping("standmanager")
@Hidden
public class StandManagerController extends MessageController<MessageToStandManagerService, StandManagerService> {

    protected StandManagerController(AirportService airportService) {
        super(airportService, Services::standManagerService);
    }

    @PostMapping("/sendmessage")
    public void postMessage(@RequestBody MessageAssignTimeFromStandManager messageAssignTimeFromStandManager) {
        airportService.getMessage(messageAssignTimeFromStandManager.flightNumber()).standManagerService().sendMessage(messageAssignTimeFromStandManager);
    }

    @PostMapping("/finished/{flightNumber}")
    public void postMessage(@PathVariable int flightNumber) {
        airportService.getMessage(flightNumber).standManagerService().finished(flightNumber);
    }

}
