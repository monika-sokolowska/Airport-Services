package com.example.application.server.repositories;

import com.example.application.server.entities.EmployeeService;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;
import java.util.UUID;

public interface EmployeesServicesRepository extends JpaRepository<EmployeeService, UUID> {
    Optional<EmployeeService> findByEmployeeIdAndServiceId(UUID employeeId, UUID serviceId);
}
