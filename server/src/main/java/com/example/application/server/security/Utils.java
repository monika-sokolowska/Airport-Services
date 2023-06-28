package com.example.application.server.security;

import com.example.application.server.entities.Employee;
import org.springframework.security.core.context.SecurityContextHolder;

public class Utils {
    public static Employee GetCurrentUser() throws Exception {
        if(SecurityContextHolder.getContext().getAuthentication() == null)
            throw new Exception("Given token is incorrect");
        return (Employee) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
    }
}
