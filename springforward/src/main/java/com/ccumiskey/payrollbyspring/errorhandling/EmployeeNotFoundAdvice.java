package com.ccumiskey.payrollbyspring.errorhandling;

import com.ccumiskey.payrollbyspring.errorhandling.EmployeeNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

@ControllerAdvice
public class EmployeeNotFoundAdvice {
    @ResponseBody // Renders the advice directly into the response body
    @ExceptionHandler(EmployeeNotFoundException.class) // Only respond in case of an EmployeeNotFoundException
    @ResponseStatus(HttpStatus.NOT_FOUND) // Issue a 404 error
    public String employeeNotFoundHandler(EmployeeNotFoundException ex) {
        return ex.getMessage();
    }
}
