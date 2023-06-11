package com.example.application.server.services;

import com.example.application.server.entities.Role;
import com.example.application.server.repositories.RoleRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.management.relation.RoleNotFoundException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class RoleService {

    private RoleRepository roleRepository;

    public Role getRoleByName(String name) throws RoleNotFoundException{
        Optional<Role> byRole = roleRepository.findByRole(name);
        return byRole.orElseThrow(RoleNotFoundException::new);
    }
}

