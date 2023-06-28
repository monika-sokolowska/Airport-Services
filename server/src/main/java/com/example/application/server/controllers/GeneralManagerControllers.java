package com.example.application.server.controllers;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.exceptions.FlightNotFoundException;
import com.example.application.server.services.EmployeeService2;
import com.example.application.server.services.FlightService2;
import com.example.application.server.services.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.boot.context.properties.bind.DefaultValue;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.UUID;

@RequestMapping("/generalManager")
@RestController
@AllArgsConstructor
@CrossOrigin
public class GeneralManagerControllers {

    private EmployeeService2 employeeService;
    private RoleService roleService;
    private FlightService2 flightService;

    @GetMapping("/standManagers")    public ResponseEntity<List<EmployeeDTO>> availableStandManager()
    {        List<Employee> standManager;
        int numberOfFlights = 3;
        try {            standManager = employeeService.getAvailableStandMangers(roleService.getRoleByName("stand manager"), numberOfFlights);
        } catch (EmployeeNotFoundException | RoleNotFoundException e)
        {            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();        }
        return ResponseEntity.ok(standManager.stream().map(employeeService::convertEmployeeToEmployeeDTO).toList());
    }

    @PostMapping("/{standManagerId}/assigStandManager")
    public void assignStandManager(@PathVariable UUID standManagerId,@RequestParam UUID flightId, @RequestParam String message,@RequestParam Integer timeToService ) {
        try {
            Employee standManger = employeeService.getEmployeeById(standManagerId);
            flightService.updateFlightInfo(flightId, standManger, message, timeToService);
        } catch (EmployeeNotFoundException | FlightNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }
    }

    @GetMapping("/flightDeparture")
    public ResponseEntity<List<FlightDTO>> flightsDeparture(@RequestParam(defaultValue = "departure") String status){
        List<Flight> flightsByStatus = flightService.getFlightsByStatus(status);
        return ResponseEntity.ok(flightsByStatus.stream().map(flightService::convertFlightToDto).toList());
    }
}



