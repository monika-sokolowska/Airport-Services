package com.example.application.server.storage;

import com.example.application.server.model.Employee;
import com.example.application.server.model.ServiceType;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;

@Component
public class EmployeeDataBase {
    List<Employee> employeeList;

    public EmployeeDataBase() {
        this.employeeList = new ArrayList<>();
        employeeList.add(new Employee("Jan Kowalski", ServiceType.BOARDING_SERVICE));
        employeeList.add(new Employee("Tomasz Kowalski", ServiceType.BOARDING_SERVICE));
        employeeList.add(new Employee("Adam Psikuta", ServiceType.CATERING_SERVICE));
        employeeList.add(new Employee("Brajanek Brzeczyszykiewicz", ServiceType.CATERING_SERVICE));
        employeeList.add(new Employee("Jesica Kowalski", ServiceType.CLEANING_SERVICE));
        employeeList.add(new Employee("Mohamed Kowalski", ServiceType.CLEANING_SERVICE));
        employeeList.add(new Employee("Anna Kowalski", ServiceType.LUGGAGE_SERVICE));
        employeeList.add(new Employee("Ewa Kozlowska", ServiceType.LUGGAGE_SERVICE));
        employeeList.add(new Employee("Alicja Nowak", ServiceType.TANKING_SERVICE));
        employeeList.add(new Employee("Tomasz Nowak", ServiceType.TANKING_SERVICE));
        employeeList.add(new Employee("Tomasz Kaczmarek", ServiceType.PUSHBACK_SERVICE));
        employeeList.add(new Employee("Robert Madej", ServiceType.PUSHBACK_SERVICE));
    }

    public void save(Employee employee) {
        employeeList.add(employee);
    }

    public List<Employee> getAvailableOnPosition(ServiceType serviceType) {
        return employeeList.stream()
                .filter(employee -> employee.getServiceType().equals(serviceType))
                .toList();
    }

    public List<Employee> getAll() {
        return employeeList;
    }
}
