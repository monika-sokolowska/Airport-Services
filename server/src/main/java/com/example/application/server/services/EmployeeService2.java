package com.example.application.server.services;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Role;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.repositories.EmployeeRepository;
import com.example.application.server.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
public class EmployeeService2 {
    private final EmployeeRepository employeeRepository;
    private final FlightRepository flightRepository;

    @Autowired
    public EmployeeService2(EmployeeRepository employeeRepository, FlightRepository flightRepository) {
        this.employeeRepository = employeeRepository;
        this.flightRepository = flightRepository;
    }


    public Employee getEmployeeById(UUID id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given id not found."));
    }

    public Employee getEmployeeByEmail(String email) throws EmployeeNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given email not found."));
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getEmployeeDetails().getFullName(),
                employee.getEmail(),
                employee.getRole().getRole(),
                employee.getDepartment().getName());
    }

    public Employee getAvailableEmployeeByRole(Role role) throws EmployeeNotFoundException {
        Optional<Employee> byRoleAndBusy = employeeRepository.findByRoleAndBusy(role, false);
        return byRoleAndBusy.orElseThrow();
    }


    public Employee getAvailableEmployeeByDeparture(Role role) throws EmployeeNotFoundException {
        Optional<Employee> byRoleAndBusy = employeeRepository.findByRoleAndBusy(role, false);
        return byRoleAndBusy.orElseThrow();
    }

    public List<Employee> getAvailableStandMangers(Role role, int numberOfFlights) throws EmployeeNotFoundException {
        Optional<List<Employee>> byRoleAndBusy = employeeRepository.findAllByRole(role);
        if(byRoleAndBusy.isEmpty()){
            throw new EmployeeNotFoundException("State manager not found");
        }

        return byRoleAndBusy.stream()
                .flatMap(Collection::stream)
                .filter(employee -> flightRepository.findByStand_managerAndStatus(employee.getId(), "departure").size() < numberOfFlights)
                .collect(toList());
    }

    public void assignLuggageArrivalService(UUID flightId) {

    }

    public List<Employee> getAvailableEmployee(boolean isBusy) {
        return employeeRepository.findAllByBusy(isBusy);
    }

    public void updateBusy(Employee employee, boolean isBusy) {
        employee.setBusy(isBusy);
        employeeRepository.save(employee);
    }
}
