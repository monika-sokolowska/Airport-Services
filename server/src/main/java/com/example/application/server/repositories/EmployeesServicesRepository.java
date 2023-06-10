package com.example.application.server.repositories;

import com.example.application.server.entities.EmployeesServices;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface EmployeesServicesRepository extends JpaRepository<EmployeesServices, UUID> {
}
