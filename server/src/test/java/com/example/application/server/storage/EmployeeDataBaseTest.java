package com.example.application.server.storage;

import com.example.application.server.model.Employee;
import com.example.application.server.model.ServiceType;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.EnumSource;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
public class EmployeeDataBaseTest {

    EmployeeDataBase underTest = new EmployeeDataBase();

    @Test
    void shouldReturnEmployeeList() {
        //when
        List<Employee> all = underTest.getAll();
        //then
        assertEquals(all.size(), 12);
    }


    @ParameterizedTest
    @EnumSource(ServiceType.class)
    void shouldReturnEmployeeListOnPosition(ServiceType serviceType) {
        //when
        List<Employee> availableOnPosition = underTest.getAvailableOnPosition(serviceType);
        //then
        assertEquals(availableOnPosition.size(), 2);
    }

    @Test
    void shouldAddEmployee() {
        //given
        Employee mariusz = new Employee("mariusz", ServiceType.CLEANING_SERVICE);
        //preconditioning
        assertEquals(underTest.getAll().size(), 12);
        //when
        underTest.save(mariusz);
        //then
        List<Employee> all = underTest.getAll();
        assertEquals(all.size(), 13);
        assertTrue(all.contains(mariusz));
    }
}