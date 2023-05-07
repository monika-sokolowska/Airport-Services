package com.example.application.server.DTOs;

import java.util.List;
import java.util.UUID;

public record EmployeeDTO(
        UUID id,
        String name, // full name
        String email,
        String role,
        List<String> services){ }