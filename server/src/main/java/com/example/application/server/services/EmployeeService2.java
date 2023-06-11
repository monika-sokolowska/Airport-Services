package com.example.application.server.services;

import com.example.application.server.DTOs.EmployeeDTO;
import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Role;
import com.example.application.server.entities.Status;
import com.example.application.server.exceptions.EmployeeNotFoundException;
import com.example.application.server.exceptions.StatusNotFound;
import com.example.application.server.repositories.EmployeeRepository;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import com.example.application.server.repositories.FlightRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Collection;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static java.util.stream.Collectors.toList;


@Service
@AllArgsConstructor
public class EmployeeService2 implements UserDetailsService {
    private final EmployeeRepository employeeRepository;
    private final FlightRepository flightRepository;
    private final StatusService statusService;

    public Employee getEmployeeById(UUID id) throws EmployeeNotFoundException {
        return employeeRepository.findById(id)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given id not found."));
    }

    public Employee getEmployeeByEmail(String email) throws EmployeeNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new EmployeeNotFoundException("Employee with given email not found."));
    }

    public boolean isEmailTaken(String email) {
        Employee employee = employeeRepository.findByEmail(email).orElse(null);
        return employee != null;
    }

    public EmployeeDTO convertEmployeeToEmployeeDTO(Employee employee) {
        return new EmployeeDTO(
                employee.getId(),
                employee.getEmployeeDetails().getFullName(),
                employee.getEmail(),
                employee.getRole().getRole(),
                employee.getDepartment().getName());
    }


    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return employeeRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("[EmpService2/loadUserByUsername] User with given email not found"));
    }

    public Employee save(Employee employee) {
        return employeeRepository.save(employee);
    }

    public List<Employee> getAvailableStandMangers(Role role, int numberOfFlights) throws EmployeeNotFoundException {
        Optional<List<Employee>> byRoleAndBusy = employeeRepository.findAllByRole(role);
        if(byRoleAndBusy.isEmpty()){
            throw new EmployeeNotFoundException("State manager not found");
        }
        Status departure;
        try {
            departure = statusService.getStatusByStatusName("departure");
        } catch (StatusNotFound e) {
            return List.of();
        }

        return byRoleAndBusy.stream()
                .flatMap(Collection::stream)
                .filter(employee -> flightRepository.findByStandManagerAndStatus(employee, departure).size() < numberOfFlights)
                .collect(toList());
    }

    public void assignLuggageArrivalService(UUID flightId) {

    }

    public List<Employee> getAvailableEmployee(boolean isBusy) {
        return employeeRepository.findAllByisBusy(isBusy);
    }

    public void updateBusy(Employee employee, boolean isBusy) {
        employee.setBusy(isBusy);
        employeeRepository.save(employee);
    }
}
