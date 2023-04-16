package com.example.application.model;

import com.example.application.service.WorkService;

public class Employee {

    private boolean assigned;
    private Task responsibility;
    private WorkService workService;
    private Work work;

    public Employee(Task responsibility, WorkService workService) {
        this.responsibility = responsibility;
    }

    public void completeWork() {
        workService.completeWork(work);
        this.work = null;
    }

    public boolean isAssigned() {
        return assigned;
    }

    public void setAssigned(boolean assigned) {
        this.assigned = assigned;
    }

    public Task getResponsibility() {
        return responsibility;
    }

    public void setResponsibility(Task responsibility) {
        this.responsibility = responsibility;
    }

    public void assignWork(Work toDo) {
        work = toDo;
    }
}
