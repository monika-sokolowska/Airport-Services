package com.example.application.server.services;

import com.example.application.server.entities.EmployeeService;
import com.example.application.server.repositories.EmployeesServicesRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
@AllArgsConstructor
public class EmployeesServicesService {
    private final EmployeesServicesRepository employeesServicesRepository;


    public boolean isEmployeeAssignedToService(UUID employeeId, UUID serviceId) {
        return employeesServicesRepository
                .findByEmployeeIdAndServiceId(employeeId, serviceId)
                .isPresent();
    }
}
