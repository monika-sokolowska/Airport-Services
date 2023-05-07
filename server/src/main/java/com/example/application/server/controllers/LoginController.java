package com.example.application.server.controllers;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.DTOs.LoginDTO;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.entities.Employee;
import com.example.application.server.services.EmployeeService2;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RequestMapping("/login")
@RestController
@CrossOrigin
public class LoginController {

    private final EmployeeService2 employeeService;

    public LoginController(EmployeeService2 employeeService) {
        this.employeeService = employeeService;
    }

    @PostMapping
    public ResponseEntity<EmployeeDTO> login(@RequestBody LoginDTO loginDTO) {
        try {
            Employee employee = employeeService.getEmployeeByEmail(loginDTO.email());
            if(!employee.getPassword().equals(loginDTO.password()))
                throw new EmployeeNotFoundException("Employee with given password not found.");
            return ResponseEntity.ok(employeeService.convertEmployeeToEmployeeDTO(employee));
        } catch (EmployeeNotFoundException e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
        }

    }
}
