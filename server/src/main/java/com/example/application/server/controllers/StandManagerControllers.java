package com.example.application.server.controllers;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Service;
import com.example.application.server.entities.Status;
import com.example.application.server.enums.StatusesEnum;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.exceptions.FlightNotFoundException;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.services.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.transaction.Transactional;
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
@CrossOrigin
public class StandManagerControllers {
    private FlightService2 flightService;
    private EmployeeService2 employeeService;
    private ServicesService servicesService;
    private final StatusService statusService;
    private final EmployeesServicesService employeesServicesService;

    @GetMapping("{standManagerId}/getFlights")
    public ResponseEntity<List<FlightDTO>> assignedFlights(@PathVariable UUID standManagerId) {
        // TODO add messages*
        List<Flight> departure;
        try {
            Employee standManger = employeeService.getEmployeeById(standManagerId);
            departure = flightService.getFlightsByStandManager(standManger).stream()
                    .filter(flight -> !flight.getStatus().getStatus().equals(StatusesEnum.STATUS_DEPARTURE.getStatusName()))
                    .toList();
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
            Service service = servicesService.createService(employee, employee.getDepartment(), flightById.get(), timeToService, message);
            employeesServicesService.assignEmployeeToFlight(employeeId, service.getId());
            employeeService.updateBusy(employee, true);
        } catch (EmployeeNotFoundException e) {
            throw new ResponseStatusException(
                    HttpStatus.NOT_FOUND, e.getMessage(), e);
        }

        return ResponseEntity.ok(employeeService.convertEmployeeToEmployeeDTO(employee));

    }


    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Flight doesn't exist",
                                            value = "Flight with given ID not found $ID",
                                            description = "Flight with given ID doesn't exist in the database"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    name = "Bad stand manager",
                                                    value = "This manager is not assigned to the flight",
                                                    description = "Flight in the database has different stand manager."
                                            ),
                                            @ExampleObject(
                                                    name = "Already assigned",
                                                    value = "Services for this flight are already assigned",
                                                    description = "This operation has already been performed"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Success",
                                            value = "Flight status has been changed to: luggageArrival",
                                            description = "Value of flight's status in the database has been updated"
                                    )
                            )
                    )
            }
    )
    @Transactional
    @PostMapping("/{standManagerId}/finished/{flightId}")
    public ResponseEntity<String> finished(@PathVariable final UUID standManagerId, @PathVariable final UUID flightId) {

        StatusesEnum startingService = StatusesEnum.STATUS_LUGGAGE_ARRIVAL;
        try {
            final Flight flight = flightService.getFlightById(flightId)
                    .orElseThrow(() -> new FlightNotFoundException("Flight with given ID not found " + flightId));
            final int flightStatusCode = StatusesEnum.getStatusEnumByStatusName(flight.getStatus().getStatus()).getStatusCode();


            if (flight.getStandManager() == null || !standManagerId.equals(flight.getStandManager().getId()))
                return ResponseEntity.badRequest().body("This manager is not assigned to the flight");

            if (flightStatusCode >= startingService.getStatusCode())
                return ResponseEntity.badRequest().body("Services for this flight are already assigned");

            final Status status = statusService.getStatusByStatusName(startingService.getStatusName());

            flight.setStatus(status);
            flightService.saveFlight(flight);

            return ResponseEntity.ok("Flight status has been changed to: " + startingService.getStatusName());

        } catch (FlightNotFoundException | StatusNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            e.printStackTrace();
            return null;
        }


    }

}
