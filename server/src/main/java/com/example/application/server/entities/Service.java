package com.example.application.server.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class Service {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false,
            columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "service_start")
    private LocalDateTime serviceStart;

    @Column(name = "service_end")
    private LocalDateTime serviceEnd;

    @Column(name = "time_to_service")
    private int timeToService;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_services_department",
                    foreignKeyDefinition = "FOREIGN KEY (department_id) " +
                            "REFERENCES departments(id)" +
                            " ON DELETE CASCADE" +
                            " ON UPDATE CASCADE"))
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_services_flight",
                    foreignKeyDefinition = "FOREIGN KEY (flight_id) " +
                            "REFERENCES flights(id)" +
                            " ON DELETE SET NULL" +
                            " ON UPDATE CASCADE"))
    private Flight flight;


    @ManyToMany(mappedBy = "services")
    private Set<Employee> employees = new HashSet<>();

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Service service = (Service) o;
        return Objects.equals(id, service.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
