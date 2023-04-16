package com.example.application.server.controller;

import com.example.application.server.model.WorkOrder;
import com.example.application.server.service.WorkService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("work")
public class WorkOrderServiceController {

    private final WorkService service;

    public WorkOrderServiceController(WorkService service) {
        this.service = service;
    }

    @GetMapping("/all")
    public List<WorkOrder> getAllWorkOrders() {
        return service.getAll();
    }

    @GetMapping("/all/{flightNumber}")
    public List<WorkOrder> getWorkOrdersByFlight(@PathVariable int flightNumber) {
        return service.getByFlightNumber(flightNumber);
    }

}
