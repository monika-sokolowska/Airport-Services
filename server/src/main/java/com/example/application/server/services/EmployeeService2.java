package com.example.application.server.services;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.entities.Department;
import com.example.application.server.entities.Employee;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.repositories.EmployeeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
public class EmployeeService2 {
    private final EmployeeRepository employeeRepository;

    @Autowired
    public EmployeeService2(EmployeeRepository employeeRepository) {
        this.employeeRepository = employeeRepository;
    }


    public Employee getEmployeeById(UUID id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given id not found."));
    }

    public Employee getEmployeeByEmail(String email) throws EmployeeNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given email not found."));
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        String service;
        try {
            service = employee.getDepartments()
                    .stream()
                    .map(Department::getName)
                    .toList().get(0);
        } catch (IndexOutOfBoundsException e) {
            service = "";
        }

        return new EmployeeDTO(
                employee.getId(),
                employee.getEmployeeDetails().getFullName(),
                employee.getEmail(),
                employee.getRole().getRole(),
                service);

//        return new EmployeeDTO(
//                employee.getId(),
//                employee.getEmployeeDetails().getFullName(),
//                employee.getEmail(),
//                employee.getRole().getRole(),
//                employee.getDepartments()
//                        .stream()
//                        .map(Department::getName)
//                        .toList());
    }

}
