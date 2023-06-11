package com.example.application.server.controllers;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.services.EmployeeService2;
import com.example.application.server.services.FlightService2;
import com.example.application.server.services.ServicesService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@RequestMapping("/standManager")
@RestController
@AllArgsConstructor
public class StandManagerControllers {
    private FlightService2 flightService;
    private EmployeeService2 employeeService;
    private ServicesService servicesService;

    @GetMapping("{standManagerId}/getFlights")
    public ResponseEntity<List<FlightDTO>> assignedFlights(@PathVariable UUID standManagerId) {
        List<Flight> departure;
        try {
            Employee standManger = employeeService.getEmployeeById(standManagerId);
            departure = flightService.getFlightsByStandManager(standManger).stream().filter(flight -> !flight.getStatus().getStatus().equals("departure")).toList();
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok(departure.stream().map(flightService::convertFlightToDto).toList());
    }

    @GetMapping("{standManagerId}/getEmployees")
    public ResponseEntity<List<EmployeeDTO>> getAvailableEmployees(@PathVariable UUID standManagerId, @RequestParam boolean isBusy
    ) {
        List<Employee> employees;
        try {
            Employee standManger = employeeService.getEmployeeById(standManagerId);
            employees = employeeService.getAvailableEmployee(isBusy);
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok(employees.stream().map(employeeService::convertEmployeeToEmployeeDTO).toList());

    }

    @PostMapping("{employeeId}/assignEmployee")
    public ResponseEntity<EmployeeDTO> assignStandManager(@PathVariable UUID employeeId, @RequestParam UUID flightId, @RequestParam String message, @RequestParam Integer timeToService) {
        Employee employee;
        try {
            employee = employeeService.getEmployeeById(employeeId);
            Optional<Flight> flightById = flightService.getFlightById(flightId);
            if (flightById.isEmpty()){
                return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
            }
            servicesService.createService(employee, employee.getDepartment(), flightById.get(), timeToService);
            employeeService.updateBusy(employee, true);
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok(employeeService.convertEmployeeToEmployeeDTO(employee));

    }

    @PostMapping("{standManagerId}/finished")
    public void finished(@PathVariable UUID standManagerId) {
        // TODO co tu ma sie wydarzyc
    }

}
