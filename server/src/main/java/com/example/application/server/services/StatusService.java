package com.example.application.server.services;

import com.example.application.server.entities.Status;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.repositories.StatusRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class StatusService {

    private final StatusRepository statusRepository;

    public Status getStatusByStatus(String statusName) throws StatusNotFound {
        Optional<Status> optionalStatus= statusRepository.findByStatus(statusName);
        if(optionalStatus.isEmpty())
            throw new StatusNotFound("Status not found");
        return optionalStatus.get();

    }

}
