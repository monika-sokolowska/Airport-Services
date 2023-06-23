package com.example.application.server.enums;


import lombok.Getter;
import lombok.RequiredArgsConstructor;

@Getter
@RequiredArgsConstructor
public enum DepartmentsEnum {

    NAVIGATOR("Navigator", 0),
    GENERAL_MANAGER("General Manager", 1),
    STAND_MANAGER("Stand Manager", 2),
    SERVICE_LUGGAGE("Luggage Service", 3),
    SERVICE_BOARDING("Boarding Service", 4),
    SERVICE_CLEANING("Cleaning Service", 5),
    SERVICE_CATERING("Catering Service", 6),
    SERVICE_TANKING("Tanking Service", 7),
    SERVICE_BOARDING_DEPARTURE("Boarding Service (departure)", 8),
    SERVICE_LUGGAGE_DEPARTURE("Luggage Service (departure)", 9),
//    NAVIGATOR_PUSHBACK("Navigator", 10),
    SERVICE_PUSHBACK("Pushback Service", 11);


    private final String departmentName;
    private final int order;

}

