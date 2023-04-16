package com.example.application.server.model;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.Instant;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
public class WorkOrderTest {

    @Test
    void shouldThrowWhenNoFlightNumber() {
        assertThrows(IllegalStateException.class, () -> new WorkOrder.Builder().build(), "Flight number required.");
    }


    @Test
    void shouldBuildWorkOrder() {
        //given
        Instant now = Instant.now();
        int flightNumber = 123;
        int estimatedTimeInMinutes = 10;
        Employee marcin = new Employee("Marcin", ServiceType.CATERING_SERVICE);
        //when
        WorkOrder build = new WorkOrder.Builder().startDate(now)
                .serviceType(ServiceType.CATERING_SERVICE)
                .flightNumber(flightNumber)
                .estimatedTimeInMinutes(estimatedTimeInMinutes)
                .isDeparture(false)
                .assignee(marcin)
                .build();
        //then
        assertEquals(build.getServiceType(), ServiceType.CATERING_SERVICE);
        assertEquals(build.getFlightNumber(), flightNumber);
        assertEquals(build.getEstimatedTimeInMinutes(), estimatedTimeInMinutes);
        assertEquals(build.getAssignee(), marcin);
        assertEquals(build.getStartDate(), now);
        assertFalse(build.isDeparture());
        assertNull(build.getCompletionDate());
    }
}