package com.example.application.server.entities;
//
//import jakarta.persistence.Column;
//import jakarta.persistence.Entity;
//import jakarta.persistence.Table;
//import lombok.AllArgsConstructor;
//import lombok.Builder;
//import lombok.Data;
//import lombok.RequiredArgsConstructor;
//
//import java.util.UUID;
//
//
//@Data
//@RequiredArgsConstructor
//@AllArgsConstructor
//@Builder
//@Entity
//@Table(name = "employees_services")
//public class EmployeesServices {
//    @Column(name = "employee_id")
//    private UUID employeeId;
//    @Column(name = "service_id")
//    private UUID serviceId;
//}


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Objects;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees_services")
public class EmployeeService {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false,
            columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "employee_id")
    private UUID employeeId;

    @Column(name = "service_id")
    private UUID serviceId;

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        EmployeeService that = (EmployeeService) o;
        return Objects.equals(id, that.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
