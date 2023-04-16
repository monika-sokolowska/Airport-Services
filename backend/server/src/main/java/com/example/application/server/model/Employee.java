package com.example.application.server.model;

import java.util.Objects;
import java.util.UUID;

public class Employee {
    private UUID id;
    private String name;
    private ServiceType serviceType;

    public Employee(String name, ServiceType serviceType) {
        this.id = UUID.randomUUID();
        this.name = name;
        this.serviceType = serviceType;
    }

    public UUID getId() {
        return id;
    }

    public void setId(UUID id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    public void setServiceType(ServiceType serviceType) {
        this.serviceType = serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return id.equals(employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
