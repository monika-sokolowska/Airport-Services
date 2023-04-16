package com.example.application.model;

public enum Task {
    ENGINE_OFF(0), CATERING_SERVICE(15), CLEANING_SERVICE(20), BOARDING(20), LUGGAGE_PACKING(20), TANKING_PLANE(10), PUSHING_PLANE(5), FOLLOW_ME(2);

    private int estimatedTimeInMinutes;

    Task(int estimatedTimeInMinutes) {
        this.estimatedTimeInMinutes = estimatedTimeInMinutes;
    }

    public int getEstimatedTime() {
        return estimatedTimeInMinutes;
    }
}
