package com.example.application.server.services;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


import java.util.UUID;


@Service
@AllArgsConstructor
public class EmployeeService2 implements UserDetailsService {
    private final EmployeeRepository employeeRepository;


    public Employee getEmployeeById(UUID id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given id not found."));
    }

    public Employee getEmployeeByEmail(String email) throws EmployeeNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given email not found."));
    }

    public boolean isEmailTaken(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        return employee != null;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getEmployeeDetails().getFullName(),
                employee.getEmail(),
                employee.getRole().getRole(),
                employee.getDepartment().getName());
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("[EmpService2/loadUserByUsername] User with given email not found"));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }
}
