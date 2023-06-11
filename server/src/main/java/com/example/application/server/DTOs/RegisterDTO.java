package com.example.application.server.DTOs;

public record RegisterDTO(
        String name,
        String surname,
        String email,
        String password,
        String roleName,
        String departmentName
) {
}
