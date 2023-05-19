package com.example.application.server.services;

import com.example.application.server.entities.Airplane;
import com.example.application.server.exceptions.AirplaneNotFound;
import com.example.application.server.repositories.AirplaneRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@AllArgsConstructor
public class AirplaneService {

    AirplaneRepository airplaneRepository;

    public Airplane getByNumber(String number) throws AirplaneNotFound {

        Optional<Airplane> airplane = airplaneRepository.findByNumber(number);
        if(airplane.isEmpty())
            throw new AirplaneNotFound("Airplane " + number + " not found.");
        else
            return airplane.get();
    }

}
