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
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.ArraySchema;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import javax.management.relation.RoleNotFoundException;
import java.util.List;
import java.util.UUID;

@Tag(name = "General Manager")
@RequestMapping("/generalManager")
@RestController
@AllArgsConstructor
@CrossOrigin
public class GeneralManagerControllers {

    private EmployeeService2 employeeService;
    private RoleService roleService;
    private FlightService2 flightService;

    @Operation(summary = "Get list of available stand managers.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of stand managers with assigned less than 3 flights.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = EmployeeDTO.class),
                                            minItems = 0,
                                            uniqueItems = true
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "No stand managers in the database or role name in the code and database are different. " +
                                    "Contact with backend to resolve this problem.",
                            content = @Content()
                    )
            }
    )
    @GetMapping("/standManagers")
    public ResponseEntity<List<EmployeeDTO>> availableStandManager() {
        List<Employee> standManager;
        int numberOfFlights = 3;
        try  {
            standManager = employeeService.getAvailableStandMangers(roleService.getRoleByName("stand manager"), numberOfFlights);
        } catch (EmployeeNotFoundException | RoleNotFoundException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
        return ResponseEntity.ok(standManager.stream().map(employeeService::convertEmployeeToEmployeeDTO).toList());
    }


    @Operation(summary = "Assign stand manager to the flight.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Assigned successfully.",
                            content = @Content()
                    ),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Stand manager or flight not found.",
                            content = @Content()
                    )
            }
    )
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

    @Operation(summary = "Get flights with given status.")
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "List of all flights with given status.",
                            content = @Content(
                                    mediaType = "application/json",
                                    array = @ArraySchema(
                                            schema = @Schema(implementation = FlightDTO.class),
                                            minItems = 0,
                                            uniqueItems = true)
                            )
                    )
            }
    )
    @GetMapping("/flightDeparture")
    @Transactional
    public ResponseEntity<List<FlightDTO>> flightsDeparture(@RequestParam(defaultValue = "departure") String status){
        List<Flight> flightsByStatus = flightService.getFlightsByStatus(status);
        return ResponseEntity.ok(flightsByStatus.stream().map(flightService::convertFlightToDto).toList());
    }
}



