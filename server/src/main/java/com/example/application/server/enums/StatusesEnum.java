package com.example.application.server.enums;

import com.example.application.server.exceptions.StatusNotFound;
import lombok.Getter;
import lombok.RequiredArgsConstructor;

import java.util.Comparator;
import java.util.stream.Stream;

@Getter
@RequiredArgsConstructor
public enum StatusesEnum {
    // that's nasty
//    STATUS_IN_FLIGHT("inFlight", 0, DepartmentsEnum.NAVIGATOR),
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
    STATUS_NAVIGATOR_TO_PUSHBACK("navigatorToPushback", 11, DepartmentsEnum.NAVIGATOR_DEPARTURE),
    STATUS_PUSHBACK("pushback", 12, DepartmentsEnum.SERVICE_PUSHBACK),
    STATUS_READY("ready", 13, DepartmentsEnum.NAVIGATOR_DEPARTURE),
    STATUS_DEPARTURE("departure", 14, DepartmentsEnum.PILOT);

    private final String statusName;
    private final int statusCode;
    private final DepartmentsEnum department;

    public static StatusesEnum getStatusEnumByStatusName(String statusName) throws StatusNotFound {
        for (StatusesEnum status : StatusesEnum.values()) {
            if (status.statusName.equalsIgnoreCase(statusName)) {
                return status;
            }
        }
        throw new StatusNotFound("Status is not found: " + statusName);
    }



    public static boolean isDepartmentAssignedToStatus(String statusName, String departmentName) throws StatusNotFound {
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


    /**
     * @return  1 - if there is ANY `StatusesEnum` with given `departmentName`
     *                  AFTER `StatusEnum` with given `StatusName`
     *          0 - if last occurrence of `StatusesEnum` with given `departmentName`
     *                  is the same as `StatusEnum` with given `StatusName`
     *          -1 - if there is `StatusesEnum` with given `departmentName`
     *                  ONLY BEFORE `StatusEnum` with given `StatusName`
     *
     * @throws StatusNotFound - when there is no StatusEnum with given parameters
     */
    public static int compareDepartmentToStatus(String departmentName, String statusName) throws StatusNotFound {

        final int statusNameCode = StatusesEnum.getStatusEnumByStatusName(statusName).getStatusCode();

        int maxOrder = Stream.of(StatusesEnum.values())
                .filter(se -> se.department.getDepartmentName().equals(departmentName))
                .map(se -> se.statusCode)
                .max(Comparator.comparing(Integer::valueOf))
                .orElseThrow(() -> new StatusNotFound(""));

        return Integer.compare(maxOrder, statusNameCode);
    }


}
