package com.example.application.server.repositories;

import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface EmployeeRepository extends JpaRepository<Employee, UUID> {
    Optional<Employee> findByEmail(String email);

    Optional<Employee> findByRoleAndBusy(Role role, boolean busy);

    Optional<List<Employee>> findAllByRole(Role role);

    List<Employee> findAllByBusy(boolean isBusy);
}
