package com.example.application.server.repositories;

import com.example.application.server.entities.EmployeeDetails;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface EmployeeDetailsRepository extends JpaRepository<EmployeeDetails, UUID> {
}

