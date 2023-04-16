package com.example.application.server.service;

import com.example.application.server.model.Employee;
import com.example.application.server.model.ServiceType;
import com.example.application.server.storage.EmployeeDataBase;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class EmployeeService {

    @Autowired
    private final EmployeeDataBase employeeDataBase;

    public EmployeeService() {
        this.employeeDataBase = new EmployeeDataBase();
    }

    public void addEmployee(Employee employee){
        employeeDataBase.save(employee);
    }

    public List<Employee> getAvailableEmployeesByResponsibility(ServiceType serviceType){
        return employeeDataBase.getAvailableOnPosition(serviceType);
    }

    public List<Employee> getAllEmployees() {
        return employeeDataBase.getAll();
    }
}
