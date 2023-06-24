package com.example.application.server.controllers;

import com.example.application.server.DTOs.FlightDTO;
import com.example.application.server.entities.Airplane;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import com.example.application.server.entities.Status;
import com.example.application.server.enums.StatusesEnum;
import com.example.application.server.exceptions.AirplaneNotFound;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.exceptions.FlightNotFoundException;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.services.*;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Tag(name = "Flights")
@RequestMapping("/flight")
@AllArgsConstructor
@RestController
@CrossOrigin
public class FlightController {

    private final FlightService2 flightService;
    private final AirplaneService airplaneService;
    private final StatusService statusService;
    private final EmployeeService2 employeeService;

    @GetMapping("/flightsByStatus")
    public ResponseEntity<List<FlightDTO>> getFlightsByStatus(@RequestParam(defaultValue = "landed") String status) {
        List<Flight> flights = flightService.getFlightsByStatus(status);
        List<FlightDTO> flightDTOS = flights.stream().map(flightService::convertFlightToDto).toList();
        return ResponseEntity.ok(flightDTOS);
    }

    /**
     * @param number number of the airplane
     * @return if airplane not found - NOT_FOUND
     *         if found and in the database, but already in service - BAD_REQUEST
     *         on success - OK
     */
    @PostMapping("/navigator/landed")
    @Transactional
    public ResponseEntity<FlightDTO> landAirplane(@RequestParam String number) {
        Airplane airplane;
        try {
            airplane = airplaneService.getByNumber(number);
        } catch (AirplaneNotFound a) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).build();
        }

        // the following checks if the airplane is already serviced
        List<Flight> flights = flightService.getFlightsByAirplaneNumber(number);
        if (!flights.isEmpty()) {

            try {
                long isAlreadyServiced = flights.stream()
                        .map(f -> f.getStatus().getStatus())
                        .filter(status -> {
                            try {
                                return StatusesEnum.getStatusEnumByStatusName(status).getStatusCode() >= StatusesEnum.STATUS_LUGGAGE_ARRIVAL.getStatusCode();
                            } catch (StatusNotFound e) {
                                throw new RuntimeException(e);
                            }
                        })
                        .count();
                if(isAlreadyServiced > 0)
                    return ResponseEntity.status(HttpStatus.BAD_REQUEST).build();
            } catch (RuntimeException e) {
                return ResponseEntity.internalServerError().build();
            }

        }

        Status status;
        try {
            status = statusService.getStatusByStatusName(StatusesEnum.STATUS_LANDED.getStatusName());
        } catch (StatusNotFound e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }

        Flight flight = new Flight(
                UUID.randomUUID(),
                airplane,
                status,
                null,
                LocalDateTime.now(),
                null,
                null
        );
        flightService.saveFlight(flight);

        return ResponseEntity.ok(flightService.convertFlightToDto(flight));

    }



    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    examples = {
                                            @ExampleObject (
                                                    name = "START",
                                                    value = "START",
                                                    description = "Employee's service is currently assigned to the flight"
                                            ),
                                            @ExampleObject (
                                                    name = "WAITING",
                                                    value = "WAITING",
                                                    description = "Employee's service will be assigned to the flight in the future"
                                            ),
                                            @ExampleObject (
                                                    name = "FINISHED",
                                                    value = "FINISHED",
                                                    description = "Employee's service was assigned to the flight in the past and won't be in the future"
                                            )
                                    }
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject (
                                            name = "ERROR",
                                            value = "ERROR",
                                            description =" Employee's service not assigned to the flight"
                                                    + "OR ids are incorrect"
                                                    + "OR employee/flight doesn't exist"
                                                    + "OR status from emp/flight doesn't exist"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/serviceStart/{employeeId}/{flightId}")
    public ResponseEntity<String> serviceStart(@PathVariable final UUID employeeId, @PathVariable final UUID flightId) {
        // as below or we could write custom function in order to optimize code (with less fetching)

        final String ERROR_MSG = "ERROR";
        String response = ERROR_MSG;

        final boolean isAssigned = employeeService.isEmployeeAssignedToFlight(employeeId, flightId);

        if(isAssigned)
            try {
                final Employee employee = employeeService.getEmployeeById(employeeId);
                final Flight flight = flightService.getFlightById(flightId)
                        .orElseThrow(() -> new FlightNotFoundException("Flight with given id not found. " + flightId));
                final String employeeDepartmentName = employee.getDepartment().getName();
                final String statusName = flight.getStatus().getStatus();

                if(StatusesEnum.isDepartmentAssignedToStatus(statusName, employeeDepartmentName))
                    return ResponseEntity.ok("START");

                final int compareResult = StatusesEnum.compareDepartmentToStatus(employeeDepartmentName, statusName);
                if(compareResult == 1)
                    response = "WAITING";
                else if(compareResult == -1)
                    response = "FINISHED";

            } catch (EmployeeNotFoundException | FlightNotFoundException | StatusNotFound e) {
                e.printStackTrace();
            }

        if(response.equals(ERROR_MSG))
            return ResponseEntity.badRequest().body(response);
        return ResponseEntity.ok(response);

    }

    /**
     * Change status to next in order
     */
    @ApiResponses(
            value = {
                    @ApiResponse(
                            responseCode = "404",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Wrong ID(s)",
                                            value = "X with given id not found: $id",
                                            description = "Flight, employee or status with given ID doesn't exist"
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "Bad service",
                                            value = "This user cannot finish current service."
                                                    + " User department: Navigator."
                                                    + " Flight status: tanking.",
                                            description = "Employee department and flight status aren't matching."
                                    )
                            )
                    ),
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    examples = @ExampleObject(
                                            name = "SUCCESS",
                                            value = "Flight status has been updated",
                                            description = "Status of the flight has been updated."
                                    )
                            )
                    )
            }
    )
    @Transactional
    @PostMapping("/finished/{employeeId}/{flightId}")
    public ResponseEntity<String> serviceFinished(@PathVariable final UUID employeeId, @PathVariable final UUID flightId) {

        try {
            Flight flight = flightService.getFlightById(flightId)
                    .orElseThrow(() -> new FlightNotFoundException("Flight with given id not found: " + flightId));
            Status status = flight.getStatus();

            final StatusesEnum currentStatus = StatusesEnum.getStatusEnumByStatusName(status.getStatus());
            final String employeeDepartment = employeeService.getEmployeeDepartment(employeeId);

            if(!StatusesEnum.isDepartmentAssignedToStatus(currentStatus.getStatusName(), employeeDepartment)) {
                // TODO ADD CUSTOM EXCEPTION
                throw new Exception("This user cannot finish service current Service." +
                        "\tUser department: " + employeeDepartment +
                        "\tFlight status: " + currentStatus.getStatusName());
            }

            flightService.finishFlightService(flight);

        } catch (FlightNotFoundException | StatusNotFound | EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }

        return ResponseEntity.ok("Flight status has been updated.");
    }


}
