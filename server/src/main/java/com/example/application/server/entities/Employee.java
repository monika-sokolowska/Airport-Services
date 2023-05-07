package com.example.application.server.entities;

import jakarta.persistence.*;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

import java.util.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "role_id")
    private UUID roleId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false)
    private Role role;

    @Column(name = "employee_details_id")
    private UUID employeeDetailsId;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "employee_details_id", referencedColumnName = "id", insertable = false, updatable = false)
    private EmployeeDetails employeeDetails;

    @Column(name = "email")
    private String email;

    @Column(name = "password")
    private String password;

    @Column(name = "is_busy")
    private boolean isBusy;

    @ManyToMany
    @JoinTable(
            name="employees_departments",
            joinColumns = @JoinColumn(name = "employee_id"),
            inverseJoinColumns = @JoinColumn(name = "department_id"))
    private List<Department> departments = new LinkedList<>();

}