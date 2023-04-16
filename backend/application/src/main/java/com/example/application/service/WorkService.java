package com.example.application.service;

import com.example.application.model.Work;
import com.example.application.storage.WorkDataBase;

import java.time.Instant;
import java.util.List;
import java.util.UUID;

public class WorkService {

    private final WorkDataBase workDataBase;

    public WorkService() {
        this.workDataBase = new WorkDataBase();
    }

    public void addWork(Work work) {
        workDataBase.save(work);
    }

    public List<Work> getWorkForFlight(UUID flightNumber){
        return workDataBase.getWorkForFlight(flightNumber);
    }

    public void completeWork(Work work) {
        Work completed = work.toBuilder()
                .completionDate(Instant.now())
                .isCompleted(true)
                .build();

        workDataBase.update(completed);

    }
}
