package com.example.application.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "employees_details")
public class EmployeeDetails {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false,
            columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "name", nullable = false)
    private String name;

    @Column(name = "surname", nullable = false)
    private String surname;

    public String getFullName() {
        return name + " " + surname;
    }

}
