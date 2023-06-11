package com.example.application.server.services;

import com.example.application.server.entities.Role;
import com.example.application.server.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private final RoleRepository roleRepository;

    public Optional<Role> getRoleByRole(String name) {
        return roleRepository.findByRole(name);
    }
}