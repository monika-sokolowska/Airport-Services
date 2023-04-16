package com.example.application.server.storage;

import com.example.application.server.model.WorkOrder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class WorkDataBase {

    private final List<WorkOrder> workOrders;

    public WorkDataBase() {
        this.workOrders = new ArrayList<>();
    }

    public void save(WorkOrder work) {
        workOrders.add(work);
    }

    public List<WorkOrder> findAll() {
        return workOrders;
    }

    public List<WorkOrder> findByFlight(int flightNumber) {
        List<WorkOrder> workOrderListByFlight = new ArrayList<>();
        Iterator<WorkOrder> workOrderIterator = workOrders.iterator();

        while(workOrderIterator.hasNext()) {
            WorkOrder next = workOrderIterator.next();
            if (next.getFlightNumber() == flightNumber){
                workOrderListByFlight.add(next);
            }
        }

        return workOrderListByFlight;
    }
}
