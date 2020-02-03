package com.ccumiskey.payrollbyspring;

class EmployeeNotFoundException extends RuntimeException {
    EmployeeNotFoundException(Long id) {
        super("Could not find employee ID" + id + "\n");
    }
}
