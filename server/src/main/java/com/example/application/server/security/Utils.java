package com.example.application.server.security;

import com.example.application.server.entities.Employee;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static Employee GetCurrentUser() {
        return (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
