package com.example.application.server.services;

import com.example.application.server.entities.Department;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Service;
import com.example.application.server.entities.Flight;
import com.example.application.server.repositories.ServiceRepository;
import lombok.AllArgsConstructor;
//import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesService {

    private ServiceRepository serviceRepository;


    public void createService(Employee employee, Department department, Flight flight, Integer timeToService) {
        serviceRepository.save(new com.example.application.server.entities.Service(UUID.randomUUID(), LocalDateTime.now(), null, timeToService, department, flight, Set.of(employee)));
    }

    public Set<Service> getFlightServices(UUID flightId) {
        return serviceRepository.findAllByFlightId(flightId);
    }


}
