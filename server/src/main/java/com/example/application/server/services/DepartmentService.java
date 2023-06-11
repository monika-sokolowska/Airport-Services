package com.example.application.server.services;

import com.example.application.server.entities.Department;
import com.example.application.server.repositories.DepartmentRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;


@Service
@AllArgsConstructor
public class DepartmentService {
    private final DepartmentRepository departmentRepository;

    public Optional<Department> getDepartmentByName(String departmentName) {
        return departmentRepository.findByName(departmentName);
    }
}
