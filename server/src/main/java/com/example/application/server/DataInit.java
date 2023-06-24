package com.example.application.server;

import com.example.application.server.entities.*;
import com.example.application.server.repositories.*;
import lombok.AllArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.JpaRepository;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;


@Configuration
@AllArgsConstructor
public class DataInit {

    private final String PATH = "data/";
    private final String DELIMITER = ",";

    private final String CSV_AIRPLANES = "airplanes.csv";
    private final String CSV_DEPARTMENTS = "departments.csv";
    private final String CSV_EMPLOYEE_DETAILS = "employees_details.csv";
    private final String CSV_EMPLOYEES = "employees.csv";
    private final String CSV_FLIGHTS = "flights.csv";
    private final String CSV_ROLES = "roles.csv";
    private final String CSV_SERVICES = "services.csv";
    private final String CSV_STATUSES = "statuses.csv";
    private final String CSV_EMPLOYEES_SERVICES = "employees_services.csv";

    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss.SSSSSS");
    private AirplaneRepository airplaneRepository;
    private DepartmentRepository departmentRepository;
    private EmployeeDetailsRepository employeeDetailsRepository;
    private EmployeesServicesRepository employeesServicesRepository;
    private EmployeeRepository employeeRepository;
    private FlightRepository flightRepository;
    private RoleRepository roleRepository;
    private ServiceRepository serviceRepository;
    private StatusRepository statusRepository;

    @Bean
    CommandLineRunner initDatabase() {
        return args -> {
            int it = 0;
            try {
                addRoles(); it++; // 0
                addStatuses(); it++;
                addAirplanes(); it++; // 2
                addEmployeeDetails(); it++;
                addDepartments(); it++; // 4
                addEmployees(); it++;
                addFlights(); it++; // 6
                addServices(); it++;
                addEmployeesServices(); it++; // 8
            } catch(Exception e) {
                System.out.println("ERROR: ---------" + it + "----------------\n" + e.getMessage() + "\nData import aborted");
            }
        };
    }
    private void addAirplanes() {
        List<Airplane> fromDatabase =  airplaneRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_AIRPLANES);
        Set<Airplane> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr ->
                        Airplane.builder()
                                .id(UUID.fromString(stringArr[0]))
                                .number(stringArr[1])
                                .build())
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, airplaneRepository);
    }

    private void addDepartments() {
        List<Department> fromDatabase =  departmentRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_DEPARTMENTS);
        Set<Department> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr ->
                        Department.builder()
                                .id(UUID.fromString(stringArr[0]))
                                .name(stringArr[1])
                                .build())
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, departmentRepository);
    }

    private void addEmployeeDetails() {
        List<EmployeeDetails> fromDatabase =  employeeDetailsRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_EMPLOYEE_DETAILS);
        Set<EmployeeDetails> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr ->
                        EmployeeDetails.builder()
                                .id(UUID.fromString(stringArr[0]))
                                .name(stringArr[1])
                                .surname(stringArr[2])
                                .build())
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, employeeDetailsRepository);
    }

    private void addEmployees() throws RuntimeException, Exception {

        List<Employee> fromDatabase =  employeeRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_EMPLOYEES);
        Set<Employee> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr -> {
                    int iterator = 0;
                    try {
                        return Employee.builder()
                                .id(UUID.fromString(stringArr[iterator++]))
                                .isBusy(Boolean.parseBoolean(stringArr[iterator++]))
                                .email(stringArr[iterator++])
                                .password(stringArr[iterator++])
                                .department(departmentRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("User department not found")))
                                .employeeDetails(employeeDetailsRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("User details not found")))
                                .role(roleRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("User role not found")))
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, employeeRepository);
    }

    private void addFlights() {
        List<Flight> fromDatabase =  flightRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_FLIGHTS);
        Set<Flight> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr -> {
                    int iterator = 0;
                    try {
                        return Flight.builder()
                                .id(UUID.fromString(stringArr[iterator++]))
                                .arrivalTime(LocalDateTime.parse(stringArr[iterator++], formatter))
                                .departureTime(LocalDateTime.parse(stringArr[iterator++], formatter))
                                .timeToService(Integer.valueOf(stringArr[iterator++]))
                                .airplane(airplaneRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("Airplane with given id for this flight doesn't exist")))
                                .standManager(employeeRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("Manager with given id for this flight doesn't exist")))
                                .status(statusRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("Status with given id for this flight doesn't exist")))
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                })
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, flightRepository);
    }

    private void addServices() {
        List<Service> fromDatabase =  serviceRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_SERVICES);
        Set<Service> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr -> {
                    int iterator = 0;
                    try {
                        return Service.builder()
                                .id(UUID.fromString(stringArr[iterator++]))
                                .serviceEnd(LocalDateTime.parse(stringArr[iterator++], formatter))
                                .serviceStart(LocalDateTime.parse(stringArr[iterator++], formatter))
                                .timeToService(Integer.parseInt(stringArr[iterator++]))
                                .department(departmentRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("Department with given id for this service doesn't exist")))
                                .flight(flightRepository.findById(UUID.fromString(stringArr[iterator++]))
                                        .orElseThrow(() -> new Exception("Flight with given id for this service doesn't exist")))
                                .build();
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }

                })
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, serviceRepository);
    }

    private void addStatuses() {
        List<Status> fromDatabase =  statusRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_STATUSES );
        Set<Status> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr ->
                        Status.builder()
                                .id(UUID.fromString(stringArr[0]))
                                .status(stringArr[1])
                                .build())
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, statusRepository);
    }

    private void addEmployeesServices() {
        List<EmployeeService> fromDatabase =  employeesServicesRepository.findAll();
        Set<String> asLines = dataReader(PATH + CSV_EMPLOYEES_SERVICES);
        Set<EmployeeService> toAdd = asLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(stringArr ->
                        EmployeeService.builder()
                                .id(UUID.fromString(stringArr[0]))
                                .employeeId(UUID.fromString(stringArr[1]))
                                .serviceId(UUID.fromString(stringArr[2]))
                                .build())
                .collect(Collectors.toSet());

        putIfAbsent(fromDatabase, toAdd, employeesServicesRepository);
    }

    private void addRoles() {
        List<Role> rolesFromDatabase =  roleRepository.findAll();
        Set<String> rolesAsLines = dataReader(PATH + CSV_ROLES);
        Set<Role> rolesToAdd = rolesAsLines.stream()
                .map(line -> line.split(DELIMITER))
                .map(roleStringArr ->
                        Role.builder()
                                .id(UUID.fromString(roleStringArr[0]))
                                .role(roleStringArr[1])
                                .build())
                .collect(Collectors.toSet());

        putIfAbsent(rolesFromDatabase, rolesToAdd, roleRepository);
    }



    private Set<String> dataReader(String csvFileName) {
        Set<String> lines = new HashSet<>();

        try (BufferedReader br = new BufferedReader(new FileReader(csvFileName))) {
            String line;
            while ((line = br.readLine()) != null)
                lines.add(line);
        } catch (FileNotFoundException e) {
            System.out.println("File: \"" + csvFileName + "\" not found");
        } catch (IOException e) {
            e.printStackTrace();
        }

        return lines;
    }

    private <T> void putIfAbsent(List<T> fromDatabase, Set<T> toInsert, JpaRepository<T, UUID> repository) {
        if (toInsert.isEmpty())
            return;

        toInsert.stream()
                .filter(t -> !fromDatabase.contains(t))
                .forEach(repository::save);
    }



}
