package com.example.application.server.entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.*;

@Data
@RequiredArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "employees")
public class Employee implements UserDetails {

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

    @ManyToOne(fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JoinColumn(name = "role_id", referencedColumnName = "id", updatable = false,
            foreignKey = @ForeignKey(name = "fk_employees_roles",
                    foreignKeyDefinition = "FOREIGN KEY (role_id) " +
                            "REFERENCES roles(id)" +
                            " ON DELETE RESTRICT" +
                            " ON UPDATE CASCADE"))
    private Role role;


    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "employee_details_id",
            referencedColumnName = "id",
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
    private Department department;

    @ManyToMany
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


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Employee employee = (Employee) o;
        return Objects.equals(id, employee.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }


    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return role.getAuthorities();
    }

    @Override
    public String getUsername() {
        return email;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }
}
