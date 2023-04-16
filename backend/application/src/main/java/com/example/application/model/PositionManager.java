package com.example.application.model;

import com.example.application.service.EmployeeService;
import com.example.application.service.FlightService;
import com.example.application.service.WorkService;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class PositionManager {

    private EmployeeService employeeService;
    private WorkService workService;
    private FlightService flightService;

    public PositionManager(EmployeeService employeeService, WorkService workService, FlightService flightService) {
        this.employeeService = employeeService;
        this.workService = workService;
        this.flightService = flightService;
    }

    public void assignWork(UUID flightNumber, Task task){
        for (Task value : Task.values()) {
            if (value.equals(task)) {
                continue;
            }
            Work.Builder workBuilder = new Work.Builder();
            workBuilder.task(value);
            workBuilder.estimatedTimeInMinutes(value.getEstimatedTime());
            workBuilder.flightNumber(flightNumber);
            workBuilder.startDate(Instant.now());
            // TODO exception if no employee available
            List<Employee> availableEmployeesByResponsibility = employeeService.getAvailableEmployeesByResponsibility(value);
            workBuilder.assignee(availableEmployeesByResponsibility.get(0));
            Work toDo = workBuilder.build();
            availableEmployeesByResponsibility.get(0).assignWork(toDo);
            workService.addWork(toDo);
        }
    }

}
