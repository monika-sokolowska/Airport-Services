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
@Table(name = "tokens")
public class Token {

    @Id
    @Column(name = "id", nullable = false, updatable = false,
            columnDefinition = "uuid DEFAULT gen_random_uuid()")
    private UUID id;

    @Column(name = "token", nullable = false, length = 512)
    private String token;

    @Column(name = "expired", nullable = false)
    private boolean expired;

    @ManyToOne(cascade = CascadeType.PERSIST)
    @JoinColumn(name = "employee_id", referencedColumnName = "id", nullable = false,
            foreignKey = @ForeignKey(
                    name = "FK_tokens_employee",
                    foreignKeyDefinition = "FOREIGN KEY (employee_id) " +
                            "REFERENCES employees(id)" +
                            " ON DELETE CASCADE" +
                            " ON UPDATE CASCADE"))
    private Employee employee;

}
