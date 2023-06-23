package com.example.application.server.enums;

import com.example.application.server.exceptions.StatusNotFound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import javax.naming.OperationNotSupportedException;

@Getter
@RequiredArgsConstructor
public enum StatusesEnum {
    // that's nasty
    STATUS_IN_FLIGHT("inFlight", 0, DepartmentsEnum.NAVIGATOR),
    STATUS_LANDED("landed", 1, DepartmentsEnum.NAVIGATOR),
    STATUS_GENERAL_MANAGER("generalManager", 2, DepartmentsEnum.GENERAL_MANAGER),
    STATUS_STAND_MANAGER("standManager", 3, DepartmentsEnum.STAND_MANAGER),
    STATUS_LUGGAGE_ARRIVAL("luggageArrival", 4, DepartmentsEnum.SERVICE_LUGGAGE),
    STATUS_BOARDING_ARRIVAL("boardingArrival", 5, DepartmentsEnum.SERVICE_BOARDING),
    STATUS_CLEANING("cleaning", 6, DepartmentsEnum.SERVICE_CLEANING),
    STATUS_CATERING("catering", 7, DepartmentsEnum.SERVICE_CATERING),
    STATUS_TANKING("tanking", 8, DepartmentsEnum.SERVICE_TANKING),
    STATUS_BOARDING_DEPARTURE("boardingDeparture", 9, DepartmentsEnum.SERVICE_BOARDING_DEPARTURE),
    STATUS_LUGGAGE_DEPARTURE("luggageDeparture", 10, DepartmentsEnum.SERVICE_LUGGAGE_DEPARTURE),
    STATUS_NAVIGATOR_TO_PUSHBACK("navigatorToPushback", 11, DepartmentsEnum.NAVIGATOR),
    STATUS_PUSHBACK("pushback", 12, DepartmentsEnum.SERVICE_PUSHBACK),
    STATUS_READY("ready", 13, DepartmentsEnum.NAVIGATOR),
    STATUS_TAKE_OFF("takeOff", 14, DepartmentsEnum.NAVIGATOR);

    private final String statusName;
    private final int statusCode;
    private final DepartmentsEnum department;   // if emp. sends two requests, he could "skip" one service, what shouldn't be possible
                                                    // says who can push
//    public static StatusesEnum getStatusEnumByStatusName(String statusName) throws StatusNotFound {
//        try {
//            return StatusesEnum.valueOf(statusName.toUpperCase());
//        } catch (IllegalArgumentException e) {
//            throw new StatusNotFound("Status is not found: " + statusName);
//        }
//    }

    public static StatusesEnum getStatusEnumByStatusName(String statusName) throws StatusNotFound {
        for (StatusesEnum status : StatusesEnum.values()) {
            if (status.statusName.equalsIgnoreCase(statusName)) {
                return status;
            }
        }
        throw new StatusNotFound("Status is not found: " + statusName);
    }



    public static boolean isPushPossible(String statusName, String departmentName) throws StatusNotFound {
        StatusesEnum byStatus = getStatusEnumByStatusName(statusName);
        return byStatus.getDepartment().getDepartmentName().equals(departmentName);
    }


    // TODO what if we want to get next status, when status is "takeOff"?
    public static StatusesEnum getNextStatusEnumByStatusName(String statusName) throws StatusNotFound {
        final  StatusesEnum currentStatus = getStatusEnumByStatusName(statusName);

        final int currentStatusCode = currentStatus.getStatusCode();
        final int nextStatusCode = (currentStatusCode + 1) % StatusesEnum.values().length;

        for (StatusesEnum status : StatusesEnum.values()) {
            if (status.getStatusCode() == nextStatusCode) {
                return status;
            }
        }

        throw new StatusNotFound("Next status not found for status: " + statusName);
    }

}
