package com.example.application.storage;

import com.example.application.model.Work;

import java.util.*;

public class WorkDataBase {

    private final Map<UUID, List<Work>> workByFlightNum;

    public WorkDataBase() {
        this.workByFlightNum = new HashMap<>();
    }

    public void save(Work work) {
        if(workByFlightNum.containsKey(work.getFlightNumber())) {
            List<Work> works = workByFlightNum.get(work.getFlightNumber());
            works.add(work);
            workByFlightNum.replace(work.getFlightNumber(), works);
        } else {
            workByFlightNum.put(work.getFlightNumber(), List.of(work));
        }
    }

    public List<Work> getWorkForFlight(UUID flightNumber) {
        return workByFlightNum.get(flightNumber);
    }

    public void update(Work completed) {
        List<Work> works = workByFlightNum.get(completed.getFlightNumber());
        works.remove(completed);
        works.add(completed);
        workByFlightNum.replace(completed.getFlightNumber(), works);
    }
}
