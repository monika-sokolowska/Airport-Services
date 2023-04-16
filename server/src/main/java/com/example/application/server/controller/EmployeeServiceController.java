package com.example.application.server.controller;

import com.example.application.server.model.Employee;
import com.example.application.server.service.EmployeeService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController()
@RequestMapping("employee")
public class EmployeeServiceController {

    private EmployeeService service;

    public EmployeeServiceController(EmployeeService service) {
        this.service = service;
    }

    @GetMapping("/allEmployees")
    public List<Employee> getAllEmployees() {
        return service.getAllEmployees();
    }
}
