package com.ccumiskey.payrollbyspring.errorhandling;

public class EmployeeNotFoundException extends RuntimeException {
    public EmployeeNotFoundException(Long id) {
        super("Could not find employee ID" + id + "\n");
    }
}
