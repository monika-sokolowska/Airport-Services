package com.example.application.server.controllers;

import com.example.application.server.DTOs.AuthenticationDTO;
import com.example.application.server.DTOs.LoginDTO;
import com.example.application.server.DTOs.RegisterDTO;
import com.example.application.server.entities.*;
import com.example.application.server.exceptions.DepartmentNotFoundException;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.services.*;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.management.relation.RoleNotFoundException;
import java.math.BigDecimal;
import java.util.Optional;
import java.util.UUID;

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


    @Transactional
    @PostMapping("/register")
    public ResponseEntity<?> registerUser(final @RequestBody RegisterDTO registerDTO) {

        Role role;
        Department department;

        try {
            role = roleService.getRoleByRole(registerDTO.roleName())
                    .orElseThrow(() -> new RoleNotFoundException("Role with given name not fount: " + registerDTO.roleName()));

            department = departmentService.getDepartmentByName(registerDTO.departmentName())
                    .orElseThrow(() -> new DepartmentNotFoundException("Department with given name not fount: " + registerDTO.departmentName()));

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
