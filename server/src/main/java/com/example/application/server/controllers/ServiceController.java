package com.example.application.server.controllers;

import com.example.application.server.DTOs.ServiceDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Service;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.services.EmployeeService2;
import com.example.application.server.services.EmployeesServicesService;
import com.example.application.server.services.ServicesService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Services")
@RequestMapping("/service")
@AllArgsConstructor
@RestController
@CrossOrigin
public class ServiceController {
    private final ServicesService servicesService;
    private final EmployeeService2 employeeService;
    private final EmployeesServicesService employeesServicesService;


    @Operation(summary = "Get current service assigned to employee")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = ServiceDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "204",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Employee has no services now",
                                            description = "Employee has no services assigned in this moment."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Employee not found",
                                            description = "No employee with give id in the database."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Multiple services in the same time window",
                                            description = "Employee has multiple services assigned in the same time."
                                    )
                            )
                    ),

            }
    )
    @GetMapping("/getServiceInfo/{employeeId}")
    public ResponseEntity<ServiceDTO> getServiceInfoByEmployeeId(@PathVariable final UUID employeeId) {

        final Employee employee;
        try {
            employee = employeeService.getEmployeeById(employeeId);
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        List<Service> servicesList = employee.getServices().stream()
                .filter(s -> s.getServiceStart().isBefore(LocalDateTime.now()))
                .filter(s -> s.getServiceEnd().isAfter(LocalDateTime.now()))
                .toList();

        if(servicesList.isEmpty())
            return  ResponseEntity.noContent().build(); // no current services assigned to employee
        if(servicesList.size() != 1)
            return ResponseEntity.internalServerError().build(); // Employee has many services simultaneously

        final Service service = servicesList.get(0);

        return ResponseEntity.ok(ServicesService.convertServiceToDto(service));
    }


}
