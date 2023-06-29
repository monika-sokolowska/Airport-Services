package com.example.application.server.controllers;

import com.example.application.server.DTOs.AuthenticationDTO;
import com.example.application.server.DTOs.LoginDTO;
import com.example.application.server.DTOs.RegisterDTO;
import com.example.application.server.entities.*;
import com.example.application.server.exceptions.DepartmentNotFoundException;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.services.*;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;


import java.util.UUID;

@Tag(name = "Security", description = "login, logout, register")
@CrossOrigin
@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class SecurityController {

    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;


    private final EmployeeService2 employeeService;
    private final EmployeeDetailsService employeeDetailsService;
    private final RoleService roleService;
    private final TokenService tokenService;
    private final DepartmentService departmentService;


    @Operation(
            summary = "Log in user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Success",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationDTO.class)
                            )
                    ),
                    @ApiResponse(
                            description = "Email or password is incorrect",
                            responseCode = "400",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/login")
    public ResponseEntity<AuthenticationDTO> login(@RequestBody LoginDTO loginDTO) {

        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginDTO.email(),
                        loginDTO.password()
                )
        );
        Employee employee;

        try {
            employee = employeeService.getEmployeeByEmail(loginDTO.email());
        } catch (EmployeeNotFoundException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().build();
        }

        String jwtToken = tokenService.createToken(employee);
        tokenService.saveToken(employee, jwtToken);
        return ResponseEntity.ok(new AuthenticationDTO(jwtToken));
    }


    @Operation(
            summary = "Log out user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Log out user",
                            content = @Content()
                    )
            }
    )
    @PostMapping("/logout")
    public ResponseEntity<?> logout(@RequestBody AuthenticationDTO authenticationDTO) {
        final String jwt = authenticationDTO.token();
        Token token = tokenService.findByToken(jwt).orElse(null);
        if (token != null) {
            token.setExpired(true);
            tokenService.saveToken(token);
        }
        return ResponseEntity.ok().build();
    }


    @Operation(
            summary = "Register new user",
            responses = {
                    @ApiResponse(
                            responseCode = "200",
                            description = "Register success.",
                            content = @Content(
                                    mediaType = "application/json",
                                    schema = @Schema(implementation = AuthenticationDTO.class)
                            )
                    ),
                    @ApiResponse(
                            responseCode = "400",
                            description = "Request body data is incorrect.",
                            content = @Content(
                                    examples = {
                                            @ExampleObject(
                                                    name = "Email is taken",
                                                    value = "Email is taken",
                                                    description = "Email is already taken."
                                            ),
                                            @ExampleObject(
                                                    name = "Bad register data",
                                                    value = "Register DTO invalid",
                                                    description = "Something in request body is incorrect."
                                            ),
                                            @ExampleObject(
                                                    name = "Bad department",
                                                    value = "Department with given name not found: $departmentName",
                                                    description = "Department doesn't exists."
                                            )
                                    }
                            )
                    )
            }
    )
    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(final @RequestBody RegisterDTO registerDTO) {

        Role role;
        Department department;

        try {
            role = roleService.getRoleByName(registerDTO.roleName());

            department = departmentService.getDepartmentByName(registerDTO.departmentName())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department with given name not found: " + registerDTO.departmentName()));

            if (employeeService.isEmailTaken(registerDTO.email()))
                throw new Exception("Email is taken");

            if (!isDataCorrect(registerDTO))
                throw new Exception("Register DTO invalid");

        } catch (Exception e) {
            return ResponseEntity.badRequest().body(e.getMessage());
        }


        EmployeeDetails employeeDetails = EmployeeDetails.builder()
                .id(UUID.randomUUID())
                .name(registerDTO.name())
                .surname(registerDTO.surname())
                .build();

        Employee employee = Employee.builder()
                .id(UUID.randomUUID())
                .role(role)
                .department(department)
                .employeeDetails(employeeDetails)
                .email(registerDTO.email())
                .password(passwordEncoder.encode(registerDTO.password()))
                .build();

        employeeDetailsService.save(employeeDetails);
        employeeService.save(employee);

        String token = tokenService.createToken(employee);
        tokenService.saveToken(employee, token);


        return ResponseEntity.ok(new AuthenticationDTO(token));
    }

    private boolean isDataCorrect(RegisterDTO registerDTO) {
        return (
                !registerDTO.name().isEmpty()
                        && !registerDTO.surname().isEmpty()
                        && !registerDTO.email().isEmpty()
                        && !registerDTO.password().isEmpty());

    }


}
