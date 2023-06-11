package com.example.application.server.repositories;

import com.example.application.server.entities.Employee;
import com.example.application.server.entities.Flight;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface FlightRepository extends JpaRepository<Flight, UUID> {

    List<Flight> findByStatusStatus(String status);
    List<Flight> findByAirplaneNumber(String number);


    List<Flight> findByStand_managerAndStatus(UUID standManager, String status);

    void saveOrUpdate(UUID flightId, String status);

    List<Flight> findByStand_manager(Employee standManger);
}
