package com.example.application.server.controllers;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.services.EmployeeService2;
import lombok.AllArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import static com.example.application.server.security.Utils.GetCurrentUser;

@RequestMapping("/employee")
@AllArgsConstructor
@RestController
@CrossOrigin
public class EmployeeController {
    EmployeeService2 employeeService;

    @GetMapping("/get")
    public ResponseEntity<EmployeeDTO> getUser() {
        Employee employee = GetCurrentUser();
        return ResponseEntity.ok(employeeService.convertEmployeeToEmployeeDTO(employee));
    }

}
