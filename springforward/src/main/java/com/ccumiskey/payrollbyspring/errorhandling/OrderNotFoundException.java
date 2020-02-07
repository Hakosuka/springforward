package com.ccumiskey.payrollbyspring.errorhandling;

public class OrderNotFoundException extends RuntimeException {
    public OrderNotFoundException(Long id) {
        super("Could not find order ID" + id + "\n");
    }
}
