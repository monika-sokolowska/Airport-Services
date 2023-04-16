package com.example.application.server.service;

import com.example.application.server.model.Employee;
import com.example.application.server.model.MessageAssignTimeFromStandManager;
import com.example.application.server.model.ServiceType;
import com.example.application.server.model.WorkOrder;
import com.example.application.server.storage.WorkDataBase;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class WorkService {

    private final WorkDataBase workDataBase;
    private final EmployeeService employeeService;

    public WorkService(EmployeeService employeeService) {
        this.employeeService = employeeService;
        this.workDataBase = new WorkDataBase();
    }

    public void createAndAssignWork(MessageAssignTimeFromStandManager messageAssignTimeFromStandManagerService) {
        List<Employee> employees = employeeService.getAllEmployees();

        Optional<Employee> availableEmployee = getAvailableEmployee(employees, messageAssignTimeFromStandManagerService.service());

        if (availableEmployee.isEmpty()) {
            return;
        }

        WorkOrder.Builder builder = new WorkOrder.Builder()
                .flightNumber(messageAssignTimeFromStandManagerService.flightNumber())
                .assignee(availableEmployee.get())
                .estimatedTimeInMinutes(messageAssignTimeFromStandManagerService.minutes())
                .serviceType(messageAssignTimeFromStandManagerService.service());

        workDataBase.save(builder.build());
        if (messageAssignTimeFromStandManagerService.service().equals(ServiceType.BOARDING_SERVICE)
                || messageAssignTimeFromStandManagerService.service().equals(ServiceType.LUGGAGE_SERVICE))
        {
            workDataBase.save(builder.isDeparture(true).build());
        }
    }

    private Optional<Employee> getAvailableEmployee(List<Employee> employees, ServiceType serviceType) {
        return employees.stream()
                .filter(employee -> !assignedEmployees().contains(employee))
                .filter(employee -> employee.getServiceType().equals(serviceType))
                .findFirst();
    }

    private List<Employee> assignedEmployees() {
        return workDataBase.findAll().stream().map(WorkOrder::getAssignee).toList();
    }

    public List<WorkOrder> getAll() {
        return workDataBase.findAll();
    }

    public void completeStage(int flightNumber, ServiceType serviceType) {
        workDataBase.findByFlight(flightNumber).stream()
                .filter(workOrder -> workOrder.getServiceType().equals(serviceType))
                .findFirst()
                .ifPresent(WorkOrder::complete);
    }

    public void completeStage(int flightNumber, ServiceType serviceType, boolean isDeparture) {
        workDataBase.findByFlight(flightNumber).stream()
                .filter(workOrder -> workOrder.getServiceType().equals(serviceType))
                .filter(WorkOrder::isDeparture)
                .findFirst()
                .ifPresent(WorkOrder::complete);
    }

    public void startWork(int flightNumber, ServiceType serviceType) {
        workDataBase.findByFlight(flightNumber).stream()
                .filter(workOrder -> workOrder.getServiceType().equals(serviceType))
                .findFirst()
                .ifPresent(WorkOrder::start);
    }

    public void startWork(int flightNumber, ServiceType serviceType, boolean isDeparture) {
        workDataBase.findByFlight(flightNumber).stream()
                .filter(workOrder -> workOrder.getServiceType().equals(serviceType))
                .filter(WorkOrder::isDeparture)
                .findFirst()
                .ifPresent(WorkOrder::start);
    }

    public List<WorkOrder> getByFlightNumber(int flightNumber) {
        return workDataBase.findByFlight(flightNumber);
    }
}
