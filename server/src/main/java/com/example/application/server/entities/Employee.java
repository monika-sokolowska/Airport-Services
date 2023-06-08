package com.example.application.server.entities;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.util.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false,
            columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "email", nullable = false, unique = true)
    private String email;

    @Column(name = "password", nullable = false)
    private String password;

    @Column(name = "is_busy")
    private boolean isBusy;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL, optional = false)
    @JoinColumn(name = "role_id", referencedColumnName = "id", insertable = false, updatable = false,
            foreignKey = @ForeignKey(name = "fk_employees_roles",
                    foreignKeyDefinition = "FOREIGN KEY (role_id) " +
                            "REFERENCES roles(id)" +
                            " ON DELETE RESTRICT" +
                            " ON UPDATE CASCADE"))
    private Role role;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_details_id",
            referencedColumnName = "id",
            insertable = false,
            updatable = false,
            unique = true,
            foreignKey = @ForeignKey(name = "fk_employees_details",
                    foreignKeyDefinition = "FOREIGN KEY (employee_details_id) " +
                            "REFERENCES employees_details(id)" +
                            " ON DELETE CASCADE" +
                            " ON UPDATE CASCADE"))
    private EmployeeDetails employeeDetails;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_employees_department",
                    foreignKeyDefinition = "FOREIGN KEY (department_id) " +
                            "REFERENCES departments(id)" +
                            " ON DELETE RESTRICT" +
                            " ON UPDATE CASCADE"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Department department;

    @ManyToMany
//    @JoinTable(name = "employees_services",
//            joinColumns = @JoinColumn(name = "employee_id"),
//            inverseJoinColumns = @JoinColumn(name = "service_id"))
    @JoinTable(name = "employees_services",
            joinColumns = @JoinColumn(name = "employee_id",
                    foreignKey = @ForeignKey(name = "fk_employees_services_employee",
                            foreignKeyDefinition = "FOREIGN KEY (employee_id) " +
                                    "REFERENCES employees(id)" +
                                    " ON UPDATE CASCADE" +
                                    " ON DELETE CASCADE")),
            inverseJoinColumns = @JoinColumn(name = "service_id",
                    foreignKey = @ForeignKey(name = "fk_employees_services_service",
                            foreignKeyDefinition = "FOREIGN KEY (service_id) " +
                                    "REFERENCES services(id)" +
                                    " ON UPDATE CASCADE" +
                                    " ON DELETE RESTRICT"))
    )

    private Set<Service> services = new HashSet<>();

}
