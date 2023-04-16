package com.example.application.storage;

import com.example.application.model.Employee;
import com.example.application.model.Task;

import java.util.ArrayList;
import java.util.List;

public class EmployeeDataBase {
    List<Employee> employeeList;

    public EmployeeDataBase() {
        this.employeeList = new ArrayList<>();
    }

    public void save(Employee employee) {
        employeeList.add(employee);
    }

    public List<Employee> getAvailableOnPosition(Task responibility) {
        return employeeList.stream()
                .filter(employee -> !employee.isAssigned())
                .filter(employee -> employee.getResponsibility().equals(responibility))
                .toList();
    }

    public void markBusy(Employee employee) {
        for (Employee employee1 : employeeList) {
            if (employee1.equals(employee)){
                employee1.setAssigned(true);
            }
        }
    }

    public void markAvailable(Employee employee) {
        for (Employee employee1 : employeeList) {
            if (employee1.equals(employee)){
                employee1.setAssigned(false);
            }
        }
    }
}
