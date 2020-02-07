package com.ccumiskey.payrollbyspring.order;

/**
 * Orders must go through a certain series of state transitions in the course of an order.
 */
public enum OrderStatus {
    IN_PROGRESS,
    COMPLETED,
    CANCELLED;
}
