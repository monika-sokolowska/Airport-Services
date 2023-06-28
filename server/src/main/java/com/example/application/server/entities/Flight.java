package com.example.application.server.entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.Objects;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Table(name = "flights")
public class Flight {

    @Id
    @Column(name = "id", nullable = false, unique = true, updatable = false,
            columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "airplane_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_flights_airplane",
                    foreignKeyDefinition = "FOREIGN KEY (airplane_id) " +
                            "REFERENCES airplanes(id)" +
                            " ON DELETE SET NULL" +
                            " ON UPDATE CASCADE"))
    private Airplane airplane;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "status_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_flights_status",
                    foreignKeyDefinition = "FOREIGN KEY (status_id) " +
                            "REFERENCES statuses(id)" +
                            " ON DELETE RESTRICT" +
                            " ON UPDATE CASCADE"))
    private Status status;

    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.ALL)
    @JoinColumn(name = "stand_manager_id", referencedColumnName = "id",
            foreignKey = @ForeignKey(name = "fk_flight_employee",
                    foreignKeyDefinition = "FOREIGN KEY (stand_manager_id) " +
                            "REFERENCES employees(id)" +
                            " ON DELETE NO ACTION" +
                            " ON UPDATE CASCADE"))
    private Employee standManager;

    @Column(name = "arrival_time")
    private LocalDateTime arrivalTime;

    @Column(name = "departure_time")
    private LocalDateTime departureTime;

    @Column(name = "time_to_service")
    private Integer timeToService;

    @Column(name = "message")
    private String message;


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Flight flight = (Flight) o;
        return Objects.equals(id, flight.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }

}
