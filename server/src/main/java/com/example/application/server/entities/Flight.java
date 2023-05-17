package com.example.application.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.OnDelete;
import org.hibernate.annotations.OnDeleteAction;

import java.time.LocalDateTime;
import java.util.UUID;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {
    @Id
    @GeneratedValue
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_flight_airplane"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Airplane airplane;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_airplane_status"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "stand_manager_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_flight_employee"))
    @OnDelete(action = OnDeleteAction.NO_ACTION)
    private Employee employee;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "time_to_service")
    private int timeToService;

}
