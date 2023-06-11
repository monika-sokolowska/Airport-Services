package com.example.application.server.services;

import com.example.application.server.entities.EmployeeDetails;
import com.example.application.server.repositories.EmployeeDetailsRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmployeeDetailsService {
    private final EmployeeDetailsRepository employeeDetailsRepository;

    public EmployeeDetails save(EmployeeDetails employeeDetails) {
        return employeeDetailsRepository.save(employeeDetails);
    }

}
