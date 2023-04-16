package com.example.application.model;

import java.time.Instant;
import java.util.Objects;
import java.util.UUID;

public class Work {
    private UUID uuid;
    private Task task;
    private int estimatedTimeInMinutes;
    private UUID flightNumber;
    private boolean isCompleted;
    private Employee assignee;
    private Instant startDate;
    private Instant completionDate;

    public UUID getUuid() {
        return uuid;
    }

    public Task getStage() {
        return task;
    }

    public int getEstimatedTimeInMinutes() {
        return estimatedTimeInMinutes;
    }

    public UUID getFlightNumber() {
        return flightNumber;
    }

    public boolean isCompleted() {
        return isCompleted;
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

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Work work = (Work) o;
        return Objects.equals(uuid, work.uuid);
    }

    @Override
    public int hashCode() {
        return Objects.hash(uuid);
    }

    @Override
    public String toString() {
        return "Work{" +
                "uuid=" + uuid +
                ", stage='" + task + '\'' +
                ", estimatedTimeInMinutes=" + estimatedTimeInMinutes +
                ", flightNumber=" + flightNumber +
                ", isCompleted=" + isCompleted +
                ", assignee=" + assignee +
                ", startDate=" + startDate +
                ", completionDate=" + completionDate +
                '}';
    }

    public Builder toBuilder(){
        Builder builder = new Builder();
        builder.id = this.uuid;
        builder.estimatedTimeInMinutes = this.estimatedTimeInMinutes;
        builder.task = this.task;
        builder.startDate = this.startDate;
        builder.completionDate = this.completionDate;
        builder.assignee = this.assignee;
        builder.flightNumber = this.flightNumber;
        builder.isCompleted = this.isCompleted;
        return builder;
    }

    public static final class Builder {
        private UUID id;
        private Task task;
        private int estimatedTimeInMinutes;
        private UUID flightNumber;
        private boolean isCompleted;
        private Employee assignee;
        private Instant startDate;
        private Instant completionDate;

        public Builder task(Task task) {
            this.task = task;
            return this;
        }

        public Builder estimatedTimeInMinutes(int estimatedTimeInMinutes) {
            this.estimatedTimeInMinutes = estimatedTimeInMinutes;
            return this;
        }

        public Builder flightNumber(UUID flightNumber) {
            this.flightNumber = flightNumber;
            return this;
        }

        public Builder isCompleted(boolean isCompleted) {
            this.isCompleted = isCompleted;
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

        public Builder completionDate(Instant completionDate) {
            this.completionDate = completionDate;
            return this;
        }

        public Work build() {
            if(task == null){
                throw new IllegalStateException("Stage cannot be empty");
            }
            if(estimatedTimeInMinutes == 0){
                throw new IllegalStateException("Estimated time cannot be 0.");
            }

            Work work = new Work();
            work.task = task;
            work.estimatedTimeInMinutes = estimatedTimeInMinutes;
            work.isCompleted = isCompleted;
            work.assignee = assignee;
            work.completionDate = completionDate;
            work.flightNumber = flightNumber;
            work.startDate = startDate;
            return work;
        }
    }
}
