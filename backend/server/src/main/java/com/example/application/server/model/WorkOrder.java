package com.example.application.server.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class WorkOrder {
    private final UUID uuid;
    private int estimatedTimeInMinutes;
    private int flightNumber;
    private Employee assignee;
    private Instant startDate;
    private Instant completionDate;
    private ServiceType serviceType;
    private boolean isDeparture;

    private WorkOrder(UUID uuid) {
        this.uuid = uuid;
    }

    public UUID getUuid() {
        return uuid;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public int getFlightNumber() {
        return flightNumber;
    }

    public Employee getAssignee() {
        return assignee;
    }

    public Instant getStartDate() {
        return startDate;
    }

    public Instant getCompletionDate() {
        return completionDate;
    }

    public boolean isDeparture() {
        return isDeparture;
    }

    public void complete(){
        this.completionDate = Instant.now();
    }

    public void start() {
        this.startDate = Instant.now();
    }

    public ServiceType getServiceType() {
        return serviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        WorkOrder workOrder = (WorkOrder) o;
        return Objects.equals(uuid, workOrder.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Work{" +
                "uuid=" + uuid +
                ", estimatedTimeInMinutes=" + estimatedTimeInMinutes +
                ", flightNumber=" + flightNumber +
                ", assignee=" + assignee +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                ", isDeparture=" + isDeparture +
                '}';
    }

    public static final class Builder {
        private UUID id;
        private int estimatedTimeInMinutes;
        private int flightNumber;
        private Employee assignee;
        private ServiceType serviceType;
        private Instant startDate;
        private Instant completionDate;
        private boolean isDeparture;

        public Builder estimatedTimeInMinutes(int estimatedTimeInMinutes) {
            this.estimatedTimeInMinutes = estimatedTimeInMinutes;
            return this;
        }

        public Builder assignee(Employee assignee) {
            this.assignee = assignee;
            return this;
        }

        public Builder startDate(Instant startDate) {
            this.startDate = startDate;
            return this;
        }

        public Builder flightNumber(int flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public Builder serviceType(ServiceType serviceType) {
            this.serviceType = serviceType;
            return this;
        }
        public Builder isDeparture(boolean isDeparture) {
            this.isDeparture = isDeparture;
            return this;
        }

        public WorkOrder build() {
            if(flightNumber == 0){
                throw new IllegalStateException("Flight number required.");
            }

            WorkOrder work = id != null ? new WorkOrder(id) : new WorkOrder(UUID.randomUUID());
            work.serviceType = serviceType;
            work.estimatedTimeInMinutes = estimatedTimeInMinutes;
            work.assignee = assignee;
            work.flightNumber = flightNumber;
            work.startDate = startDate;
            work.isDeparture = isDeparture;
            return work;
        }
    }
}
