package com.example.application.server.entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "services")
public class Service {
    @Id
    @GeneratedValue
    private UUID id;

    @Column(name = "service_start")
    private LocalDateTime serviceStart;

    @Column(name = "service_end")
    private LocalDateTime serviceEnd;

    @Column(name = "time_to_service")
    private int timeToService;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "department_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_service_department"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Department department;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "flight_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_service_flight"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Flight flight;

    @ManyToMany(mappedBy = "services")
    private Set<Employee> employees = new HashSet<>();


}
