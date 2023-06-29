package com.example.application.server.controllers;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.services.EmployeeService2;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.application.server.security.Utils.GetCurrentUser;

@Tag(name = "Employees")
@RequestMapping("/employee")
@AllArgsConstructor
@RestController
@CrossOrigin
public class EmployeeController {
    EmployeeService2 employeeService;

    @Operation(
            summary = "Get employee details.",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = EmployeeDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            content = @Content(
                                    examples = @ExampleObject (
                                            name = "ERROR",
                                            description ="User has no permission or is not logged"
                                    )
                            )
                    )
            }
    )
    @GetMapping("/get")
    public ResponseEntity<EmployeeDTO> getUser() {
        try {
            Employee employee = GetCurrentUser();
            return ResponseEntity.ok(employeeService.convertEmployeeToEmployeeDTO(employee));
        } catch(Exception e) {
            return  ResponseEntity.badRequest().build();
        }

    }

}
