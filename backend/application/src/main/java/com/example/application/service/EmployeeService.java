package com.example.application.service;

import com.example.application.model.Employee;
import com.example.application.model.Task;
import com.example.application.storage.EmployeeDataBase;

import java.util.List;

public class EmployeeService {
    private final EmployeeDataBase employeeDataBase;

    public EmployeeService() {
        this.employeeDataBase = new EmployeeDataBase();
    }

    public void addEmployee(Employee employee){
        employeeDataBase.save(employee);
    }

    public List<Employee> getAvailableEmployeesByResponsibility(Task responsible){
        return employeeDataBase.getAvailableOnPosition(responsible);
    }

    public void markEmployeeAsBusy(Employee employee){
        employeeDataBase.markBusy(employee);
    }

    public void markEmployeeAvailable(Employee employee){
        employeeDataBase.markAvailable(employee);
    }


}
