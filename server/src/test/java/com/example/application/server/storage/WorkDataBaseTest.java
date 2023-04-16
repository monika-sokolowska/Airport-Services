package com.example.application.server.storage;

import com.example.application.server.model.ServiceType;
import com.example.application.server.model.WorkOrder;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WorkDataBaseTest {

    public static final int FLIGHT_NUMBER = 992;
    public static final int FLIGHT_NUMBER_1 = 993;
    WorkDataBase underTest = new WorkDataBase();

    @Test
    void shouldAddWorkOrder() {
        //given
        WorkOrder workOrder = getWorkOrder(ServiceType.CLEANING_SERVICE, FLIGHT_NUMBER);
        //preconditioning
        assertTrue(underTest.findAll().isEmpty());
        //when
        underTest.save(workOrder);
        //then
        List<WorkOrder> all = underTest.findAll();
        assertEquals(all.size(), 1);
        assertTrue(all.contains(workOrder));
    }

    @Test
    void shouldReturnAllWorkOrders() {
        //given
        WorkOrder workOrder = getWorkOrder(ServiceType.CLEANING_SERVICE, FLIGHT_NUMBER);
        WorkOrder workOrder1 = getWorkOrder(ServiceType.BOARDING_SERVICE, FLIGHT_NUMBER_1);
        //when
        underTest.save(workOrder);
        underTest.save(workOrder1);
        List<WorkOrder> all = underTest.findAll();
        //then
        assertEquals(all.size(), 2);
        assertTrue(all.containsAll(List.of(workOrder, workOrder1)));
    }

    @Test
    void shouldReturnWorkOrdersByFlight() {
        //given
        WorkOrder workOrder = getWorkOrder(ServiceType.CLEANING_SERVICE, FLIGHT_NUMBER);
        WorkOrder workOrder1 = getWorkOrder(ServiceType.BOARDING_SERVICE, FLIGHT_NUMBER_1);
        //when
        underTest.save(workOrder);
        underTest.save(workOrder1);
        //then
        assertEquals(underTest.findByFlight(FLIGHT_NUMBER).get(0), workOrder);
        assertEquals(underTest.findByFlight(FLIGHT_NUMBER_1).get(0), workOrder1);
    }

    private static WorkOrder getWorkOrder(ServiceType cleaningService, int flightNumber) {
        return new WorkOrder.Builder().serviceType(cleaningService).estimatedTimeInMinutes(120).flightNumber(flightNumber).startDate(Instant.now()).build();
    }

}