package com.example.application.server.services;

import com.example.application.server.DTOs.ServiceDTO;
import com.example.application.server.entities.Department;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Service;
import com.example.application.server.entities.Flight;
import com.example.application.server.exceptions.AirplaneNotFound;
import com.example.application.server.repositories.EmployeeRepository;
import com.example.application.server.repositories.FlightRepository;
import com.example.application.server.repositories.ServiceRepository;
import lombok.AllArgsConstructor;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;
import java.util.Set;
import java.util.UUID;
import java.util.stream.Stream;

@org.springframework.stereotype.Service
@AllArgsConstructor
public class ServicesService {

    private final ServiceRepository serviceRepository;
    private final FlightRepository flightRepository; // looping for service
    private final EmployeeRepository employeeRepository; // looping for service

    public Service createService(Employee employee, Department department, Flight flight, Integer timeToService, String message) {
        Service service = Service.builder()
                .id(UUID.randomUUID())
                .serviceStart(LocalDateTime.now())
                .serviceEnd(null)
                .timeToService(timeToService)
                .message(message)
                .department(department)
                .flight(flight)
                .employees(Set.of(employee))
                .build();
        return serviceRepository.save(service);
//        serviceRepository.save(new Service(UUID.randomUUID(), LocalDateTime.now(), null, timeToService, department, flight, Set.of(employee)));
    }

    public Set<Service> getFlightServices(UUID flightId) {
        return serviceRepository.findAllByFlightId(flightId);
    }


    public void freeEmployees(final Flight flight, final String departmentName) {
        serviceRepository.findAllByFlightId(flight.getId()).stream()
                .filter(service -> service.getDepartment().getName().equals(departmentName))
                .map(Service::getEmployees)
                .flatMap(Collection::stream)
                .forEach(employee -> {
                    employee.setBusy(false);
                    employeeRepository.save(employee);
                });
    }

    public ServiceDTO convertServiceToDto(Service service) throws AirplaneNotFound {
        final UUID flightId = service.getFlight().getId();
        return ServiceDTO.builder()
                .flightId(flightId)
                .message(service.getMessage())
                .timeToService(service.getTimeToService())
                .airplaneNumber(flightRepository.findById(flightId).orElseThrow(() -> new AirplaneNotFound("")).getAirplane().getNumber())
                .build();
    }


}
