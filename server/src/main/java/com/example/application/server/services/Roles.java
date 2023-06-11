package com.example.application.server.services;

public enum Roles {
    STAND_MANAGER("stand manager");

    final String name;

    Roles(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
